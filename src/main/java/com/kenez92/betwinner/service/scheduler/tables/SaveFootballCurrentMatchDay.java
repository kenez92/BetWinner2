package com.kenez92.betwinner.service.scheduler.tables;

import com.kenez92.betwinner.domain.fotballdata.standings.FootballStandings;
import com.kenez92.betwinner.domain.table.CompetitionSeasonDto;
import com.kenez92.betwinner.domain.table.CurrentMatchDayDto;
import com.kenez92.betwinner.service.table.CurrentMatchDayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@Service
public class SaveFootballCurrentMatchDay {
    private final CurrentMatchDayService currentMatchDayService;

    public CurrentMatchDayDto saveCurrentMatchDayDto(CompetitionSeasonDto competitionSeasonDto,
                                                     FootballStandings[] footballStandings, Integer currentMatchDayNumber) {
        CurrentMatchDayDto currentMatchDayDto = null;
        if (currentMatchDayService.currentMatchDayExistBySeasonAndMatchDay(competitionSeasonDto, currentMatchDayNumber)) {
            currentMatchDayDto = currentMatchDayService.getCurrentMatchDayBySeasonAndMatchDay(competitionSeasonDto,
                    currentMatchDayNumber);
            log.info("Current match day already exist : {}", currentMatchDayDto);
        } else {
            CurrentMatchDayDto tmpCurrentMatchDayDto = CurrentMatchDayDto.builder()
                    .matchDay(currentMatchDayNumber)
                    .competitionSeason(competitionSeasonDto)
                    .competitionTableList(new ArrayList<>())
                    .build();
            currentMatchDayDto = currentMatchDayService.saveCurrentMatchDay(tmpCurrentMatchDayDto);
            log.info("Saved new current match day : {}", currentMatchDayDto);
        }
        return currentMatchDayDto;
    }
}
