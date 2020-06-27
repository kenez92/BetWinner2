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

    public void saveCurrentMatchDay(CompetitionSeasonDto competitionSeasonDto, FootballTable footballTable) {
        Integer currentMatchDayNumber = footballTable.getSeason().getCurrentMatchday();
        CurrentMatchDayDto tmpCurrentMatchDayDto = CurrentMatchDayDto.builder()
                .matchDay(currentMatchDayNumber)
                .competitionSeason(competitionSeasonDto)
                .build();
        CurrentMatchDayDto currentMatchDayDto;
        if (currentMatchDayService.currentMatchDayExistBySeasonAndMatchDay(competitionSeasonDto, currentMatchDayNumber)) {
            log.info("This match day already exist");
            currentMatchDayDto = currentMatchDayService.getCurrentMatchDayBySeasonAndMatchDay(competitionSeasonDto, currentMatchDayNumber);
            if (!currentMatchDayDto.equals(tmpCurrentMatchDayDto)) {
                log.info("Updating match day");
                currentMatchDayDto = tmpCurrentMatchDayDto;
            }
        } else {
            currentMatchDayDto = tmpCurrentMatchDayDto;
            log.info("Creating new match day");
        }
        currentMatchDayService.saveCurrentMatchDay(currentMatchDayDto);
    }
}
