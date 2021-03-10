package com.kenez92.betwinner.service;

import com.kenez92.betwinner.common.enums.CouponStatus;
import com.kenez92.betwinner.common.enums.MatchType;
import com.kenez92.betwinner.domain.CouponDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.CouponMapper;
import com.kenez92.betwinner.persistence.entity.Coupon;
import com.kenez92.betwinner.persistence.entity.User;
import com.kenez92.betwinner.persistence.entity.coupons.CouponType;
import com.kenez92.betwinner.persistence.entity.matches.Match;
import com.kenez92.betwinner.persistence.repository.CouponRepository;
import com.kenez92.betwinner.persistence.repository.UserRepository;
import com.kenez92.betwinner.persistence.repository.coupons.CouponTypeRepository;
import com.kenez92.betwinner.persistence.repository.matches.MatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponService {
    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;
    private final CouponTypeRepository couponTypeRepository;
    private final UserRepository userRepository;
    private final MatchRepository matchRepository;
    private final UserService userService;

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

    public CouponDto getCoupon(Long couponId, UsernamePasswordAuthenticationToken user) {
        log.debug("Getting coupon by id: {} by user: {}", couponId, user.getName());
        Coupon coupon = couponRepository.getCouponWithAllFields(couponId).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_COUPON_NOT_FOUND_EXCEPTION));
        if (!user.getName().equals(coupon.getUser().getLogin())) {
            throw new BetWinnerException(BetWinnerException.ERR_COUPON_DONT_BELONGS_TO_LOGGED_USER);
        }
        CouponDto couponDto = couponMapper.mapToCouponDto(coupon);
        log.debug("Return coupon: {}", couponDto);
        return couponDto;
    }

    public CouponDto createEmptyCouponOrReturnOpenCoupon(UsernamePasswordAuthenticationToken user) {
        Coupon coupon = null;
        try {
            coupon = couponRepository.findByUserAndAndCouponStatus(user.getName(), CouponStatus.WAITING).orElse(null);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BetWinnerException(BetWinnerException.ERR_SOMETHING_WENT_WRONG_EXCEPTION);
        }
        if (coupon == null) {
            User dbUser = userRepository.findByLogin(user.getName()).orElseThrow(()
                    -> new BetWinnerException(BetWinnerException.ERR_USER_NOT_FOUND_EXCEPTION));
            log.debug("Creating empty coupon");
            CouponDto couponDto = couponMapper.mapToCouponDto(couponRepository.save(Coupon.builder()
                    .course(0.0)
                    .rate(0.0)
                    .result(0.0)
                    .couponStatus(CouponStatus.WAITING)
                    .user(dbUser)
                    .build()));
            log.debug("Return created coupon: {}", couponDto);
            return couponDto;
        } else {
            return couponMapper.mapToCouponDto(coupon);
        }
    }

    public CouponDto setRate(Long couponId, Double rate) {
        log.debug("Set rate for coupon id / rate: {}{}", couponId, rate);
        if (rate > 0) {
            Coupon coupon = couponRepository.findById(couponId)
                    .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_COUPON_NOT_FOUND_EXCEPTION));
            coupon.setRate(rate);
            coupon.setResult(rate * coupon.getCourse());
            Coupon savedCoupon = couponRepository.save(coupon);
            setData(savedCoupon);
            return couponMapper.mapToCouponDto(savedCoupon);
        } else {
            throw new BetWinnerException(BetWinnerException.ERR_COUPON_RATE_IS_LOWER_THAN_1);
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

    @Transactional
    public void checkCoupon(Coupon coupon) {
        int size = coupon.getCouponTypeList().size();
        int counter = 0;
        for (int i = 0; i < size; i++) {
            CouponType couponType = coupon.getCouponTypeList().get(i);
            if (couponType.getCouponStatus().equals(CouponStatus.WIN)) {
                counter++;
            } else if (couponType.getCouponStatus().equals(CouponStatus.LOST)) {
                coupon.setCouponStatus(CouponStatus.LOST);
                couponRepository.save(coupon);
            } else if (couponType.getCouponStatus().equals(CouponStatus.ACTIVE)) {
                return;
            } else {
                throw new BetWinnerException(BetWinnerException.ERR_SOMETHING_WENT_WRONG_EXCEPTION);
            }
        }
        if (counter == size) {
            coupon.setCouponStatus(CouponStatus.WIN);
            userService.putMoney(coupon.getUser().getId(), coupon.getResult());
            couponRepository.save(coupon);
        }
    }

    public void checkCoupon(Long couponId) {
        log.debug("Checking coupon id: {}", couponId);
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_COUPON_NOT_FOUND_EXCEPTION));
        checkCoupon(coupon);
    }

    public CouponDto addMatch(Long couponId, Long matchId, MatchType matchType, UsernamePasswordAuthenticationToken user) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_COUPON_NOT_FOUND_EXCEPTION));
        Match match = matchRepository.findById(matchId).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_MATCH_NOT_FOUND_EXCEPTION));
        if (coupon.getUser().getLogin().equals(user.getName())) {
            if (coupon.getCouponStatus() != CouponStatus.WAITING) {
                throw new BetWinnerException(BetWinnerException.ERR_COUPON_IS_CLOSED);
            }
            log.debug("Adding match to coupon id: {}, matchId: {}, matchType: {}", couponId, matchId, matchType);
            CouponType couponType = CouponType.builder()
                    .matchType(matchType)
                    .match(match)
                    .coupon(coupon)
                    .build();
            for (CouponType type : coupon.getCouponTypeList()) {
                if (type.getMatch().getId().equals(couponType.getMatch().getId())) {
                    throw new BetWinnerException(BetWinnerException.ERR_COUPON_TYPE_EXISTS_IN_COUPON);
                }
            }
            coupon.getCouponTypeList().add(couponType);
            countFields(coupon);
            Coupon savedCoupon = couponRepository.save(coupon);
            CouponDto couponDto = couponMapper.mapToCouponDto(savedCoupon);
            log.debug("Return coupon: {}", couponDto);
            return couponDto;
        } else {
            throw new BetWinnerException(BetWinnerException.ERR_COUPON_DONT_BELONGS_TO_LOGGED_USER);
        }
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
                        break;
                    case HOME_TEAM:
                        course = course + match.getMatchStats().getHomeTeamCourse();
                        break;
                    case DRAW:
                        course = course + match.getMatchStats().getDrawCourse();
                }
            }
            course = (course * 0.9);
            if (coupon.getRate() != null && coupon.getRate() >= 1) {
                rate = coupon.getRate();
                result = course * rate;
            }
        }
        coupon.setCourse(course);
        coupon.setRate(rate);
        coupon.setResult(result);
    }

    @Transactional
    public void activeCoupon(Long couponId, UsernamePasswordAuthenticationToken user) {
        try {
            Coupon coupon = couponRepository.getCouponWithAllFields(couponId).orElse(null);
            if (coupon == null) {
                coupon = couponRepository.findById(couponId).orElseThrow(()
                        -> new BetWinnerException(BetWinnerException.ERR_COUPON_NOT_FOUND_EXCEPTION));
            }
            validateCoupon(coupon, user.getName());
            userService.takePointsForCoupon(BigDecimal.valueOf(coupon.getRate()), user);
            coupon.setCouponStatus(CouponStatus.ACTIVE);
            couponRepository.save(coupon);
        } catch (Exception ex) {
            log.error("Exception : {}", ex.getMessage());
            throw ex;
        }
    }

    @Transactional
    public boolean deleteCouponTypeFromCoupon(Long couponId, Long couponTypeId) {
        Coupon coupon = couponRepository.getCouponWithAllFields(couponId).orElse(null);
        if (coupon == null || coupon.getCouponTypeList() == null || coupon.getCouponTypeList().size() == 0) {
            return false;
        }
        for (CouponType type : coupon.getCouponTypeList()) {
            if (type.getId().equals(couponTypeId)) {
                coupon.getCouponTypeList().remove(type);
                couponTypeRepository.delete(type);
                countFields(coupon);
                couponRepository.save(coupon);
                return true;
            }
        }
        return false;
    }

    public void checkActiveCoupons() {
        List<Coupon> couponList = couponRepository.findCouponByCouponStatus(CouponStatus.ACTIVE);
        for (Coupon coupon : couponList) {
            checkCoupon(coupon);
        }
    }

    private void validateCoupon(Coupon coupon, String login) {
        if (!coupon.getUser().getLogin().equals(login)) {
            throw new BetWinnerException(BetWinnerException.ERR_COUPON_DONT_BELONGS_TO_LOGGED_USER);
        }
        if (coupon.getCouponTypeList().size() == 0) {
            throw new BetWinnerException(BetWinnerException.ERR_COUPON_IS_EMPTY);
        }
        long timeNow = new Date().getTime();
        for (CouponType couponType : coupon.getCouponTypeList()) {
            if (couponType.getMatch().getDate().getTime() <= timeNow) {
                throw new BetWinnerException(BetWinnerException.ERR_COUPON_MATCH_ALREADY_STARTED +
                        couponType.getMatch().getHomeTeam() + " : " + couponType.getMatch().getAwayTeam());
            }
        }
        if (coupon.getRate() < 1) {
            throw new BetWinnerException(BetWinnerException.ERR_COUPON_RATE_IS_LOWER_THAN_1);
        }
    }
}
