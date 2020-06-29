package com.kenez92.betwinner.sheduler.football.data;

import com.kenez92.betwinner.domain.CompetitionSeasonDto;
import com.kenez92.betwinner.domain.CurrentMatchDayDto;
import com.kenez92.betwinner.domain.fotballdata.standings.FootballTable;
import com.kenez92.betwinner.service.CurrentMatchDayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SaveCurrentMatchDay {
    private final CurrentMatchDayService currentMatchDayService;
    private final SaveCompetitionTableList saveCompetitionTableList;

    public void saveCurrentMatchDay(CompetitionSeasonDto competitionSeasonDto, FootballTable footballTable) {
        Integer currentMatchDayNumber = footballTable.getSeason().getCurrentMatchday();
        CurrentMatchDayDto tmpCurrentMatchDayDto = CurrentMatchDayDto.builder()
                .matchDay(currentMatchDayNumber)
                .competitionSeason(competitionSeasonDto)
                .build();
        CurrentMatchDayDto currentMatchDayDto;
        if (currentMatchDayService.currentMatchDayExistBySeasonAndMatchDay(competitionSeasonDto, currentMatchDayNumber)) {
            currentMatchDayDto = currentMatchDayService.getCurrentMatchDayBySeasonAndMatchDay(competitionSeasonDto, currentMatchDayNumber);
            log.info("This match day already exist");
            if (!currentMatchDayDto.equals(tmpCurrentMatchDayDto)) {
                currentMatchDayDto = currentMatchDayService.saveCurrentMatchDay(tmpCurrentMatchDayDto);
                log.info("Updating match day: {}", currentMatchDayDto);
            }
        } else {
            currentMatchDayDto = currentMatchDayService.saveCurrentMatchDay(tmpCurrentMatchDayDto);
            log.info("Creating new match day: {}", currentMatchDayDto);
        }
        saveCompetitionTableList.saveCompetitionTableList(currentMatchDayDto, footballTable);
    }
}
