package com.kenez92.betwinner.sheduler;

import com.kenez92.betwinner.domain.Competition;
import com.kenez92.betwinner.domain.CompetitionTable;
import com.kenez92.betwinner.domain.CompetitionTableElement;
import com.kenez92.betwinner.domain.fotballdata.AvailableCompetitions;
import com.kenez92.betwinner.domain.fotballdata.matches.FootballMatchList;
import com.kenez92.betwinner.domain.fotballdata.standings.FootballStandings;
import com.kenez92.betwinner.domain.fotballdata.standings.FootballTableElement;
import com.kenez92.betwinner.domain.fotballdata.standings.FootballTables;
import com.kenez92.betwinner.football.client.FootballClient;
import com.kenez92.betwinner.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class SaveDataScheduler {
    private final AvailableCompetitions availableCompetitions;
    private final FootballClient footballClient;
    private final MatchService matchService;


    private List<Long> getAllMatchId() {
        LocalDate localDate = LocalDate.now();
        FootballMatchList footballMatchList = footballClient.getMatches(localDate);
        List<Long> matchIdList = new ArrayList<>();
        Long matchId;
        int size = footballMatchList.getCount();
        for (int i = 0; i < size; i++) {
            matchId = footballMatchList.getMatches()[i].getId();
            matchIdList.add(matchId);
        }
        return matchIdList;
    }
}
