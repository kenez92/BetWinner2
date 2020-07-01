package com.kenez92.betwinner.domain.matches;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MatchScoreDto {
    private Long id;
    private Long matchId;
    private String winner;
    private String status;
    private String duration;
    private Integer fullTimeHomeTeam;
    private Integer fullTimeAwayTeam;
    private Integer halfTimeHomeTeam;
    private Integer halfTimeAwayTeam;
    private Integer extraTimeHomeTeam;
    private Integer extraTimeAwayTeam;
    private Integer penaltiesHomeTeam;
    private Integer penaltiesAwayTeam;
}
