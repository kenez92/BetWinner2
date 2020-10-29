package com.kenez92.betwinner.service.scheduler.tables;

import com.kenez92.betwinner.domain.fotballdata.standings.FootballStandings;
import com.kenez92.betwinner.domain.table.CompetitionTableDto;
import com.kenez92.betwinner.domain.table.CurrentMatchDayDto;
import com.kenez92.betwinner.service.table.CompetitionTableService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SaveCompetitionTable {
    private final CompetitionTableService competitionTableService;


    public CompetitionTableDto saveCompetitionTable(CurrentMatchDayDto currentMatchDayDto,
                                                    FootballStandings footballStanding) {
        CompetitionTableDto competitionTableDto = null;

        if (competitionTableService.existsByFields(footballStanding.getStage(),
                footballStanding.getType(), currentMatchDayDto)) {
            competitionTableDto = competitionTableService.getByFields(footballStanding.getStage(),
                    footballStanding.getType(), currentMatchDayDto);
            log.info("Competition table already exists: {}", competitionTableDto);
        } else {
            String stage = null;
            String type = null;
            if (footballStanding.getStage() != null) {
                stage = footballStanding.getStage();
            }
            if (footballStanding.getType() != null) {
                type = footballStanding.getType();
            }
            CompetitionTableDto tmpCompetitionTableDto = CompetitionTableDto.builder()
                    .stage(stage)
                    .type(type)
                    .currentMatchDay(currentMatchDayDto)
                    .build();
            competitionTableDto = competitionTableService.saveCompetitionTable(tmpCompetitionTableDto);
            log.info("Saved new competition table : {}", competitionTableDto);
        }

        return competitionTableDto;
    }
}
