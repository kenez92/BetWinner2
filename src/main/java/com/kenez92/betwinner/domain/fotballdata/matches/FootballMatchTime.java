package com.kenez92.betwinner.domain.fotballdata.matches;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class FootballMatchTime {
    @JsonProperty("homeTeam")
    private Integer homeTeam;

    @JsonProperty("awayTeam")
    private Integer awayTeam;

}
