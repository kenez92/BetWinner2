package com.kenez92.betwinner.couponTypes;

import com.kenez92.betwinner.coupons.CouponMapper;
import com.kenez92.betwinner.match.MatchMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper(uses = {CouponMapper.class, MatchMapper.class})
public interface CouponTypeMapper {
    CouponType mapToCouponType(CouponTypeDto couponTypeDto);

    @Mapping(target = "coupon", qualifiedByName = "couponDtoForCouponTypeDto")
    @Mapping(target = "match", qualifiedByName = "matchDtoForCouponTypeDto")
    CouponTypeDto mapToCouponTypeDto(CouponType couponType);

    @Mapping(target = "match", ignore = true)
    @Mapping(target = "coupon", qualifiedByName = "couponDtoForCouponTypeDto")
    CouponTypeDto mapToCouponTypeDtoForMatch(final CouponType couponType);

    @Mapping(target = "match", qualifiedByName = "matchDtoForCouponTypeDto")
    @Mapping(target = "coupon", ignore = true)
    CouponTypeDto mapToCouponTypeDtoForCoupon(final CouponType couponType);

    default List<CouponTypeDto> mapToCouponTypeDtoList(List<CouponType> couponTypeList) {
        if (couponTypeList == null) {
            return new ArrayList<>();
        } else {
            return new ArrayList<>(couponTypeList).stream()
                    .map(this::mapToCouponTypeDto)
                    .collect(Collectors.toList());
        }
    }

    @Named(value = "couponTypeDtosForCouponDto")
    default List<CouponTypeDto> mapToCouponTypeDtosForCouponDto(List<CouponType> couponTypeList) {
        if (couponTypeList == null) {
            return new ArrayList<>();
        } else {
            return new ArrayList<>(couponTypeList).stream()
                    .map(this::mapToCouponTypeDtoForCoupon)
                    .collect(Collectors.toList());
        }
    }

    @Named(value = "couponTypeDtosForMatchDto")
    default List<CouponTypeDto> mapToCouponTypeDtosForMatchDto(List<CouponType> couponTypeList) {
        if (couponTypeList == null) {
            return new ArrayList<>();
        } else {
            return new ArrayList<>(couponTypeList).stream()
                    .map(this::mapToCouponTypeDtoForMatch)
                    .collect(Collectors.toList());
        }
    }
}
