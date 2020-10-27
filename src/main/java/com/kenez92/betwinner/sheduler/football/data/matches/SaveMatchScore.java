package com.kenez92.betwinner.service.scheduler.football.data.matches;

import com.kenez92.betwinner.domain.fotballdata.matches.FootballScore;
import com.kenez92.betwinner.domain.matches.MatchScoreDto;
import com.kenez92.betwinner.service.matches.MatchScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SaveMatchScore {
    private final MatchScoreService matchScoreService;

    public MatchScoreDto saveMatchScore(final FootballScore footballScore, final Long id) {
        MatchScoreDto tmpMatchScoreDto = MatchScoreDto.builder()
                .matchId(id)
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
        MatchScoreDto matchScoreDto;
        if (matchScoreService.existByMatchId(tmpMatchScoreDto.getMatchId())) {
            matchScoreDto = matchScoreService.getByMatchId(tmpMatchScoreDto.getMatchId());
            log.info("Match score already exists: {}", matchScoreDto);
            if (matchScoreDto.getWinner() == null && tmpMatchScoreDto.getWinner() != null) {
                tmpMatchScoreDto.setId(matchScoreDto.getId());
                matchScoreDto = matchScoreService.saveMatchScore(tmpMatchScoreDto);
                log.info("Updating match score: {}", matchScoreDto);
            }
        } else {
            matchScoreDto = matchScoreService.saveMatchScore(tmpMatchScoreDto);
            log.info("Saved match score: {}", matchScoreDto);
        }
        return matchScoreDto;
    }
}
