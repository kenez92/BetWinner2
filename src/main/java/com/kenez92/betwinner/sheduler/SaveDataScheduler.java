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

    private void saveTable() {
        CompetitionTableElement competitionTableElement;
        CompetitionTable competitionTable;
        List<CompetitionTableElement> competitionTableElementList = new ArrayList<>();
        List<Long> availableCompetitionList = availableCompetitions.getAvailableCompetitionList();
        for (int i = 0; i < availableCompetitionList.size(); i++) {
            FootballTables footballTables = footballClient.getTable(availableCompetitionList.get(i));
            List<FootballStandings> footballStandingsList = Arrays.asList(footballTables.getFootballStandings());

            for (int j = 0; j < footballStandingsList.size(); j++) {
                List<FootballTableElement> footballTableElementsList
                        = Arrays.asList(footballStandingsList.get(j).getFootballTableElement());


                for (int k = 0; k < footballTableElementsList.size(); k++) {
                    competitionTableElementList.add(CompetitionTableElement.builder()
                            .name(footballTableElementsList.get(k).getTeam().getName())
                            .position(footballTableElementsList.get(k).getPosition())
                            .playedGames(footballTableElementsList.get(k).getPlayedGames())
                            .won(footballTableElementsList.get(k).getWon())
                            .draw(footballTableElementsList.get(k).getDraw())
                            .lost(footballTableElementsList.get(k).getLost())
                            .points(footballTableElementsList.get(k).getPoints())
                            .goalsFor(footballTableElementsList.get(k).getGoalsScored())
                            .goalsAgainst(footballTableElementsList.get(k).getGoalsLost())
                            .goalDifference(footballTableElementsList.get(k).getGoalDifference())
                            .build());
                }
                List<CompetitionTableElement> competitionTableElements = new ArrayList<>();
            }
        }
    }

    private void saveTable2() {
        List<Long> competitionsId = availableCompetitions.getAvailableCompetitionList();
        for (Long competitionId : competitionsId) {
            FootballTables footballTables = footballClient.getTable(competitionId);
            Competition competition = Competition.builder()
                    .competitionId(footballTables.getFootballCompetition().getId())
                    .name(footballTables.getFootballCompetition().getName())
                    .seasonId(footballTables.getSeason().getId())
                    .seasonStart(footballTables.getSeason().getStartDate())
                    .seasonEnd(footballTables.getSeason().getEndDate())
                    .currentMatchDay(footballTables.getSeason().getCurrentMatchday())
                    .winner(footballTables.getSeason().getWinner().getName())
                    .build();
            List<FootballStandings> footballStandingsList = Arrays.asList(footballTables.getFootballStandings());
            for (FootballStandings footballStandings : footballStandingsList) {
                CompetitionTable competitionTable = CompetitionTable.builder()
                        .competition(competition)
                        .stage(footballStandings.getStage())
                        .type(footballStandings.getType())
                        .build();
                List<FootballTableElement> footballTableElementList = Arrays.asList(footballStandings.getFootballTableElement());

                for (FootballTableElement footballTableElement : footballTableElementList) {
                    competitionTable.getCompetitionTableElements().add(CompetitionTableElement.builder()
                            .name(footballTableElement.getTeam().getName())
                            .position(footballTableElement.getPosition())
                            .playedGames(footballTableElement.getPlayedGames())
                            .won(footballTableElement.getWon())
                            .lost(footballTableElement.getLost())
                            .draw(footballTableElement.getDraw())
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
