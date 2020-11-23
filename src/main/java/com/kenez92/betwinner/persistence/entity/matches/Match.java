package com.kenez92.betwinner.persistence.entity.matches;

import com.kenez92.betwinner.persistence.entity.coupons.CouponType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@NamedQueries({
        @NamedQuery(
                name = "Match.predictMatches",
                query = "FROM Match WHERE (homeTeamChance > :NUMBER_ONE AND homeTeamChance < :NUMBER_TWO "
                        + " OR awayTeamChance > :NUMBER_ONE AND awayTeamChance < :NUMBER_TWO) "
                        + "AND DATE_FORMAT(DATE, '%m/%d/%Y') = DATE_FORMAT(curdate(), '%m/%d/%Y')"
        ),
        @NamedQuery(
                name = "Match.findMatchesAtDate",
                query = "FROM Match WHERE DATE_FORMAT(DATE, '%m/%d/%Y') = DATE_FORMAT(:DATE, '%m/%d/%Y')"
        )
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name = "\"MATCH\"")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FOOTBALL_ID", nullable = false)
    private Long footballId;

    @Column(name = "HOME_TEAM", nullable = false)
    private String homeTeam;

    @Column(name = "AWAY_TEAM", nullable = false)
    private String awayTeam;

    @Column(name = "COMPETITION_ID", nullable = false)
    private Long competitionId;

    @Column(name = "SEASON_ID", nullable = false)
    private Long seasonId;

    @Column(name = "DATE", nullable = false)
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

    public boolean equals(Match match) {
        if (match == null) {
            return false;
        }
        boolean result = true;
        result &= footballId.equals(match.getFootballId());
        result &= homeTeam.equals(match.getHomeTeam());
        result &= awayTeam.equals(match.getAwayTeam());
        result &= competitionId.equals(match.getCompetitionId());
        result &= seasonId.equals(match.getSeasonId());
        result &= date.getTime() == match.getDate().getTime();
        if (match.getHomeTeamPositionInTable() != null) {
            result &= homeTeamPositionInTable.equals(match.getHomeTeamPositionInTable());
        }
        if (match.getAwayTeamPositionInTable() != null) {
            result &= awayTeamPositionInTable.equals(match.getAwayTeamPositionInTable());
        }
        if (match.getHomeTeamChance() != null) {
            result &= homeTeamChance.equals(match.getHomeTeamChance());
        }
        if (match.getAwayTeamChance() != null) {
            result &= awayTeamChance.equals(match.getAwayTeamChance());
        }
        if (match.getRound() != null) {
            result &= round.equals(match.getRound());
        }
        if (match.getHomeTeamCourse() != null) {
            result &= homeTeamCourse.equals(match.getHomeTeamCourse());
        }
        if (match.getDrawCourse() != null) {
            result &= drawCourse.equals(match.getDrawCourse());
        }
        if (match.getAwayTeamCourse() != null) {
            result &= awayTeamCourse.equals(match.getAwayTeamCourse());
        }
        if (match.getMatchDay() != null) {
            result &= matchDay.equals(match.getMatchDay());
        }
        if (match.getMatchScore() != null) {
            result &= matchScore.equals(match.getMatchScore());
        }
        if (match.getWeather() != null) {
            result &= weather.equals(match.getWeather());
        }
        return result;
    }
}
