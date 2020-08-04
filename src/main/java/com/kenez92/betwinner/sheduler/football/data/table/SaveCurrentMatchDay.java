package com.kenez92.betwinner.sheduler.football.data.table;

import com.kenez92.betwinner.domain.fotballdata.standings.FootballTable;
import com.kenez92.betwinner.domain.table.CompetitionSeasonDto;
import com.kenez92.betwinner.domain.table.CurrentMatchDayDto;
import com.kenez92.betwinner.service.table.CurrentMatchDayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SaveCurrentMatchDay {
    private final CurrentMatchDayService currentMatchDayService;
    private final SaveCompetitionTableList saveCompetitionTableList;

    public void saveCurrentMatchDay(final CompetitionSeasonDto competitionSeasonDto, final FootballTable footballTable) {
        Integer currentMatchDayNumber = footballTable.getSeason().getCurrentMatchday();
        if(currentMatchDayNumber == null) {
            currentMatchDayNumber = 0;
        }
        CurrentMatchDayDto tmpCurrentMatchDayDto = CurrentMatchDayDto.builder()
                .matchDay(currentMatchDayNumber)
                .competitionSeason(competitionSeasonDto)
                .build();
        CurrentMatchDayDto currentMatchDayDto;
        if (currentMatchDayService.currentMatchDayExistBySeasonAndMatchDay(competitionSeasonDto, currentMatchDayNumber)) {
            currentMatchDayDto = currentMatchDayService.getCurrentMatchDayBySeasonAndMatchDay(competitionSeasonDto, currentMatchDayNumber);
            log.info("This match day already exist: {}", currentMatchDayDto);
            if (!currentMatchDayDto.getMatchDay().equals(tmpCurrentMatchDayDto.getMatchDay()) ||
                    !currentMatchDayDto.getCompetitionSeason().getId()
                            .equals(tmpCurrentMatchDayDto.getCompetitionSeason().getId())) {
                tmpCurrentMatchDayDto.setId(competitionSeasonDto.getId());
                currentMatchDayDto = currentMatchDayService.saveCurrentMatchDay(tmpCurrentMatchDayDto);
                log.info("Updating match day: {}", currentMatchDayDto);
            }
        } else {
            currentMatchDayDto = currentMatchDayService.saveCurrentMatchDay(tmpCurrentMatchDayDto);
            log.info("Saved match day: {}", currentMatchDayDto);
        }
        saveCompetitionTableList.saveCompetitionTableList(currentMatchDayDto, footballTable);
    }
}
