package com.kenez92.betwinner.competitionTable;

import com.kenez92.betwinner.competitionTableElement.CompetitionTableElementDto;
import com.kenez92.betwinner.currentMatchDay.CurrentMatchDayDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CompetitionTableDto {
    private Long id;
    private String stage;
    private String type;
    private CurrentMatchDayDto currentMatchDay;
    private List<CompetitionTableElementDto> competitionTableElements;
}
