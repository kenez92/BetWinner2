package com.kenez92.betwinner.controller;

import com.google.gson.Gson;
import com.kenez92.betwinner.domain.CouponDto;
import com.kenez92.betwinner.domain.OrderDto;
import com.kenez92.betwinner.domain.UserDto;
import com.kenez92.betwinner.domain.coupons.CouponTypeDto;
import com.kenez92.betwinner.domain.matches.MatchDayDto;
import com.kenez92.betwinner.domain.matches.MatchDto;
import com.kenez92.betwinner.domain.matches.MatchScoreDto;
import com.kenez92.betwinner.domain.matches.WeatherDto;
import com.kenez92.betwinner.service.OrderService;
import com.kenez92.betwinner.service.users.strategy.factory.UserStrategyFactory;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;


    @Test
    @WithMockUser(username="admin")
    public void testGetOrders() throws Exception {
        //Given
        List<OrderDto> orderDtoList = new ArrayList<>();
        orderDtoList.add(createOrderDto());
        orderDtoList.add(createOrderDto());
        Mockito.when(orderService.getOrders()).thenReturn(orderDtoList);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testGetOrder() throws Exception {
        //Given
        Mockito.when(orderService.getOrder(ArgumentMatchers.anyLong())).thenReturn(createOrderDto());
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/orders/23214")
                .contentType(MediaType.APPLICATION_JSON)
                .secure(true))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coupon.id", Matchers.is(302)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coupon.couponTypeList", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.id", Matchers.is(123)));
    }

    @Test
    public void testCreateOrder() throws Exception {
        //Given
        OrderDto orderDto = createOrderDto();
        Mockito.when(orderService.createOrder(ArgumentMatchers.any(OrderDto.class))).thenReturn(orderDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(orderDto);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/orders/")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coupon.id", Matchers.is(302)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coupon.couponTypeList", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.id", Matchers.is(123)));
    }

    @Test
    public void testUpdateOrder() throws Exception {
        //Given
        OrderDto orderDto = createOrderDto();
        Mockito.when(orderService.updateOrder(ArgumentMatchers.any(OrderDto.class))).thenReturn(orderDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(orderDto);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/orders/")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coupon.id", Matchers.is(302)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coupon.couponTypeList", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.id", Matchers.is(123)));
    }

    @Test
    public void testDeleteOrder() throws Exception {
        //Given
        Mockito.when(orderService.deleteOrder(ArgumentMatchers.anyLong())).thenReturn(true);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/orders/3323452")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(true)));
    }

    private OrderDto createOrderDto() {
        return OrderDto.builder()
                .id(3L)
                .coupon(createCouponDto())
                .user(createUserDto())
                .build();
    }

    private UserDto createUserDto() {
        return UserDto.builder()
                .id(123L)
                .firstName("Test first name")
                .lastName("Test last name")
                .login("Test")
                .password("Test password")
                .role("ROLE_ADMIN")
                .email("test@test.pl")
                .userStrategy(UserStrategyFactory.NORMAL_STRATEGY)
                .subscription(true)
                .money("1200")
                .orders(new ArrayList<>())
                .build();
    }

    private CouponTypeDto createCouponTypeDto() {
        return CouponTypeDto.builder()
                .id(2303L)
                .matchType("HOME_TEAM")
                .status("WAITING")
                .match(createMatchDto())
                .build();
    }

    private MatchDto createMatchDto() {
        return MatchDto.builder()
                .id(832983L)
                .footballId(11234L)
                .homeTeam("HOME_TEAM")
                .awayTeam("AWAY_TEAM")
                .competitionId(-202L)
                .seasonId(-203L)
                .homeTeamPositionInTable(2)
                .awayTeamPositionInTable(4)
                .homeTeamChance(60.0)
                .awayTeamChance(40.0)
                .round(23)
                .matchDay(new MatchDayDto())
                .matchScore(new MatchScoreDto())
                .weather(new WeatherDto())
                .couponTypeList(new ArrayList<>())
                .build();
    }

    private CouponDto createCouponDto() {
        List<CouponTypeDto> couponTypeList = new ArrayList<>();
        couponTypeList.add(createCouponTypeDto());
        couponTypeList.add(createCouponTypeDto());
        return CouponDto.builder()
                .id(302L)
                .couponTypeList(couponTypeList)
                .rate(20.0)
                .build();
    }
}