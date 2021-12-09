package com.kenez92.betwinner.api.footballData.dto.matches;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenez92.betwinner.api.footballData.dto.FootballArea;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FootballCompetition {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("area")
    private FootballArea area;

    @JsonProperty("code")
    private String code;

    @JsonProperty("lastUpdated")
    private LocalDate lastUpdated;
}
