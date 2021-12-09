package com.kenez92.betwinner.weather;

import com.kenez92.betwinner.match.MatchDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WeatherDto {
    private Long id;
    private String country;
    private Date date;
    private Double tempFelt;
    private Double tempMin;
    private Double tempMax;
    private Integer pressure;
    private List<MatchDto> matchList;
}
