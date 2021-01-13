package com.kenez92.betwinner.mapper;

import com.kenez92.betwinner.domain.CouponDto;
import com.kenez92.betwinner.persistence.entity.Coupon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper(uses = UserMapper.class)
public interface CouponMapper {

    Coupon mapToCoupon(CouponDto couponDto);

    @Mapping(target = "couponTypeList", ignore = true)
    CouponDto mapToCouponDto(Coupon coupon);

    default List<CouponDto> mapToCouponDtoList(List<Coupon> couponList) {
        if (couponList == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(couponList).stream()
                .map(this::mapToCouponDto)
                .collect(Collectors.toList());
    }
}
