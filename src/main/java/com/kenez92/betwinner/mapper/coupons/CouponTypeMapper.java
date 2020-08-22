package com.kenez92.betwinner.mapper.coupons;

import com.kenez92.betwinner.domain.coupons.CouponTypeDto;
import com.kenez92.betwinner.entity.coupons.CouponType;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper
public interface CouponTypeMapper {
    CouponType mapToCouponType(CouponTypeDto couponTypeDto);

    CouponTypeDto mapToCouponTypeDto(CouponType couponType);

    default List<CouponTypeDto> mapToCouponTypeDtoList(List<CouponType> couponTypeList) {
        if (couponTypeList == null) {
            return new ArrayList<>();
        } else {
            return new ArrayList<>(couponTypeList).stream()
                    .map(this::mapToCouponTypeDto)
                    .collect(Collectors.toList());
        }
    }
}
