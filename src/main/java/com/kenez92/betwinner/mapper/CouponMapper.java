package com.kenez92.betwinner.mapper;

import com.kenez92.betwinner.domain.CouponDto;
import com.kenez92.betwinner.mapper.coupons.CouponTypeMapper;
import com.kenez92.betwinner.persistence.entity.Coupon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper(uses = {UserMapper.class, CouponTypeMapper.class})
public interface CouponMapper {

    Coupon mapToCoupon(CouponDto couponDto);

    @Mapping(target = "couponTypeList", qualifiedByName = "couponTypeDtosForCouponDto")
    @Mapping(target = "user", qualifiedByName = "userDtoForCouponDto")
    CouponDto mapToCouponDto(Coupon coupon);

    @Mapping(target = "couponTypeList", qualifiedByName = "couponTypeDtosForCouponDto")
    @Mapping(target = "user", ignore = true)
    CouponDto mapToCouponDtoForUser(Coupon coupon);

    @Named(value = "couponDtoForCouponTypeDto")
    @Mapping(target = "couponTypeList", ignore = true)
    @Mapping(target = "user", qualifiedByName = "userDtoForCouponDto")
    CouponDto mapToCouponDtoForCouponType(Coupon coupon);

    default List<CouponDto> mapToCouponDtoList(List<Coupon> couponList) {
        if (couponList == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(couponList).stream()
                .map(this::mapToCouponDto)
                .collect(Collectors.toList());
    }

    @Named("couponDtosForUser")
    default List<CouponDto> mapToCouponDtosForUser(final List<Coupon> couponList) {
        if (couponList == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(couponList).stream()
                .map(this::mapToCouponDtoForUser)
                .collect(Collectors.toList());
    }
}
