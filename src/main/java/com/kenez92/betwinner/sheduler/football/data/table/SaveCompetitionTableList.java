package com.kenez92.betwinner.service.scheduler.football.data.table;

import com.kenez92.betwinner.domain.fotballdata.standings.FootballTable;
import com.kenez92.betwinner.domain.table.CompetitionTableDto;
import com.kenez92.betwinner.domain.table.CurrentMatchDayDto;
import com.kenez92.betwinner.service.table.CompetitionTableService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SaveCompetitionTableList {
    private final CompetitionTableService competitionTableService;
    private final SaveCompetitionTableElement saveCompetitionTableElement;

    public void saveCompetitionTableList(final CurrentMatchDayDto currentMatchDayDto, final FootballTable footballTable) {
        final int size = footballTable.getFootballStandings().length;
        for (Integer i = 0; i < size; i++) {
            CompetitionTableDto competitionTableDto = CompetitionTableDto.builder()
                    .stage(footballTable.getFootballStandings()[i].getStage())
                    .type(footballTable.getFootballStandings()[i].getType())
                    .currentMatchDay(currentMatchDayDto)
                    .build();

            if (competitionTableService.existsByFields(competitionTableDto.getStage(),
                    competitionTableDto.getType(), competitionTableDto.getCurrentMatchDay())) {
                competitionTableDto = competitionTableService.getByFields(competitionTableDto.getStage(),
                        competitionTableDto.getType(), competitionTableDto.getCurrentMatchDay());
                log.info("This table already exists: {}", competitionTableDto);
            } else {
                competitionTableDto = competitionTableService.saveCompetitionTable(competitionTableDto);
                log.info("Saved table :{}", competitionTableDto);
            }
            saveCompetitionTableElement.saveCompetitionTableElement(competitionTableDto, footballTable, i);
        }
    }
}
