package com.kenez92.betwinner.api.footballData.dto.matches;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class FootballScore {
    @JsonProperty("winner")
    private String winner;

    @JsonProperty("duration")
    private String duration;

    @JsonProperty("fullTime")
    private FootballMatchTime fullTime;

    @JsonProperty("halfTime")
    private FootballMatchTime halfTime;

    @JsonProperty("extraTime")
    private FootballMatchTime extraTime;

    @JsonProperty("penalties")
    private FootballMatchTime penalties;

}
