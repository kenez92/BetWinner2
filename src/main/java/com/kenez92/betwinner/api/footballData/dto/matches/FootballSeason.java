package com.kenez92.betwinner.api.footballData.dto.matches;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FootballSeason {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("startDate")
    private LocalDate startDate;

    @JsonProperty("endDate")
    private LocalDate endDate;

    @JsonProperty("currentMatchday")
    private Integer currentMatchday;

    @JsonProperty("winner")
    private FootballSeasonWinner winner;
}
