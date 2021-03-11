package com.kenez92.betwinner.persistence.entity;

import com.kenez92.betwinner.common.enums.CouponStatus;
import com.kenez92.betwinner.persistence.entity.coupons.CouponType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "COURSE")
    private Double course;

    @Column(name = "RATE")
    private Double rate;

    @Column(name = "RESULT")
    private Double result;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private CouponStatus couponStatus;

    @ManyToOne
    private User user;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY,
            targetEntity = CouponType.class,
            cascade = CascadeType.ALL,
            mappedBy = "coupon")
    private List<CouponType> couponTypeList = new ArrayList<>();
}
