package Artimia.com.controllers;

import Artimia.com.dtos.errorresponse.ErrorResponse;
import Artimia.com.dtos.order_items.CreateOrderItem;
import Artimia.com.dtos.order_items.GetOrderItem;
import Artimia.com.dtos.order_items.UpdateOrderItem;
import Artimia.com.exceptions.ResourceNotFoundException;
import Artimia.com.services.OrderItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
 
import java.util.List;

@RestController
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
public class OrderItemController 
{

    private final OrderItemService orderItemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GetOrderItem> createOrderItem( @RequestBody CreateOrderItem dto) 
    {
        return ResponseEntity.ok().body(orderItemService.createOrderItem(dto));
    }

    @GetMapping("/order/{orderId}")
    @ResponseStatus(HttpStatus.FOUND)
    public List<GetOrderItem> getOrderItemsByOrder(@PathVariable Long orderId) 
    {
        return orderItemService.getAllByOrderId(orderId);
    }
 
    @GetMapping("/{itemId}")
    public GetOrderItem getOrderItem(@PathVariable Long itemId) 
    {
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
    public void adjustQuantity(@PathVariable Long itemId,@RequestParam int adjustment) 
    {
        orderItemService.adjustQuantity(itemId, adjustment);
    }

    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderItem(@PathVariable Long itemId) 
    {
        orderItemService.deleteOrderItem(itemId);
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponse handleResourceNotFoundException(BadCredentialsException ex) 
    {
        return new ErrorResponse(ex.getMessage());
    }
}