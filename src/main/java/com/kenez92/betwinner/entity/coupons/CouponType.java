package com.kenez92.betwinner.entity.coupons;

import com.kenez92.betwinner.domain.MatchType;
import com.kenez92.betwinner.entity.Coupon;
import com.kenez92.betwinner.entity.matches.Match;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "MATCH_TYPE")
    private MatchType matchType;

    @ManyToOne
    private Match match;

    @ManyToOne
    private Coupon coupon;


}
