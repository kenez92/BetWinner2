package com.kenez92.betwinner.controller.table;

import com.kenez92.betwinner.domain.table.CompetitionSeasonDto;
import com.kenez92.betwinner.service.table.CompetitionSeasonService;
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
@RequestMapping("/v1/competitions/seasons")
public class CompetitionSeasonController {
    private final CompetitionSeasonService competitionSeasonService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CompetitionSeasonDto> getCompetitionSeasons() {
        log.info("Get all competition seasons");
        List<CompetitionSeasonDto> competitionSeasons = competitionSeasonService.getCompetitionSeasons();
        log.info("Return all competition seasons");
        return competitionSeasons;
    }

    @GetMapping(value = "/{competitionSeasonId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompetitionSeasonDto getCompetitionSeason(@PathVariable Long competitionSeasonId) {
        log.info("Get competition season by id: {}", competitionSeasonId);
        CompetitionSeasonDto competitionSeasonDto = competitionSeasonService.getCompetitionSeason(competitionSeasonId);
        log.info("Return competition season by id: {}", competitionSeasonDto);
        return competitionSeasonDto;
    }
}
