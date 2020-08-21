package com.kenez92.betwinner.controller;

import com.kenez92.betwinner.domain.CouponDto;
import com.kenez92.betwinner.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("v1/coupons")
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
    public CouponDto createEmptyCoupon(@RequestBody CouponDto couponDto) {
        log.info("Creating coupon: {}", couponDto);
        CouponDto createdCouponDto = couponService.createCoupon(couponDto);
        log.info("Return created coupon: {}", createdCouponDto);
        return createdCouponDto;
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
