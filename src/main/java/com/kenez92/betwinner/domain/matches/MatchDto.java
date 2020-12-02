package com.kenez92.betwinner.domain.matches;

import com.kenez92.betwinner.domain.coupons.CouponTypeDto;
import com.kenez92.betwinner.domain.weather.WeatherDto;
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
    private Integer round;
    private MatchDayDto matchDay;
    private MatchScoreDto matchScore;
    private WeatherDto weather;
    private MatchStatsDto matchStats;
    private List<CouponTypeDto> couponTypeList;
}
