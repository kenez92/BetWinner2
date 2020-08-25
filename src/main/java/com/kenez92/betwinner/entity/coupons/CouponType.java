package com.kenez92.betwinner.entity.coupons;

import com.kenez92.betwinner.domain.MatchType;
import com.kenez92.betwinner.domain.Status;
import com.kenez92.betwinner.entity.Coupon;
import com.kenez92.betwinner.entity.matches.Match;
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

    @Enumerated(value = EnumType.STRING)
    @Column(name = "MATCH_TYPE")
    private MatchType matchType;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "TYPE_STATUS")
    private Status status;

    @ManyToOne
    private Match match;

    @ManyToOne
    private Coupon coupon;


}
