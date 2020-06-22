package com.kenez92.betwinner.domain.fotballdata.matches;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FootballFilters {
    @JsonProperty("dateFrom")
    private LocalDate dateFrom;
    @JsonProperty("dateTo")
    private LocalDate dateTo;
}
