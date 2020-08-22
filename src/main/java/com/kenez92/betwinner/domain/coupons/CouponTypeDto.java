package com.kenez92.betwinner.domain.coupons;

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
    private String matchType;
    private MatchDto match;
    private CouponDto coupon;
}
