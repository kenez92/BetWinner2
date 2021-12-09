package com.kenez92.betwinner.api.footballData.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FootballArea {
    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;
}
