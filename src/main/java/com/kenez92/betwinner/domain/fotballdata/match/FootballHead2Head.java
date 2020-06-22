package com.kenez92.betwinner.domain.fotballdata.match;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenez92.betwinner.domain.fotballdata.FootballTeam;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FootballHead2Head {
    @JsonProperty("numberOfMatches")
    private int numberOfMatches;

    @JsonProperty("totalGoals")
    private int totalGoals;

    @JsonProperty("homeTeam")
    private FootballTeam homeTeam;

    @JsonProperty("awayTeam")
    private FootballTeam awayTeam;
}
