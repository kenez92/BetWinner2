package com.kenez92.betwinner.coupons;

import com.kenez92.betwinner.common.enums.CouponStatus;
import com.kenez92.betwinner.couponTypes.CouponType;
import com.kenez92.betwinner.users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
