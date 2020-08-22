package com.kenez92.betwinner.repository.coupons;

import com.kenez92.betwinner.entity.coupons.CouponType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface CouponTypeRepository extends CrudRepository<CouponType, Long> {
    @Override
    List<CouponType> findAll();
}
