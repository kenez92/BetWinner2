package com.kenez92.betwinner.controller;

import com.kenez92.betwinner.domain.OrderDto;
import com.kenez92.betwinner.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("v1/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getOrders() {
        log.info("Getting all orders");
        List<OrderDto> orderDtoList = orderService.getOrders();
        log.info("Return all orders: {}", orderDtoList);
        return orderDtoList;
    }

    @GetMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDto orderDto(@PathVariable Long orderId) {
        log.info("Getting order by id: {}", orderId);
        OrderDto orderDto = orderService.getOrder(orderId);
        log.info("Return order: {}", orderDto);
        return orderDto;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrderDto createOrder(@RequestBody OrderDto orderDto) {
        log.info("Creating new order: {}", orderDto);
        OrderDto createdOrder = orderService.createOrder(orderDto);
        log.info("Return created order: {}", orderDto);
        return createdOrder;
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrderDto updateOrder(@RequestBody OrderDto orderDto) {
        log.info("Updating order id: {}", orderDto.getId());
        OrderDto updatedOrder = orderService.updateOrder(orderDto);
        log.info("Return updated order: {}", updatedOrder);
        return updatedOrder;
    }

    @DeleteMapping("/{orderId}")
    public boolean deleteOrder(@PathVariable Long orderId) {
        log.info("Deleting order id: {}", orderId);
        boolean result = orderService.deleteOrder(orderId);
        if (result) {
            log.info("Order deleted id: {}", orderId);
            return true;
        } else {
            log.info("Order not deleted id: {}", orderId);
            return false;
        }
    }
}
