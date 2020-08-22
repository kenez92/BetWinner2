package com.kenez92.betwinner.entity;

import com.kenez92.betwinner.domain.table.CouponStatus;
import com.kenez92.betwinner.entity.coupons.CouponType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "COURSE")
    private Double course;

    @Column(name = "RATE")
    private Double rate;

    @Column(name = "RESULT")
    private Double result;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "STATUS")
    private CouponStatus couponStatus;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY,
            targetEntity = CouponType.class,
            mappedBy = "coupon")
    private List<CouponType> couponTypeList = new ArrayList<>();
}
