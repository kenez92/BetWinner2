package com.kenez92.betwinner.controller;

import com.kenez92.betwinner.domain.CouponDto;
import com.kenez92.betwinner.domain.Status;
import com.kenez92.betwinner.domain.coupons.CouponTypeDto;
import com.kenez92.betwinner.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/coupons")
public class CouponController {
    private final CouponService couponService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CouponDto> getCoupons() {
        log.info("Getting all coupons");
        List<CouponDto> couponDtoList = couponService.getCoupons();
        log.info("Return all coupons: {}", couponDtoList);
        return couponDtoList;
    }

    @GetMapping(value = "/{couponId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CouponDto getCoupon(@PathVariable Long couponId) {
        log.info("Getting coupon by id: {}", couponId);
        CouponDto couponDto = couponService.getCoupon(couponId);
        log.info("Return coupon: {}", couponDto);
        return couponDto;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CouponDto createEmptyCoupon() {
        log.info("Creating empty coupon");
        CouponDto couponDto = couponService.createEmptyCoupon();
        log.info("Return created coupon: {}", couponDto);
        return couponDto;
    }

    @PutMapping(value = "/check/{couponId}")
    public Status checkCoupon(@PathVariable Long couponId) {
        log.info("Checking coupon id: {}", couponId);
        Status result = couponService.checkCoupon(couponId);
        log.info("Result of the coupon with id : {}{}", couponId, " :" + result);
        return result;
    }

    @PutMapping(value = "/{couponId}")
    public CouponDto addMatch(@PathVariable Long couponId, @RequestBody CouponTypeDto couponTypeDto) {
        log.info("Adding coupon type to coupon id: {}{}", couponId, couponTypeDto);
        CouponDto couponDto = couponService.addMatch(couponId, couponTypeDto);
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
}
