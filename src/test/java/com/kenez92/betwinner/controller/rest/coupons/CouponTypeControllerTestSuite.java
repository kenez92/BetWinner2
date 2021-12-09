package com.kenez92.betwinner.controller.rest.coupons;

import com.google.gson.Gson;
import com.kenez92.betwinner.coupons.CouponDto;
import com.kenez92.betwinner.common.enums.MatchType;
import com.kenez92.betwinner.common.enums.CouponStatus;
import com.kenez92.betwinner.couponTypes.CouponTypeDto;
import com.kenez92.betwinner.weather.WeatherDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.match.MatchDto;
import com.kenez92.betwinner.matchDay.MatchDayDto;
import com.kenez92.betwinner.matchScore.MatchScoreDto;
import com.kenez92.betwinner.matchStats.MatchStatsDto;
import com.kenez92.betwinner.couponTypes.CouponTypeService;
import org.hamcrest.Matchers;
import org.junit.Assert;
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
import java.util.Date;
import java.util.List;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class CouponTypeControllerTestSuite {
    private final static String URL = "/v1/coupons/types";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CouponTypeService couponTypeService;

    @Test
    @WithMockUser(username = "admin")
    public void shouldFetchEmptyList() throws Exception {
        //Given
        Mockito.when(couponTypeService.getCouponTypes()).thenReturn(new ArrayList<>());
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/coupons/types")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testGeCouponTypes() throws Exception {
        //Given
        List<CouponTypeDto> couponTypeDtoList = new ArrayList<>();
        couponTypeDtoList.add(createCouponTypeDto());
        couponTypeDtoList.add(createCouponTypeDto());
        Mockito.when(couponTypeService.getCouponTypes()).thenReturn(couponTypeDtoList);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/coupons/types")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id", Matchers.is(2303)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].matchType", Matchers.is("HOME_TEAM")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].couponStatus", Matchers.is("ACTIVE")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].match.id", Matchers.is(832983)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].coupon.id", Matchers.is(302)));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testGetCouponType() throws Exception {
        //Given
        Mockito.when(couponTypeService.getCouponType(ArgumentMatchers.anyLong())).thenReturn(createCouponTypeDto());
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/coupons/types/33")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(2303)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchType", Matchers.is("HOME_TEAM")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.couponStatus", Matchers.is("ACTIVE")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.match.id", Matchers.is(832983)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coupon.id", Matchers.is(302)));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testGetCouponTypeShouldThrowBetWinnerExceptionWhenNotFound() throws Exception {
        //Given
        Mockito.when(couponTypeService.getCouponType(ArgumentMatchers.anyLong()))
                .thenThrow(new BetWinnerException(BetWinnerException.ERR_COUPON_TYPE_NOT_FOUND_EXCEPTION));
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/coupons/types/33")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> Assert.assertTrue(result.getResolvedException() instanceof BetWinnerException))
                .andExpect(result -> Assert.assertEquals(BetWinnerException.ERR_COUPON_TYPE_NOT_FOUND_EXCEPTION,
                        result.getResolvedException().getMessage()));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testUpdateCouponType() throws Exception {
        //Given
        Mockito.when(couponTypeService.updateCouponType(ArgumentMatchers.any(CouponTypeDto.class)))
                .thenReturn(createCouponTypeDto());
        Gson gson = new Gson();
        String jsonContent = gson.toJson(createCouponDto());
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/coupons/types/")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(2303)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchType", Matchers.is("HOME_TEAM")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.couponStatus", Matchers.is("ACTIVE")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.match.id", Matchers.is(832983)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coupon.id", Matchers.is(302)));
    }

    @Test
    @WithMockUser(username = "admin")
    public void testDeleteCouponType() throws Exception {
        //Given
        Mockito.when(couponTypeService.deleteCouponType(ArgumentMatchers.anyLong())).thenReturn(true);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/coupons/types/33")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(true)));
    }

    private CouponTypeDto createCouponTypeDto() {
        return CouponTypeDto.builder()
                .id(2303L)
                .matchType(MatchType.HOME_TEAM)
                .couponStatus(CouponStatus.ACTIVE)
                .match(createMatchDto())
                .coupon(createCouponDto())
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
                .date(new Date())
                .matchStats(new MatchStatsDto())
                .round(23)
                .matchDay(new MatchDayDto())
                .matchScore(new MatchScoreDto())
                .weather(new WeatherDto())
                .couponTypeList(new ArrayList<>())
                .build();
    }

    private CouponDto createCouponDto() {
        return CouponDto.builder()
                .id(302L)
                .couponTypeList(new ArrayList<>())
                .build();
    }
}
