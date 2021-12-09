package com.kenez92.betwinner.coupons;

import com.kenez92.betwinner.couponTypes.CouponTypeDto;
import com.kenez92.betwinner.users.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CouponDto {
    private Long id;
    private Double course;
    private Double rate;
    private Double result;
    private String couponStatus;
    private UserDto user;
    private List<CouponTypeDto> couponTypeList;
}
