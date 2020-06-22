package com.kenez92.betwinner.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CurrentMatchDayDto {
    private Long id;
    private CompetitionSeasonDto competitionSeason;
    private List<CompetitionTableDto> competitionTableList;
}
