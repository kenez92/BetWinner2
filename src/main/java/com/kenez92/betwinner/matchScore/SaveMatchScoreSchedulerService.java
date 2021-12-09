package com.kenez92.betwinner.matchScore;

import com.kenez92.betwinner.api.footballData.dto.matches.FootballScore;
import com.kenez92.betwinner.exception.BetWinnerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SaveMatchScoreSchedulerService {
    private final MatchScoreRepository matchScoreRepository;

    public MatchScore process(FootballScore footballScore, Long footballMatchId) {
        MatchScore matchScore = MatchScore.builder()
                .footballMatchId(footballMatchId)
                .winner(footballScore.getWinner())
                .status(footballScore.getDuration())
                .duration(footballScore.getDuration())
                .fullTimeHomeTeam(footballScore.getFullTime().getHomeTeam())
                .fullTimeAwayTeam(footballScore.getFullTime().getAwayTeam())
                .halfTimeHomeTeam(footballScore.getHalfTime().getHomeTeam())
                .halfTimeAwayTeam(footballScore.getHalfTime().getAwayTeam())
                .extraTimeHomeTeam(footballScore.getExtraTime().getHomeTeam())
                .extraTimeAwayTeam(footballScore.getExtraTime().getAwayTeam())
                .penaltiesHomeTeam(footballScore.getPenalties().getHomeTeam())
                .penaltiesAwayTeam(footballScore.getPenalties().getAwayTeam())
                .build();
        if (matchScoreRepository.existsByFootballMatchId(footballMatchId)) {
            MatchScore matchScoreFromDb = matchScoreRepository.findByFootballMatchId(footballMatchId).orElseThrow(()
                    -> new BetWinnerException(BetWinnerException.ERR_MATCH_SCORE_NOT_FOUND_EXCEPTION));
            if (matchScoreFromDb.equals(matchScore)) {
                return matchScoreFromDb;
            } else {
                matchScore.setId(matchScoreFromDb.getId());
                return matchScoreRepository.save(matchScore);
            }
        } else {
            return matchScore;
        }
    }
}
