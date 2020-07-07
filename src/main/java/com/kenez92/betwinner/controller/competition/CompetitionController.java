package com.kenez92.betwinner.controller.competition;

import com.kenez92.betwinner.domain.table.CompetitionDto;
import com.kenez92.betwinner.service.competition.CompetitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/v1/competitions")
public class CompetitionController {
    private final CompetitionService competitionService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CompetitionDto> getCompetitions() {
        log.info("Get all competitions");
        List<CompetitionDto> competitions = competitionService.getCompetitions();
        log.info("Return all competitions");
        return competitions;
    }

    @GetMapping(value = "/{competitionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompetitionDto getCompetition(@PathVariable Long competitionId) {
        log.info("Get competition by id: {}", competitionId);
        CompetitionDto competitionDto = competitionService.getCompetition(competitionId);
        log.info("Return competition by id: {}", competitionDto);
        return competitionDto;
    }
}
