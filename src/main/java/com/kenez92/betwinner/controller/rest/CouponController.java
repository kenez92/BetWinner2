package com.kenez92.betwinner.controller.rest;

import com.kenez92.betwinner.common.enums.CouponStatus;
import com.kenez92.betwinner.common.enums.MatchType;
import com.kenez92.betwinner.domain.CouponDto;
import com.kenez92.betwinner.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/coupons")
public class CouponController {
    private final CouponService couponService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CouponDto> getAllUserCoupons(@AuthenticationPrincipal UsernamePasswordAuthenticationToken user) {
        log.info("Getting all coupons");
        List<CouponDto> couponDtoList = couponService.getUserCoupons(user);
        log.info("Return all coupons: {}", couponDtoList);
        return couponDtoList;
    }

    @GetMapping(value = "/{couponId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CouponDto getCoupon(@PathVariable Long couponId, @AuthenticationPrincipal UsernamePasswordAuthenticationToken user) {
        log.info("Getting coupon by id: {}", couponId);
        CouponDto couponDto = couponService.getCoupon(couponId, user);
        log.info("Return coupon: {}", couponDto);
        return couponDto;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CouponDto createEmptyCoupon(@AuthenticationPrincipal UsernamePasswordAuthenticationToken user) {
        log.info("Creating empty coupon for user :{}", user.getPrincipal());
        CouponDto couponDto = couponService.createEmptyCouponOrReturnOpenCoupon(user);
        log.info("Return created coupon: {}", couponDto);
        return couponDto;
    }

    @PutMapping(value = "/check/{couponId}")
    public void checkCoupon(@PathVariable Long couponId) {
        log.info("Checking coupon id: {}", couponId);
        couponService.checkCoupon(couponId);
    }

    @PostMapping(value = "/{couponId}/{matchId}")
    public CouponDto addMatch(@PathVariable Long couponId,
                              @PathVariable Long matchId,
                              @RequestBody MatchType matchType,
                              @AuthenticationPrincipal UsernamePasswordAuthenticationToken user) {
        log.info("Adding match to coupon id: {}, matchId: {}, matchType: {}", couponId, matchId, matchType);
        CouponDto couponDto = couponService.addMatch(couponId, matchId, matchType, user);
        log.info("Return coupon: {}", couponDto);
        return couponDto;
    }

    @PutMapping(value = "/{couponId}/{rate}")
    public CouponDto setRate(@PathVariable Long couponId, @PathVariable Double rate) {
        log.info("Set rate of the coupon id / rate : {}{}", couponId, rate);
        CouponDto couponDto = couponService.setRate(couponId, rate);
        log.info("Return coupon after setting rate: {}", couponDto);
        return couponDto;
    }

    @PostMapping(value = "/{couponId}")
    public void activeCoupon(@PathVariable Long couponId, @AuthenticationPrincipal UsernamePasswordAuthenticationToken user) {
        log.info("Activating coupon id : {}", couponId);
        couponService.activeCoupon(couponId, user);
        log.info("Coupon activated: {}", couponId);
    }

    @DeleteMapping("/{couponId}")
    public boolean deleteCoupon(@PathVariable Long couponId) {
        log.info("Deleting coupon id: {}", couponId);
        boolean result = couponService.deleteCoupon(couponId);
        if (result) {
            log.info("Coupon deleted id: {}", couponId);
            return true;
        } else {
            log.info("Coupon not deleted id: {}", couponId);
            return false;
        }
    }

    @DeleteMapping("/{couponId}/{couponTypeId}")
    public boolean deleteMatchFromCoupon(@PathVariable Long couponId, @PathVariable Long couponTypeId) {
        log.info("Deleting match id: {} from coupon id: {}", couponId, couponTypeId);
        boolean result = couponService.deleteCouponTypeFromCoupon(couponId, couponTypeId);
        log.info("Deleting match type result : {}", result);
        return result;
    }
}
