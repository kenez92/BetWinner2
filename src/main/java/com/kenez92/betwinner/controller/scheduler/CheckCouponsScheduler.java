package com.kenez92.betwinner.controller.scheduler;

import com.kenez92.betwinner.couponTypes.CouponTypeService;
import com.kenez92.betwinner.coupons.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CheckCouponsScheduler {
    private final CouponService couponService;
    private final CouponTypeService couponTypeService;


    @Scheduled(cron = "0 0 1,23 * * *")
    public void checkTypes() {
        log.info("Start checking coupon types");
        couponTypeService.checkCouponTypes();
        log.info("Coupon types checked");

        log.info("Start checking coupons");
        couponService.checkActiveCoupons();
        log.info("Finished checking coupons");
    }
}
