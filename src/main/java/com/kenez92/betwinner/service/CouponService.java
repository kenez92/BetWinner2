package com.kenez92.betwinner.service;

import com.kenez92.betwinner.common.enums.MatchType;
import com.kenez92.betwinner.domain.CouponDto;
import com.kenez92.betwinner.domain.Status;
import com.kenez92.betwinner.domain.coupons.CouponTypeDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.CouponMapper;
import com.kenez92.betwinner.mapper.coupons.CouponTypeMapper;
import com.kenez92.betwinner.persistence.entity.Coupon;
import com.kenez92.betwinner.persistence.entity.User;
import com.kenez92.betwinner.persistence.entity.coupons.CouponType;
import com.kenez92.betwinner.persistence.entity.matches.Match;
import com.kenez92.betwinner.persistence.repository.CouponRepository;
import com.kenez92.betwinner.persistence.repository.UserRepository;
import com.kenez92.betwinner.persistence.repository.coupons.CouponTypeRepository;
import com.kenez92.betwinner.service.coupons.CouponTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponService {
    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;
    private final CouponTypeService couponTypeService;
    private final CouponTypeMapper couponTypeMapper;
    private final CouponTypeRepository couponTypeRepository;
    private final UserRepository userRepository;
    private final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public List<CouponDto> getUserCoupons(UsernamePasswordAuthenticationToken user) {
        User dbUser = userRepository.findByLogin(user.getName()).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_USER_NOT_FOUND_EXCEPTION));
        log.debug("Getting all user coupons");
        List<Coupon> couponList = couponRepository.findAllByUser(dbUser);
        for (Coupon coupon : couponList) {
            setData(coupon);
        }
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

    public CouponDto createEmptyCoupon(UsernamePasswordAuthenticationToken user) {
        User dbUser = userRepository.findByLogin(user.getName()).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_USER_NOT_FOUND_EXCEPTION));
        log.debug("Creating empty coupon");
        CouponDto couponDto = couponMapper.mapToCouponDto(couponRepository.save(Coupon.builder()
                .course(0.0)
                .rate(0.0)
                .result(0.0)
                .couponStatus(Status.WAITING)
                .user(dbUser)
                .build()));
        log.debug("Return created coupon: {}", couponDto);
        return couponDto;
    }

    public CouponDto setRate(Long couponId, Double rate) {
        log.debug("Set rate for coupon id / rate: {}{}", couponId, rate);
        if (rate > 0) {
            Coupon coupon = couponRepository.findById(couponId)
                    .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_COUPON_NOT_FOUND_EXCEPTION));
            coupon.setRate(rate);
            coupon.setResult(Double.parseDouble(decimalFormat.format(rate * coupon.getCourse())));
            Coupon savedCoupon = couponRepository.save(coupon);
            setData(savedCoupon);
            return couponMapper.mapToCouponDto(savedCoupon);
        } else {
            throw new BetWinnerException(BetWinnerException.ERR_COUPON_RATE_IS_LOWER_THAN_0);
        }
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

    public Status checkCoupon(Long couponId) {
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
                return Status.LOST;
            } else if (couponType.getStatus().equals(Status.WAITING)) {
                return Status.WAITING;
            } else {
                throw new BetWinnerException(BetWinnerException.ERR_SOMETHING_WENT_WRONG_EXCEPTION);
            }
        }
        if (counter == size) {
            coupon.setCouponStatus(Status.WIN);
            couponRepository.save(coupon);
        }
        return Status.WIN;
    }

    public CouponDto addMatch(Long couponId, CouponTypeDto couponTypeDto) {
        log.debug("Adding match to coupon id: {}{}", couponId, couponTypeDto);
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_COUPON_NOT_FOUND_EXCEPTION));
        CouponTypeDto savedCouponTypeDto = couponTypeService.createCouponType(couponTypeDto);
        CouponType savedCouponType = couponTypeMapper.mapToCouponType(savedCouponTypeDto);
        setData(coupon);
        coupon.getCouponTypeList().add(savedCouponType);
        countFields(coupon);
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

    private void countFields(Coupon coupon) {
        double course = 0.0;
        double rate = 0.0;
        double result = 0.0;

        if (coupon.getCouponTypeList() != null && coupon.getCouponTypeList().size() != 0) {
            for (int i = 0; i < coupon.getCouponTypeList().size(); i++) {
                MatchType matchType = coupon.getCouponTypeList().get(i).getMatchType();
                Match match = coupon.getCouponTypeList().get(i).getMatch();
                switch (matchType) {
                    case AWAY_TEAM:
                        course = course + match.getMatchStats().getAwayTeamCourse();
                    case HOME_TEAM:
                        course = course + match.getMatchStats().getHomeTeamCourse();
                    case DRAW:
                        course = course + match.getMatchStats().getDrawCourse();
                }
            }
            course = (course * 0.9) / coupon.getCouponTypeList().size();
            if (coupon.getRate() != null && coupon.getRate() > 0) {
                rate = coupon.getRate();
                result = Double.parseDouble(decimalFormat.format(course * rate));
            }
        }
        coupon.setCourse(course);
        coupon.setRate(rate);
        coupon.setResult(result);
    }
}
