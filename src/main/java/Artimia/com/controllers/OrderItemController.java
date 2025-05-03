package Artimia.com.controllers;

import Artimia.com.dtos.order_items.CreateOrderItem;
import Artimia.com.dtos.order_items.GetOrderItem;
import Artimia.com.dtos.order_items.UpdateOrderItem;
import Artimia.com.services.OrderItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
public class OrderItemController 
{

    private final OrderItemService orderItemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GetOrderItem createOrderItem(@Valid @RequestBody CreateOrderItem dto) {
        return orderItemService.createOrderItem(dto);
    }

    @GetMapping("/order/{orderId}")
    public List<GetOrderItem> getOrderItemsByOrder(@PathVariable Long orderId) {
        return orderItemService.getAllByOrderId(orderId);
    }

    @GetMapping("/{itemId}")
    public GetOrderItem getOrderItem(@PathVariable Long itemId) {
        return orderItemService.getOrderItemById(itemId);
    }

    @PutMapping("/{itemId}")
    public GetOrderItem updateOrderItem
    (
        @PathVariable Long itemId,
        @Valid @RequestBody UpdateOrderItem dto
    ) {
        return orderItemService.updateOrderItem(itemId, dto);
    }

    @PatchMapping("/{itemId}/adjust-quantity")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void adjustQuantity(
        @PathVariable Long itemId,
        @RequestParam int adjustment
    ) {
        orderItemService.adjustQuantity(itemId, adjustment);
    }

    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderItem(@PathVariable Long itemId) {
        orderItemService.deleteOrderItem(itemId);
    }
}