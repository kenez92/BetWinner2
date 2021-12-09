package com.kenez92.betwinner.currentMatchDay;

import com.kenez92.betwinner.competitionSeason.CompetitionSeasonDto;
import com.kenez92.betwinner.competitionTable.CompetitionTableDto;
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
    private Integer matchDay;
    private CompetitionSeasonDto competitionSeason;
    private List<CompetitionTableDto> competitionTableList;
}
