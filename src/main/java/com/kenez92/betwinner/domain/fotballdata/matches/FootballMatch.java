package com.kenez92.betwinner.domain.fotballdata.matches;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenez92.betwinner.domain.fotballdata.FootballTeam;
import com.kenez92.betwinner.domain.fotballdata.FootballTeam;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@ToString
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FootballMatch {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("competition")
    private FootballCompetition competition;

    @JsonProperty("season")
    private FootballSeason season;

    @JsonProperty("utcDate")
    private Date utcDate;

    @JsonProperty("status")
    private String status;

    @JsonProperty("matchday")
    private int matchDay;

    @JsonProperty("stage")
    private String stage;

    @JsonProperty("group")
    private String group;

    @JsonProperty("lastUpdated")
    private LocalDate lastUpdated;

    @JsonProperty("score")
    private FootballScore score;

    @JsonProperty("homeTeam")
    private FootballTeam homeTeam;

    @JsonProperty("awayTeam")
    private FootballTeam awayTeam;

    @JsonProperty("referees")
    private FootballReferees[] referees;
}
