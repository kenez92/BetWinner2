package com.kenez92.betwinner.domain.fotballdata.standings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FootballTableElement {
    @JsonProperty("position")
    private int position;

    @JsonProperty("team")
    private FootballStandingTeam team;

    @JsonProperty("playedGames")
    private int playedGames;

    @JsonProperty("won")
    private int won;

    @JsonProperty("draw")
    private int draw;

    @JsonProperty("lost")
    private int lost;

    @JsonProperty("points")
    private int points;

    @JsonProperty("goalsFor")
    private int goalsScored;

    @JsonProperty("goalsAgainst")
    private int goalsLost;

    @JsonProperty("goalDifference")
    private int goalDifference;
}
