package com.kenez92.betwinner.match;

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
@RequestMapping("/v1/matches")
public class MatchController {
    private final MatchService matchService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MatchDto> getMatches() {
        log.info("Getting all matches");
        List<MatchDto> matchDtoList = matchService.getMatches();
        log.info("Return matches: {}", matchDtoList);
        return matchDtoList;
    }

    @GetMapping(value = "/{matchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MatchDto getMatch(@PathVariable Long matchId) {
        log.info("Get match by id: {}", matchId);
        MatchDto matchDto = matchService.getMatch(matchId);
        log.info("Return match: {}", matchDto);
        return matchDto;
    }

    @GetMapping(value = "date={date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MatchDto> getMatchesAtDay(@PathVariable String date) {
        log.info("Getting matches at day :{}", date);
        List<MatchDto> matchDtoList = matchService.getMatchesAtDay(date);
        log.info("Return matches from day: {}{}", date, matchDtoList);
        return matchDtoList;
    }
}
