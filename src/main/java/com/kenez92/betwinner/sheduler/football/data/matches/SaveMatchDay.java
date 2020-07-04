package com.kenez92.betwinner.sheduler.football.data.matches;

import com.kenez92.betwinner.domain.fotballdata.matches.FootballMatchList;
import com.kenez92.betwinner.domain.matches.MatchDayDto;
import com.kenez92.betwinner.service.matches.MatchDayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SaveMatchDay {
    private final MatchDayService matchDayService;

    public MatchDayDto saveMatchDay(final FootballMatchList footballMatchList) {
        MatchDayDto matchDayDto;
        MatchDayDto tmpMatchDayDto = MatchDayDto.builder()
                .localDate(footballMatchList.getFilters().getDateFrom())
                .build();
        if (matchDayService.existByLocalDate(tmpMatchDayDto.getLocalDate())) {
            matchDayDto = matchDayService.getByLocalDate(tmpMatchDayDto.getLocalDate());
            log.info("Match day already exists: {}", matchDayDto);
        } else {
            matchDayDto = matchDayService.saveMatchDay(tmpMatchDayDto);
            log.info("Saved match day: {}", matchDayDto);
        }
        return matchDayDto;
    }
}
