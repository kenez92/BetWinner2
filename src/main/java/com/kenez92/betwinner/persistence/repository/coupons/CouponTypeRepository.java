package com.kenez92.betwinner.persistence.repository.coupons;

import com.kenez92.betwinner.persistence.entity.Coupon;
import com.kenez92.betwinner.persistence.entity.coupons.CouponType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Transactional
@Repository
public interface CouponTypeRepository extends CrudRepository<CouponType, Long> {
    @Override
    List<CouponType> findAll();

    List<CouponType> findByCoupon(Coupon coupon);

    @Query("SELECT DISTINCT t FROM CouponType t JOIN FETCH t.match m JOIN FETCH m.matchDay d JOIN FETCH m.matchScore " +
            "JOIN FETCH m.matchStats JOIN FETCH m.weather JOIN FETCH t.coupon WHERE t.couponStatus='ACTIVE' AND d.localDate <=?1")
    List<CouponType> couponsForCheck(LocalDate localDate);
}
