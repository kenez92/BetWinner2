package com.kenez92.betwinner.service;

import com.kenez92.betwinner.domain.CouponDto;
import com.kenez92.betwinner.domain.matches.MatchDayDto;
import com.kenez92.betwinner.domain.matches.MatchDto;
import com.kenez92.betwinner.domain.matches.MatchScoreDto;
import com.kenez92.betwinner.domain.matches.WeatherDto;
import com.kenez92.betwinner.entity.Coupon;
import com.kenez92.betwinner.entity.matches.Match;
import com.kenez92.betwinner.entity.matches.MatchDay;
import com.kenez92.betwinner.entity.matches.MatchScore;
import com.kenez92.betwinner.entity.matches.Weather;
import com.kenez92.betwinner.mapper.CouponMapper;
import com.kenez92.betwinner.repository.CouponRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class CouponServiceTestSuite {
    private final static String HOME_TEAM = "home team";
    private final static String AWAY_TEAM = "away team";
    @InjectMocks
    private CouponService couponService;
    @Mock
    private CouponRepository couponRepository;
    @Spy
    private CouponDto couponDto;
    @Spy
    private CouponMapper couponMapper;


    @Test
    public void testMapper() {
        Coupon coupon = createCoupon();
        CouponDto couponDto = couponMapper.mapToCouponDto(coupon);

        System.out.println(couponDto);
    }
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


    }

    private Coupon createCoupon() {
        return Coupon.builder()
                .id(-5L)
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