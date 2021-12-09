package com.kenez92.betwinner.api.footballData.dto.matches;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FootballFilters {
    @JsonProperty("dateFrom")
    private LocalDate dateFrom;

    @JsonProperty("dateTo")
    private LocalDate dateTo;
}
