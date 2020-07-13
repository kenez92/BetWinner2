package com.kenez92.betwinner.domain;

import com.kenez92.betwinner.domain.matches.Match;
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

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "JOIN_MATCH_ID",
            joinColumns = {@JoinColumn(name = "MATCH_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "COUPON_ID", referencedColumnName = "ID")})
    private List<Match> matchList = new ArrayList<>();
}
