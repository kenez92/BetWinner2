package com.kenez92.betwinner.domain.fotballdata.matches;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FootballMatchTime {
    @JsonProperty("homeTeam")
    private Integer homeTeam;

    @JsonProperty("awayTeam")
    private Integer awayTeam;

}
