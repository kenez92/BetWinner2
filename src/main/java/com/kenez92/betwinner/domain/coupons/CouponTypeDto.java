package com.kenez92.betwinner.domain.coupons;

import com.kenez92.betwinner.common.enums.CouponStatus;
import com.kenez92.betwinner.common.enums.MatchType;
import com.kenez92.betwinner.domain.CouponDto;
import com.kenez92.betwinner.domain.matches.MatchDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CouponTypeDto {
    private Long id;
    private MatchType matchType;
    private MatchDto match;
    private CouponStatus couponStatus;
    private CouponDto coupon;
}
