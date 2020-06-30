package com.kenez92.betwinner.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "\"MATCH\"")
public class Match {
    @Id
    @Column(unique = true)
    private Long id;

//    @ManyToMany(
//            targetEntity = Coupon.class,
//            mappedBy = "matches"
//    )
//    private List<Coupon> coupon;

    //    @Column(unique = true)
//    private Long matchId;
    private Date matchDate;
    private String homeTeam;
    private String awayTeam;
    private Integer homeTeamScore;
    private Integer awayTeamScore;
    private String duration;
    private Double chanceToWinHomeTeam;
    private Double chanceToWinAwayTeam;
}