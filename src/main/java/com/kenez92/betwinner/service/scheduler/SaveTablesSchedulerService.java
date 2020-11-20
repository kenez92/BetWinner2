package com.kenez92.betwinner.service.scheduler;

import com.kenez92.betwinner.api.client.FootballClient;
import com.kenez92.betwinner.config.AvailableCompetitions;
import com.kenez92.betwinner.domain.fotballdata.matches.FootballSeason;
import com.kenez92.betwinner.domain.fotballdata.standings.FootballStandings;
import com.kenez92.betwinner.domain.fotballdata.standings.FootballTable;
import com.kenez92.betwinner.domain.fotballdata.standings.FootballTableElement;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.persistence.entity.table.*;
import com.kenez92.betwinner.persistence.repository.table.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class SaveTablesSchedulerService {
    private final AvailableCompetitions availableCompetitions;
    private final FootballClient footballClient;
    private final CompetitionRepository competitionRepository;
    private final CompetitionSeasonRepository competitionSeasonRepository;
    private final CurrentMatchDayRepository currentMatchDayRepository;
    private final CompetitionTableRepository competitionTableRepository;
    private final CompetitionTableElementRepository competitionTableElementRepository;

    public void saveTables(int delayUsingFootballDataApi) throws InterruptedException {
        for (Long competitionId : availableCompetitions.getAvailableCompetitionList()) {
            TimeUnit.SECONDS.sleep(delayUsingFootballDataApi);
            FootballTable footballTable = footballClient.getTable(competitionId);
            Competition competition = saveCompetition(footballTable);
            CompetitionSeason competitionSeason = saveCompetitionSeason(competition, footballTable.getSeason());
            CurrentMatchDay currentMatchDay = saveCurrentMatchDay(competitionSeason, footballTable.getSeason().getCurrentMatchday());
            for (int i = 0; i < footballTable.getFootballStandings().length; i++) {
                CompetitionTable competitionTable = saveCompetitionTable(
                        currentMatchDay, footballTable.getFootballStandings()[i]);
                saveCompetitionTableElements(competitionTable, footballTable.getFootballStandings()[i].getFootballTableElement());

            }
        }
    }

    private Competition saveCompetition(FootballTable footballTable) {
        Long competitionFootballId = footballTable.getFootballCompetition().getId();
        if (competitionRepository.existsByFootballId(competitionFootballId)) {
            Competition competitionFromDb = competitionRepository.findByFootballId(competitionFootballId)
                    .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_COMPETITION_NOT_FOUND_EXCEPTION));
            if (competitionFromDb.getLastSavedRound().equals(footballTable.getSeason().getCurrentMatchday())) {
                return competitionFromDb;
            } else {
                competitionFromDb.setLastSavedRound(footballTable.getSeason().getCurrentMatchday());
                return competitionRepository.save(competitionFromDb);
            }
        } else {
            Competition competition = Competition.builder()
                    .footballId(footballTable.getFootballCompetition().getId())
                    .name(footballTable.getFootballCompetition().getName())
                    .lastSavedRound(footballTable.getSeason().getCurrentMatchday())
                    .build();
            return competitionRepository.save(competition);
        }
    }

    private CompetitionSeason saveCompetitionSeason(Competition competition, FootballSeason footballSeason) {
        String winner = footballSeason.getWinner() != null ? footballSeason.getWinner().getName() : "Season still in progress";
        CompetitionSeason competitionSeason = CompetitionSeason.builder()
                .footballId(footballSeason.getId())
                .startDate(footballSeason.getStartDate())
                .endDate(footballSeason.getEndDate())
                .winner(winner)
                .competition(competition)
                .build();

        if (competitionSeasonRepository.existsByFootballId(footballSeason.getId())) {
            CompetitionSeason competitionSeasonFromDb = competitionSeasonRepository.findByFootballId(footballSeason.getId())
                    .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_COMPETITION_SEASON_NOT_FOUND_EXCEPTION));
            competitionSeasonFromDb.setCompetition(competition);
            if (competitionSeasonFromDb.equals(competitionSeason)) {
                return competitionSeasonFromDb;
            } else {
                if (!competitionSeasonFromDb.getStartDate().equals(competitionSeason.getStartDate())) {
                    competitionSeasonFromDb.setStartDate(competitionSeason.getStartDate());
                }
                if (!competitionSeasonFromDb.getEndDate().equals(competitionSeason.getEndDate())) {
                    competitionSeasonFromDb.setEndDate(competitionSeason.getEndDate());
                }
                if (!competitionSeasonFromDb.getWinner().equals(competitionSeason.getWinner())) {
                    competitionSeasonFromDb.setWinner(competitionSeason.getWinner());
                }
                return competitionSeasonRepository.save(competitionSeasonFromDb);
            }
        } else {
            return competitionSeasonRepository.save(competitionSeason);
        }
    }

    private CurrentMatchDay saveCurrentMatchDay(CompetitionSeason competitionSeason, Integer currentMatchDayNumber) {
        CurrentMatchDay currentMatchDay = CurrentMatchDay.builder()
                .matchDay(currentMatchDayNumber)
                .competitionSeason(competitionSeason)
                .build();
        if (currentMatchDayRepository.existsByCompetitionSeasonAndMatchDay(competitionSeason, currentMatchDayNumber)) {
            CurrentMatchDay currentMatchDayFromDb = currentMatchDayRepository.findByCompetitionSeasonAndMatchDay(
                    competitionSeason, currentMatchDayNumber).orElseThrow(()
                    -> new BetWinnerException(BetWinnerException.ERR_CURRENT_MATCH_DAY_NOT_FOUND_EXCEPTION));
            currentMatchDayFromDb.setCompetitionSeason(competitionSeason);
            if (!currentMatchDayFromDb.equals(currentMatchDay)) {
                if (!currentMatchDayFromDb.getMatchDay().equals(currentMatchDay.getMatchDay())) {
                    currentMatchDayFromDb.setMatchDay(currentMatchDay.getMatchDay());
                }
                if (!currentMatchDayFromDb.getCompetitionSeason().equals(currentMatchDay.getCompetitionSeason())) {
                    currentMatchDayFromDb.setCompetitionSeason(currentMatchDay.getCompetitionSeason());
                }
                return currentMatchDayRepository.save(currentMatchDayFromDb);
            }
            return currentMatchDayFromDb;
        } else {
            return currentMatchDayRepository.save(currentMatchDay);
        }
    }

    private CompetitionTable saveCompetitionTable(CurrentMatchDay currentMatchDay, FootballStandings footballStanding) {
        CompetitionTable competitionTable = CompetitionTable.builder()
                .stage(footballStanding.getStage())
                .type(footballStanding.getType())
                .currentMatchDay(currentMatchDay)
                .build();
        if (competitionTableRepository.existsByStageAndTypeAndCurrentMatchDay(competitionTable.getStage(),
                competitionTable.getType(), currentMatchDay)) {
            CompetitionTable competitionTableFromDb = competitionTableRepository.findByStageAndTypeAndCurrentMatchDay(
                    competitionTable.getStage(), competitionTable.getType(), currentMatchDay).orElseThrow(()
                    -> new BetWinnerException(BetWinnerException.ERR_COMPETITION_TABLE_NOT_FOUND_EXCEPTION));
            if (!competitionTableFromDb.equals(competitionTable)) {
                if (!competitionTableFromDb.getStage().equals(competitionTable.getStage())) {
                    competitionTableFromDb.setStage(competitionTable.getStage());
                }
                if (!competitionTableFromDb.getType().equals(competitionTable.getType())) {
                    competitionTableFromDb.setType(competitionTable.getType());
                }
                if (!competitionTableFromDb.getCurrentMatchDay().equals(competitionTable.getCurrentMatchDay())) {
                    competitionTableFromDb.setCurrentMatchDay(competitionTable.getCurrentMatchDay());
                }
                return competitionTableRepository.save(competitionTableFromDb);
            }
            return competitionTableFromDb;
        } else {
            return competitionTableRepository.save(competitionTable);
        }
    }

    private void saveCompetitionTableElements(CompetitionTable competitionTable, FootballTableElement[] footballTableElements) {
        for (FootballTableElement footballTableElement : footballTableElements) {
            String name = footballTableElement.getTeam() != null ? footballTableElement.getTeam().getName() : "Unknown";
            CompetitionTableElement competitionTableElement = CompetitionTableElement.builder()
                    .competitionTable(competitionTable)
                    .name(name)
                    .position(footballTableElement.getPosition())
                    .playedGames(footballTableElement.getPlayedGames())
                    .won(footballTableElement.getWon())
                    .draw(footballTableElement.getDraw())
                    .lost(footballTableElement.getLost())
                    .points(footballTableElement.getPoints())
                    .goalsFor(footballTableElement.getGoalsScored())
                    .goalsAgainst(footballTableElement.getGoalsLost())
                    .goalDifference(footballTableElement.getGoalDifference())
                    .build();
            if (competitionTableElementRepository.existsByNameAndCompetitionTable(name, competitionTable)) {
                CompetitionTableElement competitionTableElementFromDb = competitionTableElementRepository
                        .findByNameAndCompetitionTable(name, competitionTable).orElseThrow(()
                                -> new BetWinnerException(BetWinnerException.ERR_COMPETITION_TABLE_ELEMENT_NOT_FOUND_EXCEPTION));
                if (!competitionTableElementFromDb.equals(competitionTableElement)) {
                    competitionTableElement.setId(competitionTableElementFromDb.getId());
                    competitionTableElementRepository.save(competitionTableElement);
                }

            } else {
                competitionTableElementRepository.save(competitionTableElement);
            }
        }
    }
}
