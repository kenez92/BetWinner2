package com.kenez92.betwinner.persistence.entity.matches;

import com.kenez92.betwinner.persistence.entity.coupons.CouponType;
import com.kenez92.betwinner.persistence.entity.weather.Weather;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@NamedQueries({
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "ROUND")
    private Integer round;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "MATCH_STATS_ID")
    private MatchStats matchStats;

    @ManyToOne
    private MatchDay matchDay;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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
        if (!Objects.equals(footballId, match.getFootballId())) return false;
        if (!Objects.equals(homeTeam, match.getHomeTeam())) return false;
        if (!Objects.equals(awayTeam, match.getAwayTeam())) return false;
        if (!Objects.equals(competitionId, match.getCompetitionId())) return false;
        if (!Objects.equals(seasonId, match.getSeasonId())) return false;
        if (!Objects.equals(date.getTime(), match.getDate().getTime())) return false;
        if (!Objects.equals(matchStats, match.getMatchStats())) return false;
        if (!Objects.equals(round, match.getRound())) return false;
        if (!Objects.equals(matchDay, match.getMatchDay())) return false;
        if (!Objects.equals(matchScore, match.getMatchScore())) return false;
        if (!Objects.equals(weather, match.getWeather())) return false;
        return Objects.equals(couponTypeList, match.getCouponTypeList());
    }
}
