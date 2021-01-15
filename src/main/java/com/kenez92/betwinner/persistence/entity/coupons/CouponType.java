package com.kenez92.betwinner.persistence.entity.coupons;

import com.kenez92.betwinner.common.enums.MatchType;
import com.kenez92.betwinner.common.enums.CouponStatus;
import com.kenez92.betwinner.persistence.entity.Coupon;
import com.kenez92.betwinner.persistence.entity.matches.Match;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NamedNativeQuery(
        name = "CouponType.couponsForCheck",
        query = "SELECT * FROM coupon_type WHERE TYPE_STATUS = 'WAITING'",
        resultClass = CouponType.class
)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table
public class CouponType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "MATCH_TYPE")
    private MatchType matchType;

    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    @Column(name = "TYPE_STATUS")
    private CouponStatus couponStatus = CouponStatus.WAITING;

    @ManyToOne
    private Match match;

    @ManyToOne
    private Coupon coupon;


}
