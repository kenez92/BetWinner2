package com.kenez92.betwinner.matchStats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MatchStatsDto {
    private Long id;
    private Long footballMatchId;
    private Integer homeTeamWins;
    private Integer draws;
    private Integer awayTeamWins;
    private Integer gamesPlayed;
    private Integer homeTeamPositionInTable;
    private Integer awayTeamPositionInTable;
    private Double homeTeamChance;
    private Double awayTeamChance;
    private Double drawChance;
    private Double homeTeamChanceH2H;
    private Double awayTeamChanceH2H;
    private Double homeTeamCourse;
    private Double drawCourse;
    private Double awayTeamCourse;
}
