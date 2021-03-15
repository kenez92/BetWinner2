package com.kenez92.betwinner.service.scheduler.matches;

import com.kenez92.betwinner.common.enums.MatchType;
import com.kenez92.betwinner.common.enums.ResultType;
import com.kenez92.betwinner.config.StrategyConfiguration;
import com.kenez92.betwinner.domain.fotballdata.match.FootballMatchById;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.persistence.entity.matches.Match;
import com.kenez92.betwinner.persistence.entity.matches.MatchDay;
import com.kenez92.betwinner.persistence.entity.matches.MatchScore;
import com.kenez92.betwinner.persistence.entity.matches.MatchStats;
import com.kenez92.betwinner.persistence.entity.weather.Weather;
import com.kenez92.betwinner.persistence.repository.matches.MatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SaveMatchSchedulerService {
    private final MatchRepository matchRepository;

    public Match process(MatchDay matchDay, MatchScore matchScore, MatchStats matchStats,
                         Weather weather, FootballMatchById footballMatchById) {
        Match match;
        try {
            match = Match.builder()
                    .footballId(footballMatchById.getMatch().getId())
                    .homeTeam(footballMatchById.getMatch().getHomeTeam().getName())
                    .awayTeam(footballMatchById.getMatch().getAwayTeam().getName())
                    .competitionId(footballMatchById.getMatch().getCompetition().getId())
                    .seasonId(footballMatchById.getMatch().getSeason().getId())
                    .date(footballMatchById.getMatch().getUtcDate())
                    .round(footballMatchById.getMatch().getMatchDay())
                    .matchDay(matchDay)
                    .matchScore(matchScore)
                    .matchStats(matchStats)
                    .weather(weather)
                    .build();
        } catch (Exception ex) {
            log.error("Exception + " + ex + ", message: " + ex.getMessage());
            throw new BetWinnerException(BetWinnerException.ERR_SOMETHING_WENT_WRONG_EXCEPTION);
        }
        if (matchScore.getWinner() != null) {
            try {
                MatchType matchType = MatchType.valueOf(matchScore.getWinner());
                match.setNormalStrategyResultType(checkIfTypeWasGood(matchType, matchStats, StrategyConfiguration.NORMAL_STRATEGY_FROM));
                match.setDefensiveStrategyResultType(checkIfTypeWasGood(matchType, matchStats, StrategyConfiguration.DEFENSIVE_STRATEGY_FROM));
                match.setAggressiveStrategyResultType(checkIfTypeWasGood(matchType, matchStats, StrategyConfiguration.AGGRESSIVE_STRATEGY_FROM));
                match.setPercent70StrategyResultType(check70PercentType(matchType, matchStats));
            } catch (Exception ex) {
                log.error("Error while checking if type was good for match football id: {}", match.getFootballId());
            }
        }
        if (matchRepository.existsByFootballId(footballMatchById.getMatch().getId())) {
            Match matchFromDb = matchRepository.findByFootballId(footballMatchById.getMatch().getId()).orElseThrow(()
                    -> new BetWinnerException(BetWinnerException.ERR_MATCH_NOT_FOUND_EXCEPTION));
            if (!matchFromDb.equals(match)) {
                match.setId(matchFromDb.getId());
                return matchRepository.save(match);
            }
            return matchFromDb;
        }
        return matchRepository.save(match);
    }

    private ResultType checkIfTypeWasGood(MatchType matchType, MatchStats matchStats, double typeFrom) {
        switch (matchType) {
            case DRAW:
                if (matchStats.getDrawChance() >= typeFrom) {
                    return ResultType.WIN;
                } else if (matchStats.getDrawChance() < typeFrom) {
                    return ResultType.NOT_BET;
                } else {
                    return ResultType.LOST;
                }
            case HOME_TEAM:
                if (matchStats.getHomeTeamChance() >= typeFrom) {
                    return ResultType.WIN;
                } else if (matchStats.getHomeTeamChance() < typeFrom) {
                    return ResultType.NOT_BET;
                } else {
                    return ResultType.LOST;
                }
            case AWAY_TEAM:
                if (matchStats.getAwayTeamChance() >= typeFrom) {
                    return ResultType.WIN;
                } else if (matchStats.getAwayTeamChance() < typeFrom) {
                    return ResultType.NOT_BET;
                } else {
                    return ResultType.LOST;
                }
        }
        return null;
    }

    private ResultType check70PercentType(MatchType matchType, MatchStats matchStats) {
        switch (matchType) {
            case AWAY_TEAM:
                if (matchStats.getAwayTeamChanceH2H() >= StrategyConfiguration.PERCENT_70_STRATEGY_FROM) {
                    return ResultType.WIN;
                } else if (matchStats.getAwayTeamChanceH2H() < StrategyConfiguration.PERCENT_70_STRATEGY_FROM) {
                    return ResultType.NOT_BET;
                } else {
                    return ResultType.LOST;
                }
            case HOME_TEAM:
                if (matchStats.getHomeTeamChanceH2H() >= StrategyConfiguration.PERCENT_70_STRATEGY_FROM) {
                    return ResultType.WIN;
                } else if (matchStats.getHomeTeamChanceH2H() < StrategyConfiguration.PERCENT_70_STRATEGY_FROM) {
                    return ResultType.NOT_BET;
                } else {
                    return ResultType.LOST;
                }
            case DRAW:
                if (matchStats.getHomeTeamChanceH2H() >= StrategyConfiguration.PERCENT_70_STRATEGY_FROM ||
                        matchStats.getAwayTeamChanceH2H() >= StrategyConfiguration.PERCENT_70_STRATEGY_FROM) {
                    return ResultType.LOST;
                } else {
                    return ResultType.NOT_BET;
                }
        }
        return null;
    }
}
