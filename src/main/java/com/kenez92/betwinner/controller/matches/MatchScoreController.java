package com.kenez92.betwinner.controller.matches;

import com.kenez92.betwinner.domain.matches.MatchScoreDto;
import com.kenez92.betwinner.service.matches.MatchScoreService;
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
@RequestMapping("/v1/matches/score")
public class MatchScoreController {
    private final MatchScoreService matchScoreService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MatchScoreDto> getMatchScores() {
        log.info("Getting all match scores");
        List<MatchScoreDto> matchScoreDtoList = matchScoreService.getMatchScores();
        log.info("Return all match scores: {}", matchScoreDtoList);
        return matchScoreDtoList;
    }

    @GetMapping(value = "/{matchScoreId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MatchScoreDto getMatchScore(@PathVariable Long matchScoreId) {
        log.info("Getting match score by id: {}", matchScoreId);
        MatchScoreDto matchScoreDto = matchScoreService.getMatchScore(matchScoreId);
        log.info("Return match score: {}", matchScoreDto);
        return matchScoreDto;
    }
}
