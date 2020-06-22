package com.kenez92.betwinner.controller;

import com.kenez92.betwinner.domain.fotballdata.matches.FootballMatchList;
import com.kenez92.betwinner.domain.fotballdata.standings.FootballTables;
import com.kenez92.betwinner.football.client.FootballClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class Controller {
    private final FootballClient footballClient;

    @GetMapping("/getMatches")
    public FootballMatchList getMatches() {
        return footballClient.getMatches(LocalDate.now());
    }

    @GetMapping("getTables")
    public FootballTables getTables() {
        return footballClient.getTable(2002L);
    }
}
