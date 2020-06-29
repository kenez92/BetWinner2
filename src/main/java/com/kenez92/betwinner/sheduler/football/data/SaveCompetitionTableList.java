package com.kenez92.betwinner.sheduler.football.data;

import com.kenez92.betwinner.domain.CompetitionTableDto;
import com.kenez92.betwinner.domain.CurrentMatchDayDto;
import com.kenez92.betwinner.domain.fotballdata.standings.FootballTable;
import com.kenez92.betwinner.service.CompetitionTableService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SaveCompetitionTableList {
    private final CompetitionTableService competitionTableService;
    private final SaveCompetitionTableElement saveCompetitionTableElement;

    public void saveCompetitionTableList(CurrentMatchDayDto currentMatchDayDto, FootballTable footballTable) {
        final int size = footballTable.getFootballStandings().length;
        for (int i = 0; i < size; i++) {
            CompetitionTableDto resultCompetitionTableDto;
            CompetitionTableDto competitionTableDto = CompetitionTableDto.builder()
                    .stage(footballTable.getFootballStandings()[i].getStage())
                    .type(footballTable.getFootballStandings()[i].getType())
                    .currentMatchDay(currentMatchDayDto)
                    .build();
            resultCompetitionTableDto = process(competitionTableDto);
            saveCompetitionTableElement.saveCompetitionTableElement(resultCompetitionTableDto, footballTable);
        }
    }

    private CompetitionTableDto process(CompetitionTableDto competitionTableDto) {
        CompetitionTableDto tmpCompetitionTableDto;
        if (competitionTableService.existsByFields(competitionTableDto.getStage(),
                competitionTableDto.getType(), competitionTableDto.getCurrentMatchDay())) {
            log.info("This table already exists");
            tmpCompetitionTableDto = competitionTableService.getByFields(competitionTableDto.getStage(),
                    competitionTableDto.getType(), competitionTableDto.getCurrentMatchDay());
            if (!tmpCompetitionTableDto.equals(competitionTableDto)) {
                tmpCompetitionTableDto = competitionTableService.saveCompetitionTable(competitionTableDto);
                log.info("Updating table: {}", tmpCompetitionTableDto);
            }
        } else {
            tmpCompetitionTableDto = competitionTableService.saveCompetitionTable(competitionTableDto);
        }
        return competitionTableDto;
    }
}
