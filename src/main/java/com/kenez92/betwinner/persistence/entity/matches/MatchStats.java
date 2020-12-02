package com.kenez92.betwinner.persistence.entity.matches;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "MATCH_STATS",
        uniqueConstraints = {@UniqueConstraint(columnNames = "FOOTBALL_MATCH_ID")},
        indexes = {@Index(name = "MatchStat_footballMatchId_index", columnList = "FOOTBALL_MATCH_ID")})
public class MatchStats {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "FOOTBALL_MATCH_ID", nullable = false)
    private Long footballMatchId;

    @Column(name = "HOME_TEAM_WINS")
    private Integer homeTeamWins;

    @Column(name = "DRAWS")
    private Integer draws;

    @Column(name = "AWAY_TEAM_WINS")
    private Integer awayTeamWins;

    @Column(name = "GAMES_PLAYED")
    private Integer gamesPlayed;

    @Column(name = "HOME_TEAM_POSITION_IN_TABLE")
    private Integer homeTeamPositionInTable;

    @Column(name = "AWAY_TEAM_POSITION_IN_TABLE")
    private Integer awayTeamPositionInTable;

    @Column(name = "HOME_TEAM_CHANCE")
    private Double homeTeamChance;

    @Column(name = "AWAY_TEAM_CHANCE")
    private Double awayTeamChance;

    @Column(name = "HOME_TEAM_COURSE")
    private Double homeTeamCourse;

    @Column(name = "DRAW_COURSE")
    private Double drawCourse;

    @Column(name = "AWAY_TEAM_COURSE")
    private Double awayTeamCourse;

    public boolean equals(MatchStats matchStats) {
        if (matchStats == null) {
            return false;
        }
        if (!Objects.equals(footballMatchId, matchStats.getFootballMatchId())) return false;
        if (!Objects.equals(homeTeamWins, matchStats.getHomeTeamWins())) return false;
        if (!Objects.equals(draws, matchStats.getDraws())) return false;
        if (!Objects.equals(awayTeamWins, matchStats.getAwayTeamWins())) return false;
        if (!Objects.equals(homeTeamPositionInTable, matchStats.homeTeamPositionInTable)) return false;
        if (!Objects.equals(awayTeamPositionInTable, matchStats.awayTeamPositionInTable)) return false;
        if (!Objects.equals(homeTeamChance, matchStats.homeTeamChance)) return false;
        if (!Objects.equals(awayTeamChance, matchStats.awayTeamChance)) return false;
        if (!Objects.equals(homeTeamCourse, matchStats.homeTeamCourse)) return false;
        if (!Objects.equals(drawCourse, matchStats.drawCourse)) return false;
        return Objects.equals(awayTeamCourse, matchStats.awayTeamCourse);
    }
}
