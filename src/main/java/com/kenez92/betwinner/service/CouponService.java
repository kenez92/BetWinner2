package com.kenez92.betwinner.service;

import com.kenez92.betwinner.domain.CouponDto;
import com.kenez92.betwinner.entity.Coupon;
import com.kenez92.betwinner.entity.matches.Match;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.CouponMapper;
import com.kenez92.betwinner.repository.CouponRepository;
import com.kenez92.betwinner.repository.matches.MatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponService {
    private final CouponRepository couponRepository;
    private final MatchRepository matchRepository;
    private final CouponMapper couponMapper;

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
        CouponDto couponDto = couponMapper.mapToCouponDto(coupon);
        log.debug("Return coupon: {}", couponDto);
        return couponDto;
    }

    public CouponDto createCoupon() {
        log.debug("Creating new coupon");
        Coupon coupon = couponRepository.save(new Coupon());
        CouponDto couponDto = couponMapper.mapToCouponDto(coupon);
        log.debug("Return created coupon: {}", couponDto);
        return couponDto;
    }

    public CouponDto addMatch(Long couponId, Long matchId) {
        log.debug("Add match to the coupon: {}{}", matchId, couponId);
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_COUPON_NOT_FOUND_EXCEPTION));
        Match match = matchRepository.findById(matchId).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_MATCH_NOT_FOUND_EXCEPTION));
        coupon.getMatchList().add(match);
        Coupon updatedCoupon = couponRepository.save(coupon);
        CouponDto couponDto = couponMapper.mapToCouponDto(updatedCoupon);
        log.debug("Return coupon with added match: {}", couponDto);
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
}
