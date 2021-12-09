package com.kenez92.betwinner.api.footballData.dto.matches;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FootballMatchList {
    @JsonProperty("count")
    private int count;

    @JsonProperty("filters")
    private FootballFilters filters;

    @JsonProperty("matches")
    private FootballMatch[] matches;
}
