package com.kenez92.betwinner.domain.matches;

import com.kenez92.betwinner.domain.CouponDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MatchDto {
    private Long id;
    private Long footballId;
    private String homeTeam;
    private String awayTeam;
    private Long competitionId;
    private Long seasonId;
    private Date date;
    private Integer homeTeamPositionInTable;
    private Integer awayTeamPositionInTable;
    private Double homeTeamChance;
    private Double awayTeamChance;
    private Integer round;
    private MatchDayDto matchDay;
    private MatchScoreDto matchScore;
    private WeatherDto weather;
    private List<CouponDto> couponList;
}
