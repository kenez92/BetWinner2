package com.kenez92.betwinner.domain.matches;

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
    private Double homeTeamCourse;
    private Double drawCourse;
    private Double awayTeamCourse;
}
