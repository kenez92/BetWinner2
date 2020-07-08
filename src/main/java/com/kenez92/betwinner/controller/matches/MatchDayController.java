package com.kenez92.betwinner.controller.matches;

import com.kenez92.betwinner.domain.matches.MatchDayDto;
import com.kenez92.betwinner.service.matches.MatchDayService;
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
@RequestMapping("/v1/matches/rounds")
public class MatchDayController {
    private final MatchDayService matchDayService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MatchDayDto> getMatchDays() {
        log.info("Getting all match days");
        List<MatchDayDto> matchDayDtoList = matchDayService.getMatchDays();
        log.info("Return all match days: {}", matchDayDtoList);
        return matchDayDtoList;
    }

    @GetMapping(value = "/{matchDayId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MatchDayDto getMatchDay(@PathVariable Long matchDayId) {
        log.info("Getting match day id: {}", matchDayId);
        MatchDayDto matchDayDto = matchDayService.getMatchDay(matchDayId);
        log.info("Return match day: {}", matchDayDto);
        return matchDayDto;
    }
}
