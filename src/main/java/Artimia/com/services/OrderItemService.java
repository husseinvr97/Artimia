package Artimia.com.services;

import Artimia.com.dtos.order_items.CreateOrderItem;
import Artimia.com.dtos.order_items.GetOrderItem;
import Artimia.com.dtos.order_items.UpdateOrderItem;
import Artimia.com.entities.OrderItems;
import Artimia.com.entities.Orders;
import Artimia.com.entities.ProductSizes;
import Artimia.com.entities.Products;
import Artimia.com.exceptions.ResourceNotFoundException;
import Artimia.com.mapper.OrderItemMapper;
import Artimia.com.repositories.OrderItemsRepository;
import Artimia.com.repositories.OrdersRepository;
import Artimia.com.repositories.ProductSizesRepository;
import Artimia.com.repositories.ProductsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService 
{

    private final OrderItemsRepository orderItemsRepository;
    private final OrdersRepository orderRepository;
    private final ProductsRepository productRepository;
    private final ProductSizesRepository productSizeRepository;

    @Transactional
    public GetOrderItem createOrderItem(CreateOrderItem dto) 
    {
        Orders order = orderRepository.findById(dto.orderId())
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        
        Products product = productRepository.findById(dto.productId())
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        
        ProductSizes size = productSizeRepository.findById(dto.sizeId())
            .orElseThrow(() -> new ResourceNotFoundException("Size not found"));

        OrderItems item = new OrderItems();
        item.setOrder(order);
        item.setProduct(product);
        item.setSize(size);
        item.setQuantity(dto.quantity());
        item.setUnitPrice(dto.unitPrice());
 
        return OrderItemMapper.toGetDto(orderItemsRepository.save(item));
    }

    public List<GetOrderItem> getAllByOrderId(Long orderId) 
    {
        return orderItemsRepository.findByOrderOrderID(orderId).stream().map(OrderItemMapper::toGetDto).toList();
    }

    public GetOrderItem getOrderItemById(Long itemId) 
    {
        return orderItemsRepository.findById(itemId).map(OrderItemMapper::toGetDto)
        .orElseThrow(() -> new ResourceNotFoundException("Order item not found"));
    }

    @Transactional
    public int updateOrderItem(Long itemId, UpdateOrderItem dto) 
    {
          if(!orderItemsRepository.existsById(itemId))
            throw new ResourceNotFoundException("Order item is not found");

        int numberOfRowsEffected = 0;
        if(!(dto.quantity() == 0))
        {
            orderItemsRepository.setQuantity(itemId, dto.quantity());
            ++numberOfRowsEffected;
        }
        if(!(dto.unitPrice().doubleValue() == 0.00))
        {
            orderItemsRepository.setUnitPrice(itemId,dto.unitPrice());
            ++numberOfRowsEffected;
        }
        return numberOfRowsEffected;
    }

    @Transactional
    public void deleteOrderItem(Long itemId) 
    {
        if (!orderItemsRepository.existsById(itemId)) 
        {
            throw new ResourceNotFoundException("Order item not found");
        }
        orderItemsRepository.deleteById(itemId);
    }
}