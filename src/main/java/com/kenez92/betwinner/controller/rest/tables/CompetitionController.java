package com.kenez92.betwinner.controller.rest.tables;

import com.kenez92.betwinner.domain.table.CompetitionDto;
import com.kenez92.betwinner.service.table.CompetitionService;
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

    @GetMapping(value = "/name/{competitionName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompetitionDto getCompetitionById(@PathVariable String competitionName) {
        log.info("Get competition by name: {}", competitionName);
        CompetitionDto competitionDto = competitionService.getByName(competitionName);
        log.info("Return competition : {}", competitionDto);
        return competitionDto;
    }
}
