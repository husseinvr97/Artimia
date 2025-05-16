package Artimia.com.controllers;

import Artimia.com.dtos.errorresponse.ErrorResponse;
import Artimia.com.dtos.order.CreateDTO;
import Artimia.com.dtos.order.GetDTO;
import Artimia.com.dtos.order.SearchDTO;
import Artimia.com.dtos.order.UpdateDTO;
import Artimia.com.dtos.others.CheckOutDto;
import Artimia.com.exceptions.InvalidOrderException;
import Artimia.com.exceptions.InvalidStatusTransitionException;
import Artimia.com.exceptions.ResourceNotFoundException;
import Artimia.com.services.OrderService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

@Validated
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<GetDTO> createOrder(@RequestBody @Valid CreateDTO dto) {
        return new ResponseEntity<>(orderService.createOrder(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<GetDTO> getOrderById(@PathVariable Long orderId) {
        return new ResponseEntity<>(orderService.getOrderById(orderId), HttpStatus.FOUND);
    }

    @GetMapping
    public ResponseEntity<List<GetDTO>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAll(), HttpStatus.FOUND);
    }

    @PostMapping("/search")
    public ResponseEntity<List<GetDTO>> searchOrders(@RequestBody SearchDTO searchDto) {
        return new ResponseEntity<>(orderService.searchOrders(searchDto), HttpStatus.FOUND);
    }

    @PostMapping(value = "/checkout/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> proceedToCheckOut(@RequestBody @Valid List<CheckOutDto> orderItemList,
            @PathVariable Long userId) {
        return new ResponseEntity<>(orderService.proceedToCheckOut(orderItemList, userId), HttpStatus.OK);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<GetDTO> updateOrder(@PathVariable Long orderId, @RequestBody UpdateDTO dto) {
        return new ResponseEntity<>(orderService.updateOrder(orderId, dto), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GetDTO>> getMethodName(@PathVariable Long userId) {
        return new ResponseEntity<>(orderService.getAllByUserId(userId), HttpStatus.OK);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ InvalidOrderException.class, InvalidStatusTransitionException.class })
    public ResponseEntity<ErrorResponse> handleBadRequestExceptions(RuntimeException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class, ConstraintViolationException.class })
    public ResponseEntity<ErrorResponse> handleConstraintValidationsExceptions(RuntimeException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}