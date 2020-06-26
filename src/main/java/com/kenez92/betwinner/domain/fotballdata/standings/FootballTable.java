package com.kenez92.betwinner.domain.fotballdata.standings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenez92.betwinner.domain.fotballdata.matches.FootballCompetition;
import com.kenez92.betwinner.domain.fotballdata.matches.FootballSeason;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class FootballTables {

    @JsonProperty("competition")
    private FootballCompetition footballCompetition;

    @JsonProperty("season")
    private FootballSeason season;

    @JsonProperty("standings")
    private FootballStandings[] footballStandings;
}
