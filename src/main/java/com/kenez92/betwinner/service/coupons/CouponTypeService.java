package com.kenez92.betwinner.service.coupons;

import com.kenez92.betwinner.common.enums.CouponStatus;
import com.kenez92.betwinner.domain.coupons.CouponTypeDto;
import com.kenez92.betwinner.domain.matches.MatchScoreDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.coupons.CouponTypeMapper;
import com.kenez92.betwinner.persistence.entity.coupons.CouponType;
import com.kenez92.betwinner.persistence.entity.matches.MatchScore;
import com.kenez92.betwinner.persistence.repository.coupons.CouponTypeRepository;
import com.kenez92.betwinner.service.matches.MatchScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponTypeService {
    private final MatchScoreService matchScoreService;
    private final CouponTypeRepository couponTypeRepository;
    private final CouponTypeMapper couponTypeMapper;

    public CouponTypeDto getCouponType(Long couponId) {
        log.debug("Getting coupon type id: {}", couponId);
        CouponType couponType = couponTypeRepository.findById(couponId)
                .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_COUPON_TYPE_NOT_FOUND_EXCEPTION));
        setData(couponType);
        CouponTypeDto couponTypeDto = couponTypeMapper.mapToCouponTypeDto(couponType);
        log.debug("Return coupon type: {}", couponTypeDto);
        return couponTypeDto;
    }

    public List<CouponTypeDto> getCouponTypes() {
        log.debug("Getting coupon types");
        List<CouponType> couponTypeList = couponTypeRepository.findAll();
        for (CouponType couponType : couponTypeList) {
            setData(couponType);
        }
        List<CouponTypeDto> couponTypeDtoList = couponTypeMapper.mapToCouponTypeDtoList(couponTypeList);
        log.debug("Return coupon types: {}", couponTypeDtoList);
        return couponTypeDtoList;
    }

    public CouponTypeDto createCouponType(CouponTypeDto couponTypeDto) {
        log.debug("Creating coupon type: {}", couponTypeDto);
        CouponType couponType = couponTypeRepository.save(couponTypeMapper.mapToCouponType(couponTypeDto));
        setData(couponType);
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

    public void checkCouponTypes() {
        List<CouponType> couponTypeList = couponTypeRepository.couponsForCheck(LocalDate.now());
        for (CouponType couponType : couponTypeList) {
            log.debug("Check coupon type id: {}", couponType.getId());
            if (couponType.getCouponStatus().equals(CouponStatus.ACTIVE)) {
                MatchScore matchScore = couponType.getMatch().getMatchScore();
                if (matchScore.getWinner() != null) {
                    if (couponType.getMatchType().toString().equals(matchScore.getWinner())) {
                        couponType.setCouponStatus(CouponStatus.WIN);
                        log.debug("Coupon type win!");
                    } else {
                        couponType.setCouponStatus(CouponStatus.LOST);
                        log.debug("CouponType lost!");
                    }
                    couponTypeRepository.save(couponType);
                }
            }
        }
    }

    public void setData(CouponType couponType) {
        couponType.getMatch().getMatchDay().setMatchesList(new ArrayList<>());
        couponType.getMatch().getWeather().setMatchList(new ArrayList<>());
        couponType.getMatch().setCouponTypeList(new ArrayList<>());
        couponType.getCoupon().setCouponTypeList(new ArrayList<>());
    }
}
