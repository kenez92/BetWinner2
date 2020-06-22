package com.kenez92.betwinner.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CompetitionSeasonDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String winner;
    private CompetitionDto competition;
    private List<CurrentMatchDayDto> currentMatchDayList;
}
