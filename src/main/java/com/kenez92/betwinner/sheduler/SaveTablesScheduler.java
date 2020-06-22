package com.kenez92.betwinner.sheduler;

import com.kenez92.betwinner.domain.CompetitionDto;
import com.kenez92.betwinner.domain.CompetitionTableDto;
import com.kenez92.betwinner.domain.CompetitionTableElementDto;
import com.kenez92.betwinner.domain.fotballdata.AvailableCompetitions;
import com.kenez92.betwinner.domain.fotballdata.standings.FootballStandings;
import com.kenez92.betwinner.domain.fotballdata.standings.FootballTableElement;
import com.kenez92.betwinner.domain.fotballdata.standings.FootballTables;
import com.kenez92.betwinner.football.client.FootballClient;
import com.kenez92.betwinner.service.CompetitionService;
import com.kenez92.betwinner.service.CompetitionTableElementService;
import com.kenez92.betwinner.service.CompetitionTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

    @Scheduled(fixedDelay = 10000)
    private void saveTable() {
        List<Long> availableCompetition = availableCompetitions.getAvailableCompetitionList();
        for (Long competitionId : availableCompetition) {
            FootballTables footballTables = footballClient.getTable(competitionId);
            String winner = "Season still in progress";
            if (footballTables.getSeason().getWinner() != null) {
                winner = footballTables.getSeason().getWinner().getName();
            }
            CompetitionDto competitionDto = competitionService.saveCompetition(CompetitionDto.builder()
                    .competitionId(footballTables.getFootballCompetition().getId())
                    .name(footballTables.getFootballCompetition().getName())
                    .seasonId(footballTables.getSeason().getId())
                    .seasonStart(footballTables.getSeason().getStartDate())
                    .seasonEnd(footballTables.getSeason().getEndDate())
                    .currentMatchDay(footballTables.getSeason().getCurrentMatchday())
                    .winner(winner)
                    .build());

            List<FootballStandings> footballStandingsList = Arrays.asList(footballTables.getFootballStandings());

            for (FootballStandings footballStandings : footballStandingsList) {
                CompetitionTableDto competitionTableDto
                        = competitionTableService.saveCompetitionTable(CompetitionTableDto.builder()
                        .competition(competitionDto)
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
