package com.kenez92.betwinner.service;

import com.kenez92.betwinner.common.enums.MatchType;
import com.kenez92.betwinner.common.enums.UserStrategy;
import com.kenez92.betwinner.coupons.CouponService;
import com.kenez92.betwinner.coupons.CouponDto;
import com.kenez92.betwinner.common.enums.CouponStatus;
import com.kenez92.betwinner.users.UserRole;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.coupons.Coupon;
import com.kenez92.betwinner.users.User;
import com.kenez92.betwinner.couponTypes.CouponType;
import com.kenez92.betwinner.match.Match;
import com.kenez92.betwinner.matchDay.MatchDay;
import com.kenez92.betwinner.matchScore.MatchScore;
import com.kenez92.betwinner.matchStats.MatchStats;
import com.kenez92.betwinner.weather.Weather;
import com.kenez92.betwinner.coupons.CouponRepository;
import com.kenez92.betwinner.users.UserRepository;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

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
    private UserRepository userRepository;

    @Test
    public void testGetCoupon() {
        //Given
        Coupon coupon = createCoupon();
        Long number = coupon.getId();
        Mockito.when(couponRepository.getCouponWithAllFields(number)).thenReturn(Optional.of(coupon));
        //When
        CouponDto tmpCoupon = couponService.getCoupon(number, new UsernamePasswordAuthenticationToken("Test",""));
        //Then
        Assert.assertEquals(number, tmpCoupon.getId());
        Assert.assertEquals(coupon.getCourse(), tmpCoupon.getCourse());
        Assert.assertEquals(coupon.getRate(), tmpCoupon.getRate());
        Assert.assertEquals(coupon.getResult(), tmpCoupon.getResult());
    }

    @Test
    public void testGetCouponShouldThrowBetWinnerExceptionWhenCouponNotFound() {
        //Given
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken("","");
        //When
        Mockito.when(couponRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        //Then
        Assertions.assertThrows(BetWinnerException.class, () -> couponService.getCoupon(6L, user));
    }

    @Test
    public void testGetCoupons() {
        //Given
        List<Coupon> couponList = new ArrayList<>();
        couponList.add(createCoupon());
        couponList.add(createCoupon());
        couponList.add(createCoupon());
        Mockito.when(userRepository.findByLogin(ArgumentMatchers.anyString())).thenReturn(Optional.ofNullable(createUser()));
        Mockito.when(couponRepository.findAllByUser(ArgumentMatchers.any(User.class))).thenReturn(couponList);
        //When
        List<CouponDto> couponDtoList = couponService.getUserCoupons(new UsernamePasswordAuthenticationToken("test", "test"));
        //Then
        Assert.assertNotNull(couponDtoList);
        Assert.assertEquals(3, couponDtoList.size());
    }

    @Test
    public void testCreateEmptyCoupon() {
        //Given
        Mockito.when(couponRepository.save(ArgumentMatchers.any(Coupon.class))).thenReturn(new Coupon());
        Mockito.when(userRepository.findByLogin(ArgumentMatchers.anyString())).thenReturn(Optional.ofNullable(createUser()));
        //When
        CouponDto tmpCouponDto = couponService.createEmptyCouponOrReturnOpenCoupon(new UsernamePasswordAuthenticationToken("test", "test"));
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
                .user(createUser())
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
                        .matchStats(new MatchStats())
                        .round(23)
                        .matchDay(new MatchDay())
                        .matchScore(new MatchScore())
                        .weather(new Weather())
                        .couponTypeList(new ArrayList<>())
                        .build())
                .coupon(Coupon.builder()
                        .id(-5L)
                        .build())
                .couponStatus(CouponStatus.ACTIVE)
                .build();
    }

    private User createUser() {
        return User.builder()
                .id(123L)
                .firstName("Test first name")
                .lastName("Test last name")
                .login("Test")
                .password("Test password")
                .role(UserRole.ROLE_ADMIN)
                .email("test@test.pl")
                .userStrategy(UserStrategy.NORMAL_STRATEGY)
                .subscription(true)
                .coupons(new ArrayList<>())
                .build();
    }
}
