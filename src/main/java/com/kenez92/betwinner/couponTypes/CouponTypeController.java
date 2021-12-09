package com.kenez92.betwinner.couponTypes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/coupons/types")
public class CouponTypeController {
    private final CouponTypeService couponTypeService;

    @GetMapping(value = "/{couponId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CouponTypeDto getCouponType(@PathVariable Long couponId) {
        log.info("Getting coupon type id :{}", couponId);
        CouponTypeDto couponTypeDto = couponTypeService.getCouponType(couponId);
        log.info("Return coupon type: {}", couponTypeDto);
        return couponTypeDto;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CouponTypeDto> getCouponTypes() {
        log.info("Getting coupon types");
        List<CouponTypeDto> couponTypeDtoList = couponTypeService.getCouponTypes();
        log.info("Return coupon types: {}", couponTypeDtoList);
        return couponTypeDtoList;
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CouponTypeDto updateCouponType(@RequestBody CouponTypeDto couponTypeDto) {
        log.info("Updating coupon type id: {}", couponTypeDto.getId());
        CouponTypeDto updatedCouponTypeDto = couponTypeService.updateCouponType(couponTypeDto);
        log.info("Return updated coupon type: {}", updatedCouponTypeDto);
        return updatedCouponTypeDto;
    }

    @DeleteMapping(value = "/{couponTypeId}")
    public boolean deleteCouponType(@PathVariable Long couponTypeId) {
        log.info("Deleting coupon type id: {}", couponTypeId);
        boolean result = couponTypeService.deleteCouponType(couponTypeId);
        if (result) {
            log.info("Deleted coupon type id: {}", couponTypeId);
        } else {
            log.info("Coupon type not deleted id: {}", couponTypeId);
        }
        return result;
    }

    @GetMapping(value = "/checkTypes")
    public void checkTypes(@AuthenticationPrincipal UsernamePasswordAuthenticationToken user) {
        UserDetails userDetails = (UserDetails) user.getPrincipal();
        couponTypeService.checkCouponTypes();
    }
}
