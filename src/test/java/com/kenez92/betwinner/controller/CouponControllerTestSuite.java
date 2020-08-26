package com.kenez92.betwinner.controller;

import com.google.gson.Gson;
import com.kenez92.betwinner.domain.CouponDto;
import com.kenez92.betwinner.domain.Status;
import com.kenez92.betwinner.domain.coupons.CouponTypeDto;
import com.kenez92.betwinner.domain.matches.MatchDayDto;
import com.kenez92.betwinner.domain.matches.MatchDto;
import com.kenez92.betwinner.domain.matches.MatchScoreDto;
import com.kenez92.betwinner.domain.matches.WeatherDto;
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
    public void testGetCoupons() throws Exception {
        //Given
        List<CouponDto> couponDtoList = new ArrayList<>();
        couponDtoList.add(createCouponDto());
        couponDtoList.add(createCouponDto());
        Mockito.when(couponService.getCoupons()).thenReturn(couponDtoList);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/coupons")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    public void testGetCoupon() throws Exception {
        //Given
        Mockito.when(couponService.getCoupon(ArgumentMatchers.anyLong())).thenReturn(createCouponDto());
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/coupons/33")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(302)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.couponTypeList", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.couponTypeList.[0].id", Matchers.is(2303)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.couponTypeList.[0].matchType", Matchers.is("HOME_TEAM")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.couponTypeList.[0].status", Matchers.is("WAITING")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.couponTypeList.[0].match.id", Matchers.is(832983)));
    }

    @Test
    public void testCreateEmptyCoupon() throws Exception {
        //Given
        CouponDto couponDto = CouponDto.builder()
                .course(0.0)
                .rate(0.0)
                .result(0.0)
                .couponStatus("WAITING")
                .build();
        Mockito.when(couponService.createEmptyCoupon()).thenReturn(couponDto);
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.couponStatus", Matchers.is("WAITING")));
    }

    @Test
    public void testCheckCoupon() throws Exception {
        //Given
        Mockito.when(couponService.checkCoupon(ArgumentMatchers.anyLong())).thenReturn(Status.WAITING);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/coupons/check/234")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is("WAITING")));
    }

    @Test
    public void testAddMatch() throws Exception {
        //Given
        Mockito.when(couponService.addMatch(ArgumentMatchers.anyLong(), ArgumentMatchers.any(CouponTypeDto.class)))
                .thenReturn(createCouponDto());
        Gson gson = new Gson();
        String jsonContent = gson.toJson(createCouponTypeDto());
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/coupons/333")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent)
                .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
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
        List<CouponTypeDto> couponTypeDtoList = new ArrayList<>();
        couponTypeDtoList.add(createCouponTypeDto());
        couponTypeDtoList.add(createCouponTypeDto());
        return CouponDto.builder()
                .id(302L)
                .couponTypeList(couponTypeDtoList)
                .build();
    }

}