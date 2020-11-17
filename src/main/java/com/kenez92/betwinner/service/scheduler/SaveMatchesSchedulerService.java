package com.kenez92.betwinner.service.scheduler;

import com.kenez92.betwinner.common.enums.FootballMatchStatusEnum;
import com.kenez92.betwinner.config.AvailableCompetitions;
import com.kenez92.betwinner.domain.fotballdata.matches.FootballMatch;
import com.kenez92.betwinner.domain.fotballdata.matches.FootballMatchList;
import com.kenez92.betwinner.football.client.FootballClient;
import com.kenez92.betwinner.service.matches.MatchService;
import com.kenez92.betwinner.service.table.CurrentMatchDayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SaveMatchesSchedulerService {
    private final FootballClient footballClient;
    private final AvailableCompetitions availableCompetitions;
    private final CurrentMatchDayService currentMatchDayService;
    private final MatchService matchService;


    public void saveMatches(int delay) {
        for (Long competitionId : availableCompetitions.getAvailableCompetitionList()) {
            List<Long> footballMatchIdsToUpdate = new ArrayList<>();
            Integer currentMatchDay = currentMatchDayService.getActualCurrentMatchDayNumber(competitionId);
            FootballMatchList footballMatchList = footballClient.getMatchesInRound(currentMatchDay, competitionId);
            for (FootballMatch footballMatch : footballMatchList.getMatches()) {
                if (matchService.isNotEqualOrNotExists(footballMatch)) {
                    footballMatchIdsToUpdate.add(footballMatch.getId());
                }
            }
            for (Long id : footballMatchIdsToUpdate) {
                System.out.println(id);
            }
        }
    }
}

//        public void saveMatch (FootballMatch footballMatch){
//            MatchDto matchDto = MatchDto.builder()
//                    .footballId(footballMatch.getId())
//                    .homeTeam(footballMatchById.getHead2head().getHomeTeam().getName())
//                    .awayTeam(footballMatchById.getHead2head().getAwayTeam().getName())
//                    .competitionId(footballMatchById.getMatch().getCompetition().getId())
//                    .seasonId(footballMatchById.getMatch().getSeason().getId())
//                    .date(footballMatchById.getMatch().getUtcDate())
//                    .homeTeamPositionInTable(0)
//                    .awayTeamPositionInTable(0)
//                    .homeTeamChance(0.0)
//                    .awayTeamChance(0.0)
//                    .round(footballMatchById.getMatch().getMatchDay())
//                    .homeTeamCourse(0.0)
//                    .drawCourse(0.0)
//                    .awayTeamCourse(0.0)
//                    .matchDay(matchDayDto)
//                    .weather(weatherDto)
//                    .build();
//            countChance.countChance(footballMatchById, matchDto);
//            courseCounter.process(matchDto);
//            MatchScoreDto matchScoreDto = saveMatchScore.saveMatchScore(footballMatchById.getMatch().getScore(), matchDto.getFootballId());
//            matchDto.setMatchScore(matchScoreDto);
//            if (matchService.existsByFields(matchDto.getHomeTeam(), matchDto.getAwayTeam(), matchDto.getRound())) {
//                MatchDto tmpMatchDto = matchService.findByFields(matchDto.getHomeTeam(), matchDto.getAwayTeam(),
//                        matchDto.getRound());
//                log.info("Match already exists: {}", tmpMatchDto);
//                if (matchDto.equals(tmpMatchDto)) {
//                    matchDayDto.setId(tmpMatchDto.getId());
//                    MatchDto updatedMatchDto = matchService.saveMatch(matchDto);
//                    log.info("Updating match: {}", updatedMatchDto);
//                }
//            } else {
//                MatchDto savedMatchDto = matchService.saveMatch(matchDto);
//                log.info("Saved match: {}", savedMatchDto);
//            }
//        }
//    }
//}
