package com.kenez92.betwinner.domain.fotballdata.standings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FootballStandings {
    @JsonProperty("stage")
    private String stage;

    @JsonProperty("type")
    private String type;

    @JsonProperty("group")
    private String group;

    @JsonProperty("table")
    private FootballTableElement[] footballTableElement;
}
