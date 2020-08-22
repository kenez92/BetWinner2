package com.kenez92.betwinner.entity.matches;

import com.kenez92.betwinner.entity.coupons.CouponType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "\"MATCH\"")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FOOTBALL_ID")
    private Long footballId;

    @Column(name = "HOME_TEAM")
    private String homeTeam;

    @Column(name = "AWAY_TEAM")
    private String awayTeam;

    @Column(name = "COMPETITION_ID")
    private Long competitionId;

    @Column(name = "SEASON_ID")
    private Long seasonId;

    @Column(name = "DATE")
    private Date date;

    @Column(name = "HOME_TEAM_POSITION_IN_TABLE")
    private Integer homeTeamPositionInTable;

    @Column(name = "AWAY_TEAM_POSITION_IN_TABLE")
    private Integer awayTeamPositionInTable;

    @Column(name = "HOME_TEAM_CHANCE")
    private Double homeTeamChance;

    @Column(name = "AWAY_TEAM_CHANCE")
    private Double awayTeamChance;

    @Column(name = "ROUND")
    private Integer round;

    @Column(name = "HOME_TEAM_COURSE")
    private Double homeTeamCourse;

    @Column(name = "DRAW_COURSE")
    private Double drawCourse;

    @Column(name = "AWAY_TEAM_COURSE")
    private Double awayTeamCourse;

    @ManyToOne
    private MatchDay matchDay;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "MATCH_SCORE_ID")
    private MatchScore matchScore;

    @ManyToOne
    private Weather weather;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY,
            targetEntity = CouponType.class,
            mappedBy = "match")
    private List<CouponType> couponTypeList = new ArrayList<>();
}
