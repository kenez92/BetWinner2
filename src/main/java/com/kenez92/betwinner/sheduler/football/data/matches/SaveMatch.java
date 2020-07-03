package com.kenez92.betwinner.sheduler.football.data.matches;

import com.kenez92.betwinner.domain.fotballdata.match.FootballMatchById;
import com.kenez92.betwinner.domain.matches.MatchDayDto;
import com.kenez92.betwinner.domain.matches.MatchDto;
import com.kenez92.betwinner.domain.matches.MatchScoreDto;
import com.kenez92.betwinner.domain.matches.WeatherDto;
import com.kenez92.betwinner.logic.CountChance;
import com.kenez92.betwinner.service.matches.MatchService;
import com.kenez92.betwinner.sheduler.openweathermap.SaveWeather;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SaveMatch {
    private final MatchService matchService;
    private final CountChance countChance;
    private final SaveMatchScore saveMatchScore;
    private final SaveWeather saveWeather;


    public void saveMatch(FootballMatchById footballMatchById, MatchDayDto matchDayDto, WeatherDto weatherDto) {
        MatchDto matchDto = MatchDto.builder()
                .footballId(footballMatchById.getMatch().getId())
                .homeTeam(footballMatchById.getHead2head().getHomeTeam().getName())
                .awayTeam(footballMatchById.getHead2head().getAwayTeam().getName())
                .competitionId(footballMatchById.getMatch().getCompetition().getId())
                .seasonId(footballMatchById.getMatch().getSeason().getId())
                .date(footballMatchById.getMatch().getUtcDate())
                .homeTeamPositionInTable(0)
                .awayTeamPositionInTable(0)
                .homeTeamChance(0.0)
                .awayTeamChance(0.0)
                .round(footballMatchById.getMatch().getMatchDay())
                .matchDay(matchDayDto)
                .weather(weatherDto)
                .build();
        countChance.countChance(footballMatchById, matchDto);
        MatchScoreDto matchScoreDto = saveMatchScore.saveMatchScore(footballMatchById.getMatch().getScore(), matchDto.getFootballId());
        matchDto.setMatchScore(matchScoreDto);
        if (matchService.existsByFields(matchDto.getHomeTeam(), matchDto.getAwayTeam(), matchDto.getRound())) {
            MatchDto tmpMatchDto = matchService.findByFields(matchDto.getHomeTeam(), matchDto.getAwayTeam(),
                    matchDto.getRound());
            log.info("Match already exists: {}", tmpMatchDto);
            if (matchDto.equals(tmpMatchDto)) {
                matchDayDto.setId(tmpMatchDto.getId());
                MatchDto updatedMatchDto = matchService.saveMatch(matchDto);
                log.info("Updating match: {}", updatedMatchDto);
            }
        } else {
            MatchDto savedMatchDto = matchService.saveMatch(matchDto);
            log.info("Saved match: {}", savedMatchDto);
        }
    }
}
