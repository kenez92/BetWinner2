package com.kenez92.betwinner.controller.rest.tables;

import com.kenez92.betwinner.domain.table.CompetitionTableElementDto;
import com.kenez92.betwinner.service.table.CompetitionTableElementService;
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
@RequestMapping("/v1/competitions/seasons/rounds/tables/teams")
public class CompetitionTableElementController {
    private final CompetitionTableElementService competitionTableElementService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CompetitionTableElementDto> getCompetitionTableElements() {
        log.info("Getting all competition table elements");
        List<CompetitionTableElementDto> competitionTableElementDtos = competitionTableElementService
                .getCompetitionTableElements();
        log.info("Return all competition table elements: {}", competitionTableElementDtos);
        return competitionTableElementDtos;
    }

    @GetMapping(value = "/{competitionTableElementId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompetitionTableElementDto getCompetitionTableElement(@PathVariable Long competitionTableElementId) {
        log.info("Getting competition table element id: {}", competitionTableElementId);
        CompetitionTableElementDto competitionTableElementDto = competitionTableElementService
                .getCompetitionTableElement(competitionTableElementId);
        log.info("Return competition table element: {}", competitionTableElementDto);
        return competitionTableElementDto;
    }
}
