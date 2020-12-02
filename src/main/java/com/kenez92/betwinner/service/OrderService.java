package com.kenez92.betwinner.service;

import com.kenez92.betwinner.domain.OrderDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.OrderMapper;
import com.kenez92.betwinner.persistence.entity.Order;
import com.kenez92.betwinner.persistence.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserService userService;

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
        setData(order);
        OrderDto orderDto = orderMapper.mapToOrderDto(order);
        log.debug("Return order: {}", orderDto);
        return orderDto;
    }

    public OrderDto createOrder(OrderDto orderDto) {
        if (new BigDecimal(orderDto.getUser().getMoney()).compareTo(BigDecimal.valueOf(orderDto.getCoupon().getRate())) > 0) {
            if (orderDto.getId() != null) {
                if (orderDto.getId() != 0 && isExisting(orderDto.getId())) {
                    throw new BetWinnerException(BetWinnerException.ERR_ORDER_WITH_THIS_ID_ALREADY_EXISTS_EXCEPTION);
                }
            } else {
                log.debug("Creating new order: {}", orderDto);
                Order order = orderRepository.save(orderMapper.mapToOrder(orderDto));
                OrderDto createdOrder = orderMapper.mapToOrderDto(order);
                log.debug("Return created order: {}", createdOrder);
                return createdOrder;
            }
            userService.putMoney(orderDto.getUser().getId(), -(orderDto.getCoupon().getRate()));
        }
        throw new BetWinnerException(BetWinnerException.ERR_ORDER_ID_MUST_BE_NULL_OR_0_EXCEPTION);
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
        log.debug("Order not exists id: {}", orderId);
        return false;
    }

    private void setData(Order order) {
        order.getUser().setOrders(new ArrayList<>());
        //order.getCoupon().getCouponTypeList().
    }

    private boolean isExisting(final Long orderId) {
        if (orderId == null) {
            throw new BetWinnerException(BetWinnerException.ERR_ORDER_ID_MUST_BE_NOT_NULL_EXCEPTION);
        }
        boolean result = orderRepository.existsById(orderId);
        log.info("Order exists: {}", result);
        return result;
    }
}
