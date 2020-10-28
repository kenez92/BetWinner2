package com.kenez92.betwinner.service.scheduler.tables;

import com.kenez92.betwinner.domain.fotballdata.standings.FootballStandings;
import com.kenez92.betwinner.domain.table.CompetitionSeasonDto;
import com.kenez92.betwinner.domain.table.CurrentMatchDayDto;
import com.kenez92.betwinner.service.table.CurrentMatchDayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SaveFootballCurrentMatchDay {
    private final CurrentMatchDayService currentMatchDayService;

    public CurrentMatchDayDto currentMatchDayDto(CompetitionSeasonDto competitionSeasonDto,
                                                 FootballStandings[] footballStandings) {
        return null;

    }
}
