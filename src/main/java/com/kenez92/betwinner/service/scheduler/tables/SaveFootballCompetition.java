package com.kenez92.betwinner.service.scheduler.tables;

import com.kenez92.betwinner.domain.fotballdata.standings.FootballTable;
import com.kenez92.betwinner.domain.table.CompetitionDto;
import com.kenez92.betwinner.service.table.CompetitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@Service
public class SaveFootballCompetition {
    private final CompetitionService competitionService;

    public CompetitionDto saveCompetition(FootballTable footballTable) {
        Long competitionId = footballTable.getFootballCompetition().getId();
        CompetitionDto competitionDto = null;
        if (!competitionService.competitionExistByFootballId(competitionId)) {
            competitionDto = CompetitionDto.builder()
                    .footballId(competitionId)
                    .name(footballTable.getFootballCompetition().getName())
                    .competitionSeasonList(new ArrayList<>())
                    .build();
            log.info("Saving new competition: {}", competitionDto);
            CompetitionDto savedCompetitionDto = competitionService.saveCompetition(competitionDto);
            log.info("Saved competition : {}", savedCompetitionDto);
        } else {
            competitionDto = competitionService.getCompetitionByFootballId(competitionId);
            log.info("Competition with this id already exists : {}", competitionId);
        }
        return competitionDto;
    }
}
