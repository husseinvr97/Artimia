package Artimia.com.controllers;

import Artimia.com.dtos.errorresponse.ErrorResponse;
import Artimia.com.dtos.order_items.CreateOrderItem;
import Artimia.com.dtos.order_items.GetOrderItem;
import Artimia.com.dtos.order_items.UpdateOrderItem;
import Artimia.com.exceptions.ResourceNotFoundException;
import Artimia.com.services.OrderItemService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @PostMapping
    public ResponseEntity<GetOrderItem> createOrderItem(@RequestBody @Valid CreateOrderItem dto)
            throws ResourceNotFoundException {
        return new ResponseEntity<>(orderItemService.createOrderItem(dto), HttpStatus.CREATED);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<GetOrderItem>> getOrderItemsByOrder(@PathVariable Long orderId) {
        return new ResponseEntity<>(orderItemService.getAllByOrderId(orderId), HttpStatus.FOUND);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<GetOrderItem> getOrderItem(@PathVariable Long itemId) {
        return new ResponseEntity<>(orderItemService.getOrderItemById(itemId), HttpStatus.FOUND);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Integer> updateOrderItem(@PathVariable Long itemId, @RequestBody @Valid UpdateOrderItem dto) {
        return new ResponseEntity<>(orderItemService.updateOrderItem(itemId, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<HttpStatus> deleteOrderItem(@PathVariable Long itemId) {
        orderItemService.deleteOrderItem(itemId);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponse handleResourceNotFoundException(BadCredentialsException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleMethodArgumentNotValidException(ConstraintViolationException ex) {
        return new ErrorResponse(ex.getMessage());
    }
}