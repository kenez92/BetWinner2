package com.kenez92.betwinner.api.footballData.dto.match;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenez92.betwinner.api.footballData.dto.matches.FootballMatch;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FootballMatchById {
    @JsonProperty("head2head")
    private FootballHead2Head head2head;

    @JsonProperty("match")
    private FootballMatch match;
}
