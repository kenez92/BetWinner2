package com.kenez92.betwinner.service;

import com.kenez92.betwinner.common.enums.MatchType;
import com.kenez92.betwinner.domain.*;
import com.kenez92.betwinner.persistence.entity.Coupon;
import com.kenez92.betwinner.persistence.entity.Order;
import com.kenez92.betwinner.persistence.entity.User;
import com.kenez92.betwinner.persistence.entity.coupons.CouponType;
import com.kenez92.betwinner.persistence.entity.matches.Match;
import com.kenez92.betwinner.persistence.entity.matches.MatchDay;
import com.kenez92.betwinner.persistence.entity.matches.MatchScore;
import com.kenez92.betwinner.persistence.entity.matches.MatchStats;
import com.kenez92.betwinner.persistence.entity.weather.Weather;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.persistence.repository.OrderRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class OrderServiceTestSuite {
    private final static String HOME_TEAM = "home team";
    private final static String AWAY_TEAM = "away team";

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    public void testGetOrders() {
        //Given
        List<Order> orderList = new ArrayList<>();
        orderList.add(createOrder());
        orderList.add(createOrder());
        orderList.add(createOrder());
        Mockito.when(orderRepository.findAll()).thenReturn(orderList);
        //When
        List<OrderDto> orderDtoList = orderService.getOrders();
        //Then
        Assert.assertEquals(3, orderDtoList.size());
    }

    @Test
    public void testGetOrder() {
        //Given
        Order order = createOrder();
        Long orderId = order.getId();
        Mockito.when(orderRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(order));
        //When
        OrderDto orderDto = orderService.getOrder(orderId);
        //Then
        Assert.assertEquals(orderId, orderDto.getId());
    }

    @Test
    public void testGetOrderShouldThrowBetWinnerExceptionWhenOrderNotFound() {
        //Given
        Mockito.when(orderRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        //When
        //Then
        Assertions.assertThrows(BetWinnerException.class, () -> orderService.getOrder(2L));
    }

    @Test
    public void testCreateOrder() {
        //Given
        OrderDto orderDto = new OrderDto();
        orderDto.setUser(UserDto.builder()
                .money("2000")
                .build());
        orderDto.setCoupon(CouponDto.builder()
                .rate(10.0)
                .build());
        Order order = createOrder();
        Mockito.when(orderRepository.save(ArgumentMatchers.any(Order.class))).thenReturn(order);
        //When
        OrderDto tmpOrder = orderService.createOrder(orderDto);
        //Then
        Assert.assertNotNull(tmpOrder);
    }

    @Test
    public void testCreateOrderShouldThrowExceptionWhenIdIsDifferentThanNullOr0() {
        //Given
        OrderDto orderDto = new OrderDto();
        orderDto.setUser(UserDto.builder()
                .id(234L)
                .money("2000")
                .build());
        orderDto.setCoupon(CouponDto.builder()
                .rate(10.0)
                .build());
        orderDto.setId(2332L);
        //When
        //Given
        Assertions.assertThrows(BetWinnerException.class, () -> orderService.createOrder(orderDto));
    }

    @Test
    public void testUpdateOrder() {
        //Given
        OrderDto orderDto = new OrderDto();
        orderDto.setId(3L);
        Order order = createOrder();
        Mockito.when(orderRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
        Mockito.when(orderRepository.save(ArgumentMatchers.any(Order.class))).thenReturn(order);
        //When
        OrderDto tmpOrder = orderService.updateOrder(orderDto);
        //Then
        Assert.assertEquals(orderDto.getId(), tmpOrder.getId());
    }

    @Test
    public void testUpdateOrderShouldThrowExceptionWhenOrderNotExists() {
        //Given
        OrderDto orderDto = new OrderDto();
        orderDto.setId(3L);
        //When
        Mockito.when(orderRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);
        //Then
        Assertions.assertThrows(BetWinnerException.class, () -> orderService.updateOrder(orderDto));
    }

    @Test
    public void testDeleteOrderShouldThrowBetWinnerExceptionWhenOrderNotDeleted() {
        //Given
        Mockito.when(orderRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
        //When
        //Then
        Assertions.assertThrows(BetWinnerException.class, () -> orderService.deleteOrder(3L));
    }

    @Test
    public void testDeleteOrderShouldReturnFalseWhenNotExists() {
        //Given
        Mockito.when(orderRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);
        //When
        boolean result = orderService.deleteOrder(3L);
        //Then
        Assert.assertFalse(result);
    }


    private Order createOrder() {
        return Order.builder()
                .id(3L)
                .coupon(new Coupon())
                .user(new User())
                .build();
    }
}
