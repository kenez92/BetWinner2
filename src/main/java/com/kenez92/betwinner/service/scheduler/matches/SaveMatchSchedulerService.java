package com.kenez92.betwinner.service.scheduler.matches;

import com.kenez92.betwinner.domain.fotballdata.match.FootballMatchById;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.persistence.entity.matches.Match;
import com.kenez92.betwinner.persistence.entity.matches.MatchDay;
import com.kenez92.betwinner.persistence.entity.matches.MatchScore;
import com.kenez92.betwinner.persistence.entity.matches.MatchStats;
import com.kenez92.betwinner.persistence.entity.weather.Weather;
import com.kenez92.betwinner.persistence.repository.matches.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SaveMatchSchedulerService {
    private final MatchRepository matchRepository;

    public Match process(MatchDay matchDay, MatchScore matchScore, MatchStats matchStats,
                         Weather weather, FootballMatchById footballMatchById) {
        Match match = Match.builder()
                .footballId(footballMatchById.getMatch().getId())
                .homeTeam(footballMatchById.getHead2head().getHomeTeam().getName())
                .awayTeam(footballMatchById.getHead2head().getAwayTeam().getName())
                .competitionId(footballMatchById.getMatch().getCompetition().getId())
                .seasonId(footballMatchById.getMatch().getSeason().getId())
                .date(footballMatchById.getMatch().getUtcDate())
                .round(footballMatchById.getMatch().getMatchDay())
                .matchDay(matchDay)
                .matchScore(matchScore)
                .matchStats(matchStats)
                .weather(weather)
                .build();

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
}
