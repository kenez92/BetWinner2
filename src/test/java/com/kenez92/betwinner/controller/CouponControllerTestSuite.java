package com.kenez92.betwinner.controller;

import com.google.gson.Gson;
import com.kenez92.betwinner.common.enums.CouponStatus;
import com.kenez92.betwinner.common.enums.MatchType;
import com.kenez92.betwinner.common.enums.UserStrategy;
import com.kenez92.betwinner.domain.CouponDto;
import com.kenez92.betwinner.domain.UserDto;
import com.kenez92.betwinner.domain.coupons.CouponTypeDto;
import com.kenez92.betwinner.domain.matches.MatchDayDto;
import com.kenez92.betwinner.domain.matches.MatchDto;
import com.kenez92.betwinner.domain.matches.MatchScoreDto;
import com.kenez92.betwinner.domain.matches.MatchStatsDto;
import com.kenez92.betwinner.domain.weather.WeatherDto;
import com.kenez92.betwinner.service.CouponService;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
public class CouponControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CouponService couponService;

    @Test
    @WithMockUser(username = "admin")
    public void testGetCoupons() throws Exception {
        //Given
        List<CouponDto> couponDtoList = new ArrayList<>();
        couponDtoList.add(createCouponDto());
        couponDtoList.add(createCouponDto());
        Mockito.when(couponService.getUserCoupons(ArgumentMatchers.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(couponDtoList);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/coupons")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testGetCoupon() throws Exception {
        //Given
        Mockito.when(couponService.getCoupon(ArgumentMatchers.anyLong(),
                ArgumentMatchers.any(UsernamePasswordAuthenticationToken.class))).thenReturn(createCouponDto());
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/coupons/33")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(302)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.couponTypeList", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.couponTypeList.[0].id", Matchers.is(2303)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.couponTypeList.[0].matchType", Matchers.is("HOME_TEAM")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.couponTypeList.[0].couponStatus", Matchers.is("ACTIVE")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.couponTypeList.[0].match.id", Matchers.is(832983)));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testCreateEmptyCoupon() throws Exception {
        //Given
        CouponDto couponDto = CouponDto.builder()
                .course(0.0)
                .rate(0.0)
                .result(0.0)
                .couponStatus("WAITING")
                .user(createUserDto())
                .build();
        Mockito.when(couponService.createEmptyCouponOrReturnOpenCoupon(ArgumentMatchers.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(couponDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(couponDto);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/coupons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
                .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.course", Matchers.is(0.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rate", Matchers.is(0.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result", Matchers.is(0.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.couponStatus", Matchers.is("WAITING")));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testAddMatch() throws Exception {
        //Given
        Mockito.when(couponService.addMatch(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(),
                ArgumentMatchers.any(MatchType.class), ArgumentMatchers.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(createCouponDto());
        Gson gson = new Gson();
        String jsonContent = gson.toJson(MatchType.HOME_TEAM);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/coupons/333/545")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
                .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "admin")
    public void testSetRate() throws Exception {
        //Given
        Mockito.when(couponService.setRate(ArgumentMatchers.anyLong(), ArgumentMatchers.anyDouble()))
                .thenReturn(createCouponDto());
        Gson gson = new Gson();
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/coupons/333/1000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testDeleteCoupon() throws Exception {
        //Given
        Mockito.when(couponService.deleteCoupon(ArgumentMatchers.anyLong())).thenReturn(true);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/coupons/333")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    private CouponTypeDto createCouponTypeDto() {
        return CouponTypeDto.builder()
                .id(2303L)
                .matchType(MatchType.HOME_TEAM)
                .couponStatus(CouponStatus.ACTIVE)
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
                .matchStats(new MatchStatsDto())
                .round(23)
                .matchDay(new MatchDayDto())
                .matchScore(new MatchScoreDto())
                .weather(new WeatherDto())
                .couponTypeList(new ArrayList<>())
                .build();
    }

    private CouponDto createCouponDto() {
        List<CouponTypeDto> couponTypeDtoList = new ArrayList<>();
        couponTypeDtoList.add(createCouponTypeDto());
        couponTypeDtoList.add(createCouponTypeDto());
        return CouponDto.builder()
                .id(302L)
                .couponTypeList(couponTypeDtoList)
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
                .userStrategy(UserStrategy.NORMAL_STRATEGY)
                .subscription(true)
                .coupons(new ArrayList<>())
                .build();
    }
}
