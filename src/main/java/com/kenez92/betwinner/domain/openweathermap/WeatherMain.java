package com.kenez92.betwinner.domain.openweathermap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherMain {
    @JsonProperty("list")
    private WeatherList[] weatherList;
}
