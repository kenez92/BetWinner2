package com.kenez92.betwinner.controller.table;

import com.kenez92.betwinner.domain.table.CompetitionTableDto;
import com.kenez92.betwinner.service.table.CompetitionTableService;
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
@RequestMapping("/v1/competitions/seasons/rounds/tables")
public class CompetitionTableController {
    private final CompetitionTableService competitionTableService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CompetitionTableDto> getCompetitionTables() {
        log.info("Get all competition table");
        List<CompetitionTableDto> competitionTableDtoList = competitionTableService.getCompetitionTables();
        log.info("Return all competition table: {}", competitionTableDtoList);
        return competitionTableDtoList;
    }

    @GetMapping(value = "/{competitionTableId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompetitionTableDto getCompetitionTable(@PathVariable Long competitionTableId) {
        log.info("Get competition table id: {}", competitionTableId);
        CompetitionTableDto competitionTableDto = competitionTableService.getCompetitionTable(competitionTableId);
        log.info("Return competition table: {}", competitionTableDto);
        return competitionTableDto;
    }
}
