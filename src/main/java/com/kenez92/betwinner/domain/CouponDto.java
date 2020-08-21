package com.kenez92.betwinner.domain;

import com.kenez92.betwinner.domain.matches.MatchDto;
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
    private List<MatchDto> matchList;
}
