package com.kenez92.betwinner.domain.fotballdata;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FootballArea {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;
}
