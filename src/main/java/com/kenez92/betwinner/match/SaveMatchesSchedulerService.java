package com.kenez92.betwinner.match;

import com.kenez92.betwinner.api.footballData.FootballClient;
import com.kenez92.betwinner.api.footballData.dto.match.FootballMatchById;
import com.kenez92.betwinner.api.footballData.dto.matches.FootballMatch;
import com.kenez92.betwinner.api.footballData.dto.matches.FootballMatchList;
import com.kenez92.betwinner.competitionTable.CompetitionTable;
import com.kenez92.betwinner.competitionTable.CompetitionTableRepository;
import com.kenez92.betwinner.competitionTableElement.CompetitionTableElementRepository;
import com.kenez92.betwinner.competitions.CompetitionDto;
import com.kenez92.betwinner.competitions.CompetitionService;
import com.kenez92.betwinner.config.AvailableCompetitions;
import com.kenez92.betwinner.currentMatchDay.CurrentMatchDay;
import com.kenez92.betwinner.currentMatchDay.CurrentMatchDayRepository;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.matchDay.MatchDay;
import com.kenez92.betwinner.matchDay.SaveMatchDaySchedulerService;
import com.kenez92.betwinner.matchScore.MatchScore;
import com.kenez92.betwinner.matchScore.SaveMatchScoreSchedulerService;
import com.kenez92.betwinner.matchStats.MatchStats;
import com.kenez92.betwinner.matchStats.SaveMatchStatsSchedulerService;
import com.kenez92.betwinner.weather.SaveWeatherSchedulerService;
import com.kenez92.betwinner.weather.Weather;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class SaveMatchesSchedulerService {
    private final AvailableCompetitions availableCompetitions;
    private final CompetitionService competitionService;
    private final FootballClient footballClient;
    private final SaveMatchDaySchedulerService saveMatchDaySchedulerService;
    private final SaveMatchScoreSchedulerService saveMatchScoreSchedulerService;
    private final SaveWeatherSchedulerService saveWeatherSchedulerService;
    private final SaveMatchStatsSchedulerService saveMatchStatsSchedulerService;
    private final SaveMatchSchedulerService saveMatchSchedulerService;
    private final CompetitionTableRepository competitionTableRepository;
    private final CompetitionTableElementRepository competitionTableElementRepository;
    private final CurrentMatchDayRepository currentMatchDayRepository;

    private final String COMPETITION_STAGE = "REGULAR_SEASON";
    private final String COMPETITION_TYPE = "TOTAL";


    public void saveMatches(int delayUsingFootballDataApi) {
        try {
            Set<Long> footballMatchListIds = getFootballMatchListIds(delayUsingFootballDataApi);
            for (Long footballMatchId : footballMatchListIds) {
                try {
                    TimeUnit.SECONDS.sleep(delayUsingFootballDataApi);
                    FootballMatchById footballMatchById = footballClient.getMatch(footballMatchId);

                    CompetitionTable competitionTable = getCompetitionTable(footballMatchById.getMatch().getMatchDay(),
                            footballMatchById.getMatch().getSeason().getId());

                    MatchDay matchDay = saveMatchDaySchedulerService.process(footballMatchById.getMatch().getUtcDate());

                    MatchScore matchScore = saveMatchScoreSchedulerService.process(footballMatchById.getMatch().getScore(),
                            footballMatchById.getMatch().getId());

                    MatchStats matchStats = saveMatchStatsSchedulerService.process(footballMatchById, footballMatchId, competitionTable);

                    Weather weather = saveWeatherSchedulerService.process(footballMatchById.getMatch().getUtcDate(),
                            footballMatchById.getMatch().getCompetition().getArea().getName());

                    saveMatchSchedulerService.process(matchDay, matchScore, matchStats, weather, footballMatchById);
                } catch (Exception ex) {
                    log.error("Error while saving match id: {}", footballMatchId);
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Set<Long> getFootballMatchListIds(int delayUsingFootballDataApi) throws InterruptedException {
        Set<Long> footballMatchSetIds = new HashSet<>();
        for (Long competitionId : availableCompetitions.getAvailableCompetitionList()) {
            CompetitionDto competitionDto = competitionService.getCompetitionByFootballId(competitionId);
            Integer currentMatchDayNumber = competitionDto.getLastSavedRound();
            TimeUnit.SECONDS.sleep(delayUsingFootballDataApi);
            FootballMatchList footballMatchList = footballClient.getMatchesInRound(currentMatchDayNumber, competitionId);
            for (FootballMatch footballMatch : footballMatchList.getMatches()) {
                footballMatchSetIds.add(footballMatch.getId());
            }
        }
        TimeUnit.SECONDS.sleep(delayUsingFootballDataApi);
        FootballMatchList footballMatchList = footballClient.getMatches(LocalDate.now().minusDays(2), LocalDate.now().plusDays(8));
        for (FootballMatch footballMatch : footballMatchList.getMatches()) {
            if (availableCompetitions.getAvailableCompetitionList().contains(footballMatch.getCompetition().getId())) {
                footballMatchSetIds.add(footballMatch.getId());
            }
        }
        return footballMatchSetIds;
    }

    public CompetitionTable getCompetitionTable(Integer currentMatchDayNumber, Long footballCompetitionSeasonId) {
        CurrentMatchDay currentMatchDay = currentMatchDayRepository
                .findByCurrentMatchDayNumberAndCompetitionSeasonId(currentMatchDayNumber, footballCompetitionSeasonId).orElse(null);
        if (currentMatchDay == null) {
            List<CurrentMatchDay> currentMatchDays = currentMatchDayRepository.findByCompetitionSeason_FootballId(footballCompetitionSeasonId);
            currentMatchDay = currentMatchDays.stream()
                    .max(Comparator.comparing(CurrentMatchDay::getMatchDay))
                    .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_CURRENT_MATCH_DAY_NOT_FOUND_EXCEPTION));
        }
        CompetitionTable competitionTable = competitionTableRepository
                .findByStageAndTypeAndCurrentMatchDay(COMPETITION_STAGE, COMPETITION_TYPE, currentMatchDay).orElseThrow(()
                        -> new BetWinnerException(BetWinnerException.ERR_COMPETITION_TABLE_NOT_FOUND_EXCEPTION));
        competitionTable.setCompetitionTableElements(competitionTableElementRepository.findByCompetitionTable(competitionTable));

        return competitionTable;
    }
}
