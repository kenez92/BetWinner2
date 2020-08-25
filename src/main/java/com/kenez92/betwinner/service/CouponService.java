package com.kenez92.betwinner.service;

import com.kenez92.betwinner.domain.CouponDto;
import com.kenez92.betwinner.domain.Status;
import com.kenez92.betwinner.domain.coupons.CouponTypeDto;
import com.kenez92.betwinner.entity.Coupon;
import com.kenez92.betwinner.entity.coupons.CouponType;
import com.kenez92.betwinner.entity.matches.Match;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.CouponMapper;
import com.kenez92.betwinner.mapper.coupons.CouponTypeMapper;
import com.kenez92.betwinner.repository.CouponRepository;
import com.kenez92.betwinner.repository.coupons.CouponTypeRepository;
import com.kenez92.betwinner.service.coupons.CouponTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponService {
    private final CouponRepository couponRepository;
    private final CouponTypeService couponTypeService;
    private final CouponMapper couponMapper;
    private final CouponTypeMapper couponTypeMapper;
    private final CouponTypeRepository couponTypeRepository;

    public List<CouponDto> getCoupons() {
        log.debug("Getting all coupons");
        List<Coupon> couponList = couponRepository.findAll();
        List<CouponDto> couponDtoList = couponMapper.mapToCouponDtoList(couponList);
        log.debug("Return all coupons: {}", couponDtoList);
        return couponDtoList;
    }

    public CouponDto getCoupon(Long couponId) {
        log.debug("Getting coupon by id: {}", couponId);
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_COUPON_NOT_FOUND_EXCEPTION));
        setData(coupon);
        CouponDto couponDto = couponMapper.mapToCouponDto(coupon);
        log.debug("Return coupon: {}", couponDto);
        return couponDto;
    }

    public CouponDto createEmptyCoupon() {
        log.debug("Creating empty coupon");
        CouponDto couponDto = couponMapper.mapToCouponDto(couponRepository.save(new Coupon()));
        log.debug("Return created coupon: {}", couponDto);
        return couponDto;
    }


    public boolean deleteCoupon(Long couponId) {
        log.debug("Deleting coupon id: {}", couponId);
        couponRepository.deleteById(couponId);
        if (couponRepository.existsById(couponId)) {
            log.debug("Coupon not deleted id: {}", couponId);
            return false;
        } else {
            log.debug("Coupon deleted id: {}", couponId);
            return true;
        }
    }

    public boolean checkCoupon(Long couponId) {
        log.debug("Checking coupon id: {}", couponId);
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_COUPON_NOT_FOUND_EXCEPTION));
        int size = coupon.getCouponTypeList().size();
        int counter = 0;
        for (int i = 0; i < size; i++) {
            CouponType couponType = coupon.getCouponTypeList().get(i);
            if (couponType.getStatus().equals(Status.WIN)) {
                counter++;
            } else if (couponType.getStatus().equals(Status.LOST)) {
                coupon.setCouponStatus(Status.LOST);
                couponRepository.save(coupon);
                throw new BetWinnerException(BetWinnerException.INFO_COUPON_IS_LOST);
            } else if (couponType.getStatus().equals(Status.WAITING)) {
                throw new BetWinnerException(BetWinnerException.INFO_COUPON_IS_WAITING);
            } else {
                throw new BetWinnerException(BetWinnerException.ERR_SOMETHING_WENT_WRONG_EXCEPTION);
            }
        }
        if (counter == size) {
            coupon.setCouponStatus(Status.WIN);
            couponRepository.save(coupon);
        }
        return true;
    }

    public CouponDto addMatch(Long couponId, CouponTypeDto couponTypeDto) {
        log.debug("Adding match to coupon id: {}{}", couponId, couponTypeDto);
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_COUPON_NOT_FOUND_EXCEPTION));
        CouponTypeDto savedCouponTypeDto = couponTypeService.createCouponType(couponTypeDto);
        CouponType savedCouponType = couponTypeMapper.mapToCouponType(savedCouponTypeDto);
        setData(coupon);
        coupon.getCouponTypeList().add(savedCouponType);
        Coupon savedCoupon = couponRepository.save(coupon);
        setData(savedCoupon);
        CouponDto couponDto = couponMapper.mapToCouponDto(savedCoupon);

        log.debug("Return coupon: {}", couponDto);
        return couponDto;
    }

    private void setData(Coupon coupon) {
        Coupon tmpCoupon = Coupon.builder()
                .id(coupon.getId())
                .build();
        List<CouponType> couponTypeList = couponTypeRepository.findByCoupon(coupon);
        for (CouponType couponType : couponTypeList) {
            couponType.setCoupon(tmpCoupon);
            couponType.setMatch(Match.builder()
                    .id(couponType.getMatch().getId())
                    .build());
        }
        coupon.setCouponTypeList(couponTypeList);
    }
}
