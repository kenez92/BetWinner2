package com.kenez92.betwinner.service.coupons;

import com.kenez92.betwinner.common.enums.CouponStatus;
import com.kenez92.betwinner.common.enums.MatchType;
import com.kenez92.betwinner.domain.coupons.CouponTypeDto;
import com.kenez92.betwinner.domain.matches.MatchScoreDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.persistence.entity.Coupon;
import com.kenez92.betwinner.persistence.entity.coupons.CouponType;
import com.kenez92.betwinner.persistence.entity.matches.Match;
import com.kenez92.betwinner.persistence.entity.matches.MatchDay;
import com.kenez92.betwinner.persistence.entity.matches.MatchScore;
import com.kenez92.betwinner.persistence.entity.matches.MatchStats;
import com.kenez92.betwinner.persistence.entity.weather.Weather;
import com.kenez92.betwinner.persistence.repository.coupons.CouponTypeRepository;
import com.kenez92.betwinner.service.matches.MatchScoreService;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CouponTypeServiceTestSuite {
    @Autowired
    private CouponTypeService couponTypeService;

    @MockBean
    private CouponTypeRepository couponTypeRepository;

    @MockBean
    private MatchScoreService matchScoreService;

    @Test
    public void testGetCouponType() {
        //Given
        Mockito.when(couponTypeRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(createCouponType()));
        //When
        CouponTypeDto couponTypeDto = couponTypeService.getCouponType(2303L);
        //Then
        Assert.assertEquals(2303L, couponTypeDto.getId(), 0.01);
        Assert.assertEquals("HOME_TEAM", couponTypeDto.getMatchType().toString());
        Assert.assertEquals("ACTIVE", couponTypeDto.getCouponStatus().toString());
    }

    @Test
    public void testGetCouponTypeShouldThrowException() {
        //Given
        Mockito.when(couponTypeRepository.findById(ArgumentMatchers.anyLong()))
                .thenThrow(new BetWinnerException(BetWinnerException.ERR_COUPON_TYPE_NOT_FOUND_EXCEPTION));
        //When & Then
        Assertions.assertThrows(BetWinnerException.class, () -> couponTypeService.getCouponType(234L));
    }

    @Test
    public void testGetCouponTypes() {
        //Given
        List<CouponType> couponTypeList = new ArrayList<>();
        couponTypeList.add(createCouponType());
        couponTypeList.add(createCouponType());
        couponTypeList.add(createCouponType());
        Mockito.when(couponTypeRepository.findAll()).thenReturn(couponTypeList);
        //When
        List<CouponTypeDto> couponTypeDtoList = couponTypeService.getCouponTypes();
        //then
        Assert.assertNotNull(couponTypeDtoList);
        Assert.assertEquals(3, couponTypeDtoList.size());
    }

    @Test
    public void testCreateCouponType() {
        //Given
        Mockito.when(couponTypeRepository.save(ArgumentMatchers.any(CouponType.class)))
                .thenReturn(createCouponType());
        //When
        CouponTypeDto couponTypeDto = couponTypeService.createCouponType(new CouponTypeDto());
        //Then
        Assert.assertEquals(2303L, couponTypeDto.getId(), 0.01);
        Assert.assertEquals("HOME_TEAM", couponTypeDto.getMatchType().toString());
        Assert.assertEquals("ACTIVE", couponTypeDto.getCouponStatus().toString());
    }

    @Test
    public void testDeleteCouponTypeShouldReturnTrue() {
        //Given
        Mockito.when(couponTypeRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);
        //When
        boolean result = couponTypeService.deleteCouponType(232L);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testDeleteCouponTypeShouldReturnFalse() {
        //Given
        Mockito.when(couponTypeRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
        //When
        boolean result = couponTypeService.deleteCouponType(232L);
        //Then
        Assert.assertFalse(result);
    }

    @Test
    public void testUpdateCouponType() {
        //Given
        Mockito.when(couponTypeRepository.save(ArgumentMatchers.any(CouponType.class))).thenReturn(createCouponType());
        //When
        CouponTypeDto couponTypeDto = couponTypeService.updateCouponType(new CouponTypeDto());
        //Then
        Assert.assertEquals(2303L, couponTypeDto.getId(), 0.01);
        Assert.assertEquals("HOME_TEAM", couponTypeDto.getMatchType().toString());
        Assert.assertEquals("ACTIVE", couponTypeDto.getCouponStatus().toString());

    }

    private CouponType createCouponType() {
        return CouponType.builder()
                .id(2303L)
                .matchType(MatchType.HOME_TEAM)
                .couponStatus(CouponStatus.ACTIVE)
                .match(createMatch())
                .coupon(createCoupon())
                .build();
    }

    private Match createMatch() {
        return com.kenez92.betwinner.persistence.entity.matches.Match.builder()
                .id(832983L)
                .footballId(11234L)
                .homeTeam("HOME_TEAM")
                .awayTeam("AWAY_TEAM")
                .competitionId(-202L)
                .seasonId(-203L)
                .date(new Date())
                .matchStats(new MatchStats())
                .round(23)
                .matchDay(new MatchDay())
                .matchScore(new MatchScore())
                .weather(new Weather())
                .couponTypeList(new ArrayList<>())
                .build();
    }

    private Coupon createCoupon() {
        return Coupon.builder()
                .id(302L)
                .couponTypeList(new ArrayList<>())
                .build();
    }

    private MatchScoreDto createMatchScoreDto() {
        return MatchScoreDto.builder()
                .id(1234L)
                .footballMatchId(122L)
                .winner("HOME_TEAM")
                .status("FINISHED")
                .duration("REGULAR")
                .fullTimeHomeTeam(1)
                .fullTimeAwayTeam(2)
                .halfTimeHomeTeam(1)
                .halfTimeAwayTeam(0)
                .extraTimeHomeTeam(1)
                .extraTimeAwayTeam(1)
                .penaltiesHomeTeam(2)
                .penaltiesAwayTeam(1)
                .build();
    }
}
