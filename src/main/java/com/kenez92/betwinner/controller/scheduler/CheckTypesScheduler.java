package com.kenez92.betwinner.controller.scheduler;

import com.kenez92.betwinner.service.coupons.CouponTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CheckTypesScheduler {

    private final CouponTypeService couponTypeService;
    @Scheduled(cron = "0 0 1,23 * * *")
    public void checkTypes() {
        log.info("Start checking coupon types");
        couponTypeService.checkCouponTypes();
        log.info("Coupon types checked");
    }
}
