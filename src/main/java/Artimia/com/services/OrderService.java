package Artimia.com.services;

import Artimia.com.dtos.order.CreateDTO;
import Artimia.com.dtos.order.GetDTO;
import Artimia.com.dtos.order.SearchDTO;
import Artimia.com.dtos.order.UpdateDTO;
import Artimia.com.dtos.others.CheckOutDto;
import Artimia.com.entities.OrderItems;
import Artimia.com.entities.Orders;
import Artimia.com.entities.UserAddress;
import Artimia.com.entities.Users;
import Artimia.com.enums.OrderStatus;
import Artimia.com.exceptions.InvalidStatusTransitionException;
import Artimia.com.exceptions.ResourceNotFoundException;
import Artimia.com.mapper.OrderMapper;
import Artimia.com.repositories.CityRepository;
import Artimia.com.repositories.GovernorateRepository;
import Artimia.com.repositories.OrderItemsRepository;
import Artimia.com.repositories.OrdersRepository;
import Artimia.com.repositories.ProductSizesRepository;
import Artimia.com.repositories.ProductsRepository;
import Artimia.com.repositories.UserAddressRepository;
import Artimia.com.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

        private final OrdersRepository ordersRepository;
        private final UserRepository userRepository;
        private final CityRepository cityRepository;
        private final GovernorateRepository governorateRepository;
        private final OrderItemService orderItemService;
        private final OrderItemsRepository orderItemsRepository;
        private final UserAddressRepository userAddressRepository;
        private final ProductsRepository productsRepository;
        private final ProductSizesRepository productSizeRepository;

        @Transactional
        public GetDTO createOrder(CreateDTO dto) {
                Users user = userRepository.findById(dto.userId())
                                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

                UserAddress userAddress = new UserAddress();
                userAddress.setAddressLine1(dto.addressLine1());
                userAddress.setGovernorate(governorateRepository.findByNameEn(dto.cityName())
                                .orElseThrow(() -> new ResourceNotFoundException("City Not Found")));
                userAddress.setCity(cityRepository.findByNameEn(dto.cityName())
                                .orElseThrow(() -> new ResourceNotFoundException("City Not Found")));
                userAddress.setUser(user);

                List<BigDecimal> prices = new ArrayList<>(dto.orderItemsIds().length * 2);
                for (Long orderItemId : dto.orderItemsIds()) {
                        prices.add(orderItemService.getOrderItemById(orderItemId.longValue()).unitPrice());
                }

                Orders order = new Orders();
                order.setUser(user);
                order.setTotalAmount(prices.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
                order.setOrderStatus(dto.status());
                order.setUserAddress(userAddress);

                return OrderMapper.toDto(ordersRepository.save(order));
        }

        public GetDTO getOrderById(Long orderId) {
                return ordersRepository.findById(orderId).map(OrderMapper::toDto)
                                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        }

        public List<GetDTO> searchOrders(SearchDTO searchDto) {
                return ordersRepository
                                .searchOrders(searchDto.userId(), searchDto.status(), searchDto.startDate(),
                                                searchDto.endDate())
                                .stream()
                                .map(OrderMapper::toDto)
                                .toList();
        }

        @Transactional
        public GetDTO updateOrder(Long orderId, UpdateDTO dto) {
                Orders order = ordersRepository.findById(orderId)
                                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

                validateStatusTransition(order.getOrderStatus(), dto.status());

                order.setOrderStatus(dto.status());

                UserAddress userAddress = new UserAddress();
                userAddress.setAddressLine1(dto.addressLine1());
                userAddress.setGovernorate(governorateRepository.findByNameEn(dto.governorateName())
                                .orElseThrow(() -> new ResourceNotFoundException("City Not Found")));
                userAddress.setCity(cityRepository.findByNameEn(dto.cityName())
                                .orElseThrow(() -> new ResourceNotFoundException("City Not Found")));
                order.setUserAddress(userAddress);

                return OrderMapper.toDto(ordersRepository.save(order));
        }

        @Transactional
        public Long proceedToCheckOut(List<CheckOutDto> orderItemList, Long userId) {

                Orders order = new Orders();
                if (userId == null) {
                        throw new IllegalArgumentException("userId is null");
                }
                order.setUser(
                                userRepository.findById(userId)
                                                .orElseThrow(() -> new ResourceNotFoundException("User Not Found")));

                BigDecimal totalAmount = orderItemList.stream()
                                .map(CheckOutDto::price)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                order.setTotalAmount(totalAmount);
                order.setUserAddress(userAddressRepository.findByUserUserId(userId)
                                .orElseThrow(() -> new ResourceNotFoundException("User address not found")));

                ordersRepository.save(order);

                List<OrderItems> orderItems = new ArrayList<>();

                for (CheckOutDto checkOutDto : orderItemList) {
                        OrderItems orderItem = new OrderItems();
                        orderItem.setOrder(order);
                        orderItem.setProduct(productsRepository.findById(checkOutDto.productId())
                                        .orElseThrow(() -> new ResourceNotFoundException("Product is not found")));
                        orderItem.setQuantity(checkOutDto.quantity());
                        orderItem.setSize(productSizeRepository.findById(checkOutDto.productSizeId())
                                        .orElseThrow(() -> new ResourceNotFoundException("Size is not found")));
                        orderItem.setUnitPrice(checkOutDto.price());
                        orderItems.add(orderItem);
                        orderItemsRepository.save(orderItem);
                }
                order.setOrderItems(orderItems);
                return order.getOrderID();
        }

        @Transactional
        public List<GetDTO> getAllByUserId(Long userId) {
                return ordersRepository
                                .findByUser(userRepository.findById(userId)
                                                .orElseThrow(() -> new ResourceNotFoundException("User Not Found")))
                                .stream().map(OrderMapper::toDto).toList();
        }

        private void validateStatusTransition(OrderStatus current, OrderStatus newStatus) {
                if (current == OrderStatus.CANCELLED && newStatus != OrderStatus.CANCELLED) {
                        throw new InvalidStatusTransitionException("Cannot modify cancelled orders");
                }
        }

        public record MonthlySalesDTO(String month, double total) {
        }
}