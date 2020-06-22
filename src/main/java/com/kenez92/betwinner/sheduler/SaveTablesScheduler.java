package com.kenez92.betwinner.sheduler;

import com.kenez92.betwinner.domain.*;
import com.kenez92.betwinner.domain.fotballdata.AvailableCompetitions;
import com.kenez92.betwinner.domain.fotballdata.standings.FootballStandings;
import com.kenez92.betwinner.domain.fotballdata.standings.FootballTableElement;
import com.kenez92.betwinner.domain.fotballdata.standings.FootballTables;
import com.kenez92.betwinner.football.client.FootballClient;
import com.kenez92.betwinner.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class SaveTablesScheduler {
    private final AvailableCompetitions availableCompetitions;
    private final FootballClient footballClient;
    private final CompetitionService competitionService;
    private final CompetitionTableElementService competitionTableElementService;
    private final CompetitionTableService competitionTableService;
    private final CompetitionSeasonService competitionSeasonService;
    private final CurrentMatchDayService currentMatchDayService;

    @Scheduled(fixedDelay = 10000)
    private void saveTable() {
        List<Long> availableCompetition = availableCompetitions.getAvailableCompetitionList();
        for (Long competitionId : availableCompetition) {
            FootballTables footballTables = footballClient.getTable(competitionId);

            CompetitionDto competitionDto = competitionService.saveCompetition(CompetitionDto.builder()
                    .id(footballTables.getFootballCompetition().getId())
                    .name(footballTables.getFootballCompetition().getName())
                    .build());
            String winner = "Season still in progress!";
            if (footballTables.getSeason().getWinner() != null) {
                winner = footballTables.getSeason().getWinner().getName();
            }
            System.out.println(competitionDto);
            CompetitionSeasonDto competitionSeasonDto
                    = competitionSeasonService.saveCompetitionSeason(CompetitionSeasonDto.builder()
                    .id(footballTables.getSeason().getId())
                    .competition(competitionDto)
                    .startDate(footballTables.getSeason().getStartDate())
                    .endDate(footballTables.getSeason().getEndDate())
                    .winner(winner)
                    .build());

            CurrentMatchDayDto currentMatchDayDto = currentMatchDayService.saveCurrentMatchDay(CurrentMatchDayDto.builder()
                    .competitionSeason(competitionSeasonDto)
                    .build());

            List<FootballStandings> footballStandingsList = Arrays.asList(footballTables.getFootballStandings());

            for (FootballStandings footballStandings : footballStandingsList) {
                CompetitionTableDto competitionTableDto
                        = competitionTableService.saveCompetitionTable(CompetitionTableDto.builder()
                        .currentMatchDay(currentMatchDayDto)
                        .stage(footballStandings.getStage())
                        .type(footballStandings.getType())
                        .build());
                List<FootballTableElement> footballTableElementList = Arrays.asList(footballStandings.getFootballTableElement());


                for (FootballTableElement footballTableElement : footballTableElementList) {
                    CompetitionTableElementDto competitionTableElementDto
                            = competitionTableElementService.saveCompetitionTableElement(CompetitionTableElementDto.builder()
                            .competitionTable(competitionTableDto)
                            .name(footballTableElement.getTeam().getName())
                            .position(footballTableElement.getPosition())
                            .playedGames(footballTableElement.getPlayedGames())
                            .won(footballTableElement.getWon())
                            .draw(footballTableElement.getDraw())
                            .lost(footballTableElement.getLost())
                            .points(footballTableElement.getPoints())
                            .goalsFor(footballTableElement.getGoalsScored())
                            .goalsAgainst(footballTableElement.getGoalsLost())
                            .goalDifference(footballTableElement.getGoalDifference())
                            .build());
                }
            }
        }
    }
}
