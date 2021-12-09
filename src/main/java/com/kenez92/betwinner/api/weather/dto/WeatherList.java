package com.kenez92.betwinner.api.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherList {
    @JsonProperty("dt")
    private Long date;

    @JsonProperty("main")
    private WeatherDetails weatherDetails;
}
