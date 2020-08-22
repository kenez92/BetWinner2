package com.kenez92.betwinner.service.coupons;

import com.kenez92.betwinner.domain.coupons.CouponTypeDto;
import com.kenez92.betwinner.entity.coupons.CouponType;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.coupons.CouponTypeMapper;
import com.kenez92.betwinner.repository.coupons.CouponTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponTypeService {
    private final CouponTypeRepository couponTypeRepository;
    private final CouponTypeMapper couponTypeMapper;

    public CouponTypeDto getCouponType(Long couponId) {
        log.debug("Getting coupon type id: {}", couponId);
        CouponType couponType = couponTypeRepository.findById(couponId)
                .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_COUPON_TYPE_NOT_FOUND_EXCEPTION));
        CouponTypeDto couponTypeDto = couponTypeMapper.mapToCouponTypeDto(couponType);
        log.debug("Return coupon type: {}", couponTypeDto);
        return couponTypeDto;
    }

    public List<CouponTypeDto> getCouponTypes() {
        log.debug("Getting coupon types");
        List<CouponType> couponTypeList = couponTypeRepository.findAll();
        List<CouponTypeDto> couponTypeDtoList = couponTypeMapper.mapToCouponTypeDtoList(couponTypeList);
        log.debug("Return coupon types: {}", couponTypeDtoList);
        return couponTypeDtoList;
    }

    public CouponTypeDto createCouponType(CouponTypeDto couponTypeDto) {
        log.debug("Creating coupon type: {}", couponTypeDto);
        CouponType couponType = couponTypeRepository.save(couponTypeMapper.mapToCouponType(couponTypeDto));
        CouponTypeDto savedCouponTypeDto = couponTypeMapper.mapToCouponTypeDto(couponType);
        log.debug("Return created coupon type: {}", savedCouponTypeDto);
        return savedCouponTypeDto;
    }

    public CouponTypeDto updateCouponType(CouponTypeDto couponTypeDto) {
        log.debug("Updating coupon type: {}", couponTypeDto);
        CouponType couponType = couponTypeRepository.save(couponTypeMapper.mapToCouponType(couponTypeDto));
        CouponTypeDto updatedCouponType = couponTypeMapper.mapToCouponTypeDto(couponType);
        log.debug("Return updated coupon type: {}", updatedCouponType);
        return updatedCouponType;
    }

    public boolean deleteCouponType(Long couponTypeId) {
        log.debug("Deleting coupon type id: {}", couponTypeId);
        couponTypeRepository.deleteById(couponTypeId);
        if (couponTypeRepository.existsById(couponTypeId)) {
            log.debug("Coupon type not deleted id: {}", couponTypeId);
            return false;
        } else {
            log.debug("Coupon type deleted id: {}", couponTypeId);
            return true;
        }
    }
}
