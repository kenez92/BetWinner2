package com.kenez92.betwinner.api.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDetails {
    @JsonProperty("feels_like")
    private Double tempFelt;

    @JsonProperty("temp_min")
    private Double tempMin;

    @JsonProperty("temp_max")
    private Double tempMax;

    @JsonProperty("pressure")
    private Integer pressure;

}
