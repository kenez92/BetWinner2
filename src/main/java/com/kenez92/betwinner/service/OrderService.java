package com.kenez92.betwinner.service;

import com.kenez92.betwinner.domain.Order;
import com.kenez92.betwinner.domain.OrderDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.OrderMapper;
import com.kenez92.betwinner.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getOrders() {
        log.debug("Getting all orders");
        List<Order> orders = orderRepository.findAll();
        List<OrderDto> orderDtoList = orderMapper.mapToOrderDtoList(orders);
        log.debug("Return all orders");
        return orderDtoList;
    }

    public OrderDto getOrder(Long orderId) {
        log.debug("Getting order id: {}", orderId);
        Order order = orderRepository.findById(orderId).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_ORDER_NOT_FOUND_EXCEPTION));
        OrderDto orderDto = orderMapper.mapToOrderDto(order);
        log.debug("Return order: {}", orderDto);
        return orderDto;
    }

    public OrderDto createOrder(OrderDto orderDto) {
        if (isExisting(orderDto.getId())) {
            throw new BetWinnerException(BetWinnerException.ERR_ORDER_WITH_THIS_ID_ALREADY_EXISTS_EXCEPTION);
        } else {
            log.debug("Creating new order: {}", orderDto);
            Order order = orderRepository.save(orderMapper.mapToOrder(orderDto));
            OrderDto createdOrder = orderMapper.mapToOrderDto(order);
            log.debug("Return created order: {}", createdOrder);
            return createdOrder;
        }
    }

    public OrderDto updateOrder(OrderDto orderDto) {
        if (isExisting(orderDto.getId())) {
            log.debug("Updating order: {}", orderDto);
            Order order = orderRepository.save(orderMapper.mapToOrder(orderDto));
            OrderDto updatedOrder = orderMapper.mapToOrderDto(order);
            log.debug("Return updated order: {}", updatedOrder);
            return updatedOrder;
        }
        throw new BetWinnerException(BetWinnerException.ERR_ORDER_NOT_EXIST_EXCEPTION);
    }

    public boolean deleteOrder(Long orderId) {
        log.debug("Deleting order id: {}", orderId);
        if (isExisting(orderId)) {
            orderRepository.deleteById(orderId);
            if (isExisting(orderId)) {
                throw new BetWinnerException(BetWinnerException.ERR_ORDER_NOT_DELETED);
            }
            log.debug("Order deleted id: {}", orderId);
            return true;
        }
        return false;
    }

    private boolean isExisting(Long orderId) {
        boolean result = orderRepository.existsById(orderId);
        log.info("Order exists: {}", result);
        return result;
    }
}
