package Artimia.com.services;

import Artimia.com.dtos.order.*;
import Artimia.com.entities.Orders;
import Artimia.com.entities.Users;
import Artimia.com.enums.OrderStatus;
import Artimia.com.exceptions.*;
import Artimia.com.mapper.OrderMapper;
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

    @Transactional
    public GetDTO createOrder(CreateDTO dto) {
        Users user = userRepository.findById(dto.userId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        validateOrderAmount(dto.totalAmount());

        Orders order = new Orders();
        order.setUser(user);
        order.setTotalAmount(dto.totalAmount());
        order.setOrderStatus(dto.status());
        order.setShippingAddress(dto.shippingAddress());
        order.setPaymentMethod(dto.paymentMethod());

        return OrderMapper.toDto(ordersRepository.save(order));
    }

    public GetDTO getOrderById(Long orderId) {
        return ordersRepository.findById(orderId)
            .map(OrderMapper::toDto)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public List<GetDTO> searchOrders(SearchDTO searchDto) {
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
    public GetDTO updateOrder(Long orderId, UpdateDTO dto) {
        Orders order = ordersRepository.findById(orderId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        validateStatusTransition(order.getOrderStatus(), dto.status());

        order.setOrderStatus(dto.status());
        order.setShippingAddress(dto.shippingAddress());

        return OrderMapper.toDto(ordersRepository.save(order));
    }

    public List<MonthlySalesDTO> getMonthlySalesReport() 
    {
        return ordersRepository.getMonthlySalesReport().stream()
            .map(this::mapToMonthlySalesDto)
            .toList();
    }

    private void validateOrderAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidOrderException("Order amount must be positive");
        }
    }

    private void validateStatusTransition(OrderStatus current, OrderStatus newStatus) 
    {
        if (current == OrderStatus.CANCELLED && newStatus != OrderStatus.CANCELLED) {
            throw new InvalidStatusTransitionException("Cannot modify cancelled orders");
        }
    }

    private MonthlySalesDTO mapToMonthlySalesDto(Object[] result) {
        return new MonthlySalesDTO(
            (String) result[0],
            ((Number) result[1]).doubleValue()
        );
    }

    public record MonthlySalesDTO(String month, double total) {}
}