package com.kenez92.betwinner.sheduler.football.data;

import com.kenez92.betwinner.domain.CompetitionDto;
import com.kenez92.betwinner.domain.CompetitionSeasonDto;
import com.kenez92.betwinner.domain.fotballdata.standings.FootballTable;
import com.kenez92.betwinner.service.CompetitionSeasonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SaveCompetitionSeason {
    private final CompetitionSeasonService competitionSeasonService;
    private final SaveCurrentMatchDay saveCurrentMatchDay;

    public void saveCompetitionSeason(CompetitionDto competitionDto, FootballTable footballTable) {
        String winner = "Season still in progress";
        if (footballTable.getSeason().getWinner() != null) {
            winner = footballTable.getSeason().getWinner().getName();
        }
        CompetitionSeasonDto tmpCompetitionSeasonDto = CompetitionSeasonDto.builder()
                .footballId(footballTable.getSeason().getId())
                .startDate(footballTable.getSeason().getStartDate())
                .endDate(footballTable.getSeason().getEndDate())
                .winner(winner)
                .competition(competitionDto)
                .build();
        CompetitionSeasonDto competitionSeasonDto;

        if (competitionSeasonService.competitionSeasonExistByFootballId(tmpCompetitionSeasonDto.getFootballId())) {
            competitionSeasonDto = competitionSeasonService.getCompetitionSeasonByFootballId(tmpCompetitionSeasonDto.getFootballId());
            log.info("This season already exist: {}", competitionSeasonDto);
            Long competitionSeasonDtoId = competitionSeasonDto.getId();
            //if (!competitionSeasonDto.equals(tmpCompetitionSeasonDto)) {
            if (!competitionSeasonDto.getFootballId().equals(tmpCompetitionSeasonDto.getFootballId()) ||
                    !competitionSeasonDto.getStartDate().equals(tmpCompetitionSeasonDto.getStartDate()) ||
                    !competitionSeasonDto.getEndDate().equals(tmpCompetitionSeasonDto.getEndDate()) ||
                    !competitionSeasonDto.getWinner().equals(tmpCompetitionSeasonDto.getWinner())) {
                tmpCompetitionSeasonDto.setId(competitionSeasonDtoId);
                competitionSeasonDto = competitionSeasonService.saveCompetitionSeason(tmpCompetitionSeasonDto);
                log.info("Updating season: {}", competitionSeasonDto);
            }
        } else {
            competitionSeasonDto = competitionSeasonService.saveCompetitionSeason(tmpCompetitionSeasonDto);
            log.info("Creating new competition season: {}", competitionSeasonDto);
        }
        saveCurrentMatchDay.saveCurrentMatchDay(competitionSeasonDto, footballTable);
    }
}
