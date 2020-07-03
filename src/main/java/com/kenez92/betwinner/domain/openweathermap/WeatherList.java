package com.kenez92.betwinner.domain.openweathermap;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class WeatherList {

    @JsonProperty("dt")
    private Long date;

    @JsonProperty("main")
    private WeatherDetails weatherDetails;
}
