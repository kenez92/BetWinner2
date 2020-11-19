package com.kenez92.betwinner.service;

import com.kenez92.betwinner.domain.CouponDto;
import com.kenez92.betwinner.common.enums.MatchType;
import com.kenez92.betwinner.domain.Status;
import com.kenez92.betwinner.persistence.entity.Coupon;
import com.kenez92.betwinner.persistence.entity.coupons.CouponType;
import com.kenez92.betwinner.persistence.entity.matches.Match;
import com.kenez92.betwinner.persistence.entity.matches.MatchDay;
import com.kenez92.betwinner.persistence.entity.matches.MatchScore;
import com.kenez92.betwinner.persistence.entity.matches.Weather;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.persistence.repository.CouponRepository;
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
public class CouponServiceTestSuite {
    private final static String HOME_TEAM = "home team";
    private final static String AWAY_TEAM = "away team";
    @Autowired
    private CouponService couponService;
    @MockBean
    private CouponRepository couponRepository;

    @Test
    public void testGetCoupon() {
        //Given
        Coupon coupon = createCoupon();
        Long number = coupon.getId();
        Mockito.when(couponRepository.findById(number)).thenReturn(Optional.of(coupon));
        //When
        CouponDto tmpCoupon = couponService.getCoupon(number);
        //Then
        Assert.assertEquals(number, tmpCoupon.getId());
        Assert.assertEquals(coupon.getCourse(), tmpCoupon.getCourse());
        Assert.assertEquals(coupon.getRate(), tmpCoupon.getRate());
        Assert.assertEquals(coupon.getResult(), tmpCoupon.getResult());
    }

    @Test
    public void testGetCouponShouldThrowBetWinnerExceptionWhenCouponNotFound() {
        //Given
        //When
        Mockito.when(couponRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        //Then
        Assertions.assertThrows(BetWinnerException.class, () -> couponService.getCoupon(6L));
    }

    @Test
    public void testGetCoupons() {
        //Given
        List<Coupon> couponList = new ArrayList<>();
        couponList.add(createCoupon());
        couponList.add(createCoupon());
        couponList.add(createCoupon());
        Mockito.when(couponRepository.findAll()).thenReturn(couponList);
        //When
        List<CouponDto> couponDtoList = couponService.getCoupons();
        //Then
        Assert.assertNotNull(couponDtoList);
        Assert.assertEquals(3, couponDtoList.size());
    }

    @Test
    public void testCreateEmptyCoupon() {
        //Given
        Mockito.when(couponRepository.save(ArgumentMatchers.any(Coupon.class))).thenReturn(new Coupon());
        //When
        CouponDto tmpCouponDto = couponService.createEmptyCoupon();
        //Then
        Assert.assertNotNull(tmpCouponDto);
    }


    private Coupon createCoupon() {
        return Coupon.builder()
                .id(-5L)
                .course(20.2d)
                .rate(10.0)
                .result(202d)
                .couponTypeList(createCouponTypeList())
                .build();
    }

    private List<CouponType> createCouponTypeList() {
        List<CouponType> couponTypeList = new ArrayList<>();
        couponTypeList.add(createCouponType());
        couponTypeList.add(createCouponType());
        couponTypeList.add(createCouponType());
        return couponTypeList;
    }

    private CouponType createCouponType() {
        return CouponType.builder()
                .matchType(MatchType.HOME_TEAM)
                .match(Match.builder()
                        .footballId(-11234L)
                        .homeTeam(HOME_TEAM)
                        .awayTeam(AWAY_TEAM)
                        .competitionId(-202L)
                        .seasonId(-203L)
                        .date(new Date())
                        .homeTeamPositionInTable(2)
                        .awayTeamPositionInTable(4)
                        .homeTeamChance(60.0)
                        .awayTeamChance(40.0)
                        .round(23)
                        .matchDay(new MatchDay())
                        .matchScore(new MatchScore())
                        .weather(new Weather())
                        .couponTypeList(new ArrayList<>())
                        .build())
                .coupon(Coupon.builder()
                        .id(-5L)
                        .build())
                .status(Status.WAITING)
                .build();
    }
}