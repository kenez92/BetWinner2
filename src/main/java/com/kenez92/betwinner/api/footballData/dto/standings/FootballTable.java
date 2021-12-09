package com.kenez92.betwinner.api.footballData.dto.standings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenez92.betwinner.api.footballData.dto.matches.FootballCompetition;
import com.kenez92.betwinner.api.footballData.dto.matches.FootballSeason;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class FootballTable {
    @JsonProperty("competition")
    private FootballCompetition footballCompetition;

    @JsonProperty("season")
    private FootballSeason season;

    @JsonProperty("standings")
    private FootballStandings[] footballStandings;
}
