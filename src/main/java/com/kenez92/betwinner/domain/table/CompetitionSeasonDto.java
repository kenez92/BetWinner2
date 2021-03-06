package com.kenez92.betwinner.domain.table;

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
    private Long footballId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String winner;
    private CompetitionDto competition;
    private List<CurrentMatchDayDto> currentMatchDayList;
}
