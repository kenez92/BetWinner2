package com.kenez92.betwinner.domain;

import com.kenez92.betwinner.entity.Coupon;
import com.kenez92.betwinner.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    private User user;
    private Coupon coupon;
}
