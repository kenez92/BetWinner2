package com.kenez92.betwinner.domain.coupons;

import com.kenez92.betwinner.domain.CouponDto;
import com.kenez92.betwinner.domain.matches.MatchDto;

public class CouponTypeDto {
    private Long id;
    private String matchType;
    private MatchDto match;
    private CouponDto coupon;
}
