package com.kenez92.betwinner.repository;

import com.kenez92.betwinner.domain.Coupon;
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

    @Override
    Optional<Coupon> findById(Long couponId);
}
