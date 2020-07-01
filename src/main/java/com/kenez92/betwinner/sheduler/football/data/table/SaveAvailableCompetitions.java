package com.kenez92.betwinner.sheduler.football.data.table;

import com.kenez92.betwinner.domain.table.CompetitionDto;
import com.kenez92.betwinner.domain.fotballdata.standings.FootballTable;
import com.kenez92.betwinner.service.competition.CompetitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SaveAvailableCompetitions {
    private final CompetitionService competitionService;
    private final SaveCompetitionSeason saveCompetitionSeason;

    public void saveCompetitions(final FootballTable footballTable) {
        CompetitionDto tmpCompetitionDto = CompetitionDto.builder()
                .footballId((footballTable.getFootballCompetition().getId()))
                .name(footballTable.getFootballCompetition().getName())
                .build();
        Long competitionId = footballTable.getFootballCompetition().getId();
        CompetitionDto competitionDto;
        if (competitionService.competitionExistByFootballId(competitionId)) {
            competitionDto = competitionService.getCompetitionByFootballId(competitionId);
            log.info("Competition already exist: {}", competitionDto);
            if (!competitionDto.getFootballId().equals(tmpCompetitionDto.getFootballId()) ||
                    !competitionDto.getName().equals(tmpCompetitionDto.getName())) {
                tmpCompetitionDto.setId(competitionDto.getId());
                competitionDto = competitionService.saveCompetition(tmpCompetitionDto);
                log.info("Updating competition: {}", competitionDto);
            }
        } else {
            competitionDto = competitionService.saveCompetition(CompetitionDto.builder()
                    .footballId(footballTable.getFootballCompetition().getId())
                    .name(footballTable.getFootballCompetition().getName())
                    .build());
            log.info("Saving new competition: {}", competitionDto);
        }
        saveCompetitionSeason.saveCompetitionSeason(competitionDto, footballTable);
    }
}
