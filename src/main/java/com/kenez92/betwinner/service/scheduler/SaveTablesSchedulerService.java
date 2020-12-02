package com.kenez92.betwinner.service.scheduler;

import com.kenez92.betwinner.api.client.FootballClient;
import com.kenez92.betwinner.config.AvailableCompetitions;
import com.kenez92.betwinner.domain.fotballdata.standings.FootballTable;
import com.kenez92.betwinner.persistence.entity.table.Competition;
import com.kenez92.betwinner.persistence.entity.table.CompetitionSeason;
import com.kenez92.betwinner.persistence.entity.table.CompetitionTable;
import com.kenez92.betwinner.persistence.entity.table.CurrentMatchDay;
import com.kenez92.betwinner.service.scheduler.table.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class SaveTablesSchedulerService {
    private final AvailableCompetitions availableCompetitions;
    private final FootballClient footballClient;
    private final SaveCompetitionService saveCompetitionService;
    private final SaveCompetitionSeasonService saveCompetitionSeasonService;
    private final SaveCurrentMatchDayService saveCurrentMatchDayService;
    private final SaveCompetitionTableService saveCompetitionTableService;
    private final SaveCompetitionTableElementService saveCompetitionTableElementService;

    public void saveTables(int delayUsingFootballDataApi) throws InterruptedException {
        for (Long competitionId : availableCompetitions.getAvailableCompetitionList()) {
            TimeUnit.SECONDS.sleep(delayUsingFootballDataApi);
            FootballTable footballTable = footballClient.getTable(competitionId);
            Competition competition = saveCompetitionService.process(footballTable);
            CompetitionSeason competitionSeason = saveCompetitionSeasonService.process(
                    competition, footballTable.getSeason());
            CurrentMatchDay currentMatchDay = saveCurrentMatchDayService.process(
                    competitionSeason, footballTable.getSeason().getCurrentMatchday());
            for (int i = 0; i < footballTable.getFootballStandings().length; i++) {
                CompetitionTable competitionTable = saveCompetitionTableService.process(
                        currentMatchDay, footballTable.getFootballStandings()[i]);
                saveCompetitionTableElementService.process(
                        competitionTable, footballTable.getFootballStandings()[i].getFootballTableElement());
            }
        }
    }
}
