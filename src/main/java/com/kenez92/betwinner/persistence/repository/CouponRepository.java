package com.kenez92.betwinner.persistence.repository;

import com.kenez92.betwinner.common.enums.CouponStatus;
import com.kenez92.betwinner.persistence.entity.Coupon;
import com.kenez92.betwinner.persistence.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface CouponRepository extends CrudRepository<Coupon, Long> {
    @Override
    List<Coupon> findAll();

    List<Coupon> findAllByUser(User user);

    @Override
    Optional<Coupon> findById(Long couponId);

    List<Coupon> findByUser(User user);

    @Query("SELECT c FROM Coupon c JOIN c.user u WHERE c.user.login = ?1 AND c.couponStatus = ?2")
    Optional<Coupon> findByUserAndAndCouponStatus(String login, CouponStatus couponStatus);

    @Query("SELECT c FROM Coupon c JOIN FETCH c.user JOIN FETCH c.couponTypeList t JOIN FETCH t.match m JOIN FETCH m.matchScore JOIN FETCH m.matchDay JOIN FETCH m.matchStats WHERE c.id = ?1 ")
    Optional<Coupon> getCouponWithAllFields(Long couponId);

    @Query("SELECT c FROM Coupon c JOIN FETCH c.user JOIN FETCH c.couponTypeList t JOIN t.match m JOIN m.matchScore JOIN m.matchDay JOIN m.matchStats WHERE c.couponStatus = ?1 ")
    List<Coupon> findCouponByCouponStatus(CouponStatus couponStatus);

}
