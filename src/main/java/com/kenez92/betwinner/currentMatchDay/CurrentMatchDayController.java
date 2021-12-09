package com.kenez92.betwinner.currentMatchDay;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/competitions/seasons/rounds")
public class CurrentMatchDayController {
    private final CurrentMatchDayService currentMatchDayService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CurrentMatchDayDto> getCurrentMatches() {
        log.info("Get all current match days");
        List<CurrentMatchDayDto> currentMatchDayDtoList = currentMatchDayService.getCurrentMatchDays();
        log.info("Return all current match days: {}", currentMatchDayDtoList);
        return currentMatchDayDtoList;
    }

    @GetMapping(value = "/{currentMatchDayId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CurrentMatchDayDto getCurrentMatchDay(@PathVariable Long currentMatchDayId) {
        log.info("Get current match day by id: {}", currentMatchDayId);
        CurrentMatchDayDto currentMatchDayDto = currentMatchDayService.getCurrentMatchDay(currentMatchDayId);
        log.info("Return current match day: {}", currentMatchDayId);
        return currentMatchDayDto;
    }

    @GetMapping(value = "/season/{seasonId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CurrentMatchDayDto> getCurrentMatchDayBySeasonId(@PathVariable Long seasonId) {
        log.info("Get current match day by season id: {}", seasonId);
        List<CurrentMatchDayDto> currentMatchDayDtoList = currentMatchDayService.getCurrentMatchDaysByCompetitionSeasonId(seasonId);
        log.info("Return current match days: {}", currentMatchDayDtoList);
        return currentMatchDayDtoList;
    }
}
