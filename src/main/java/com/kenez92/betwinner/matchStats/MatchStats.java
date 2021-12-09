package com.kenez92.betwinner.matchStats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
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

    @Column(name = "HOME_TEAM_CHANCE", columnDefinition = "DECIMAL(4,2)")
    private Double homeTeamChance;

    @Column(name = "DRAW_CHANCE", columnDefinition = "DECIMAL(4,2)")
    private Double drawChance;

    @Column(name = "AWAY_TEAM_CHANCE", columnDefinition = "DECIMAL(4,2)")
    private Double awayTeamChance;

    @Column(name = "HOME_TEAM_CHANCE_H2H", columnDefinition = "DECIMAL(4,2)")
    private Double homeTeamChanceH2H;

    @Column(name = "AWAY_TEAM_CHANCE_H2H", columnDefinition = "DECIMAL(4,2)")
    private Double awayTeamChanceH2H;

    @Column(name = "HOME_TEAM_COURSE", columnDefinition = "DECIMAL(16,2)")
    private Double homeTeamCourse;

    @Column(name = "DRAW_COURSE", columnDefinition = "DECIMAL(16,2)")
    private Double drawCourse;

    @Column(name = "AWAY_TEAM_COURSE", columnDefinition = "DECIMAL(16,2)")
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
        if (!Objects.equals(drawChance, matchStats.drawChance)) return false;
        if (!Objects.equals(homeTeamChanceH2H, matchStats.homeTeamChanceH2H)) return false;
        if (!Objects.equals(awayTeamChanceH2H, matchStats.awayTeamChanceH2H)) return false;
        if (!Objects.equals(homeTeamCourse, matchStats.homeTeamCourse)) return false;
        if (!Objects.equals(drawCourse, matchStats.drawCourse)) return false;
        return Objects.equals(awayTeamCourse, matchStats.awayTeamCourse);
    }
}
