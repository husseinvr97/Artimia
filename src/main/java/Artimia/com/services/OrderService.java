package Artimia.com.services;

import Artimia.com.dtos.order.CreateDTO;
import Artimia.com.dtos.order.GetDTO;
import Artimia.com.dtos.order.SearchDTO;
import Artimia.com.dtos.order.UpdateDTO;
import Artimia.com.entities.Orders;
import Artimia.com.entities.UserAddress;
import Artimia.com.entities.Users;
import Artimia.com.enums.OrderStatus;
import Artimia.com.exceptions.*;
import Artimia.com.mapper.OrderMapper;
import Artimia.com.repositories.CityRepository;
import Artimia.com.repositories.GovernorateRepository;
import Artimia.com.repositories.OrdersRepository;
import Artimia.com.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService 
{

    private final OrdersRepository ordersRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final GovernorateRepository governorateRepository;

    @Transactional
    public GetDTO createOrder(CreateDTO dto) 
    {
        Users user = userRepository.findById(dto.userId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserAddress userAddress = new UserAddress();
        userAddress.setAddressLine1(dto.addressLine1());
        userAddress.setGovernorate(governorateRepository.findByNameEn(dto.cityName()).orElseThrow(()->new ResourceNotFoundException("City Not Found")));
        userAddress.setCity(cityRepository.findByNameEn(dto.cityName()).orElseThrow(()->new ResourceNotFoundException("City Not Found")));
        userAddress.setPostalCode(dto.postalCode());


        validateOrderAmount(dto.totalAmount());

        Orders order = new Orders();
        order.setUser(user);
        order.setTotalAmount(dto.totalAmount());
        order.setOrderStatus(dto.status());
        order.setUserAddress(userAddress);
        order.setPaymentMethod(dto.paymentMethod());

        return OrderMapper.toDto(ordersRepository.save(order));
    }

    public GetDTO getOrderById(Long orderId) 
    {
        return ordersRepository.findById(orderId)
            .map(OrderMapper::toDto)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public List<GetDTO> searchOrders(SearchDTO searchDto) 
    {
        return ordersRepository.searchOrders(
                searchDto.userId(),
                searchDto.status(),
                searchDto.startDate(),
                searchDto.endDate()
            ).stream()
            .map(OrderMapper::toDto)
            .toList();
    }

    @Transactional
    public GetDTO updateOrder(Long orderId, UpdateDTO dto) 
    {
        Orders order = ordersRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        validateStatusTransition(order.getOrderStatus(), dto.status());

        order.setOrderStatus(dto.status());

        UserAddress userAddress = new UserAddress();
        userAddress.setAddressLine1(dto.addressLine1());
        userAddress.setGovernorate(governorateRepository.findByNameEn(dto.governorateName()).orElseThrow(()->new ResourceNotFoundException("City Not Found")));
        userAddress.setCity(cityRepository.findByNameEn(dto.cityName()).orElseThrow(()->new ResourceNotFoundException("City Not Found")));
        userAddress.setPostalCode(dto.postalCode());
        order.setUserAddress(userAddress);

        return OrderMapper.toDto(ordersRepository.save(order));
    }

    private void validateOrderAmount(BigDecimal amount) 
    {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) 
        {
            throw new InvalidOrderException("Order amount must be positive");
        }
    }

    private void validateStatusTransition(OrderStatus current, OrderStatus newStatus) 
    {
        if (current == OrderStatus.CANCELLED && newStatus != OrderStatus.CANCELLED) {
            throw new InvalidStatusTransitionException("Cannot modify cancelled orders");
        }
    }

    public record MonthlySalesDTO(String month, double total) {}
}