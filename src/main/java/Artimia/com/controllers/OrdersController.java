package Artimia.com.controllers;

import Artimia.com.dtos.order.CreateDTO;
import Artimia.com.dtos.order.GetDTO;
import Artimia.com.dtos.order.SearchDTO;
import Artimia.com.dtos.order.UpdateDTO;
import Artimia.com.exceptions.InvalidOrderException;
import Artimia.com.exceptions.InvalidStatusTransitionException;
import Artimia.com.exceptions.ResourceNotFoundException;
import Artimia.com.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrdersController 
{

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<GetDTO> createOrder(@RequestBody CreateDTO dto) 
    {

        return ResponseEntity.ok().body(orderService.createOrder(dto));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<GetDTO> getOrderById(@PathVariable Long orderId) 
    {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @PostMapping("/search")
    public ResponseEntity<List<GetDTO>> searchOrders(@RequestBody SearchDTO searchDto) {
        return ResponseEntity.ok(orderService.searchOrders(searchDto));
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<GetDTO> updateOrder(
            @PathVariable Long orderId,
            @RequestBody UpdateDTO dto
    ) {
        return ResponseEntity.ok(orderService.updateOrder(orderId, dto));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) 
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler({
            InvalidOrderException.class,
            InvalidStatusTransitionException.class
    })
    public ResponseEntity<String> handleBadRequestExceptions(RuntimeException ex) 
    {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}