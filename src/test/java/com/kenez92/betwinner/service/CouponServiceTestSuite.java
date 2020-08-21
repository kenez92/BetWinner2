package com.kenez92.betwinner.service;

import com.kenez92.betwinner.domain.CouponDto;
import com.kenez92.betwinner.entity.Coupon;
import com.kenez92.betwinner.entity.matches.Match;
import com.kenez92.betwinner.entity.matches.MatchDay;
import com.kenez92.betwinner.entity.matches.MatchScore;
import com.kenez92.betwinner.entity.matches.Weather;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.repository.CouponRepository;
import com.kenez92.betwinner.repository.matches.MatchRepository;
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
    @MockBean
    private MatchRepository matchRepository;

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
        Assert.assertEquals(3, tmpCoupon.getMatchList().size());
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
    public void testCreateCoupon() {
        //Given
        Mockito.when(couponRepository.save(ArgumentMatchers.any(Coupon.class))).thenReturn(new Coupon());
        //When
        CouponDto tmpCouponDto = couponService.createCoupon(new CouponDto());
        //Then
        Assert.assertNotNull(tmpCouponDto);
    }


    private Coupon createCoupon() {
        return Coupon.builder()
                .id(-5L)
                .course(20.2d)
                .rate(10.0)
                .result(202d)
                .matchList(createMatchList())
                .build();
    }

    private List<Match> createMatchList() {
        List<Match> matchList = new ArrayList<>();
        matchList.add(createMatch());
        matchList.add(createMatch());
        matchList.add(createMatch());
        return matchList;
    }

    private Match createMatch() {
        return Match.builder()
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
                .couponList(new ArrayList<>())
                .build();
    }
}