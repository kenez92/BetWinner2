package com.kenez92.betwinner.entity;

import com.kenez92.betwinner.domain.table.CouponStatus;
import com.kenez92.betwinner.entity.matches.Match;
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
    @ManyToMany
    @JoinTable(
            name = "JOIN_MATCH_ID",
            joinColumns = {@JoinColumn(name = "MATCH_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "COUPON_ID", referencedColumnName = "ID")})
    private List<Match> matchList = new ArrayList<>();
}
