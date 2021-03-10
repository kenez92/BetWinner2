package com.kenez92.betwinner.service.scheduler.matches;

import com.kenez92.betwinner.domain.fotballdata.match.FootballHead2Head;
import com.kenez92.betwinner.domain.fotballdata.match.FootballMatchById;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.persistence.entity.matches.MatchStats;
import com.kenez92.betwinner.persistence.entity.table.CompetitionTable;
import com.kenez92.betwinner.persistence.entity.table.CompetitionTableElement;
import com.kenez92.betwinner.persistence.repository.matches.MatchStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SaveMatchStatsSchedulerService {
    private final MatchStatsRepository matchStatsRepository;
    private final MatchStatsCourseCounter matchStatsCourseCounter;
    private final MatchStatsChanceCounter chanceCounter;

    public MatchStats process(FootballMatchById footballMatchById, Long footballMatchId, CompetitionTable competitionTable) {
        FootballHead2Head footballHead2Head = footballMatchById.getHead2head();
        MatchStats matchStats = MatchStats.builder()
                .footballMatchId(footballMatchId)
                .homeTeamWins(footballHead2Head != null ? footballHead2Head.getHomeTeam().getWins() : 0)
                .draws(footballHead2Head != null ? footballHead2Head.getHomeTeam().getDraws() : 0)
                .awayTeamWins(footballHead2Head != null ? footballHead2Head.getAwayTeam().getWins() : 0)
                .gamesPlayed(footballHead2Head != null ? footballHead2Head.getNumberOfMatches() : 0)
                .homeTeamPositionInTable(getPositionInTable(competitionTable, footballMatchById.getMatch()
                        .getHomeTeam().getName()))
                .awayTeamPositionInTable(getPositionInTable(competitionTable, footballMatchById.getMatch()
                        .getAwayTeam().getName()))
                .homeTeamChance(50D)
                .awayTeamChance(50D)
                .homeTeamChanceH2H(50D)
                .awayTeamChanceH2H(50D)
                .homeTeamCourse(0D)
                .drawCourse(0D)
                .awayTeamCourse(0D)
                .build();
        chanceCounter.process(matchStats);
        matchStatsCourseCounter.process(matchStats);
        if (matchStatsRepository.existsByFootballMatchId(footballMatchId)) {
            MatchStats matchStatsFromDb = matchStatsRepository.findByFootballMatchId(footballMatchId).orElseThrow(()
                    -> new BetWinnerException(BetWinnerException.ERR_MATCH_STATS_NOT_FOUND_EXCEPTION));
            if (matchStatsFromDb.equals(matchStats)) {
                return matchStatsFromDb;
            } else {
                matchStats.setId(matchStatsFromDb.getId());
                return matchStatsRepository.save(matchStats);
            }
        } else {
            return matchStatsRepository.save(matchStats);
        }
    }

    public Integer getPositionInTable(CompetitionTable competitionTable, String name) {
        CompetitionTableElement competitionTableElement = competitionTable.getCompetitionTableElements().stream()
                .filter(e -> e.getName().equals(name))
                .findAny()
                .orElse(null);
        if (competitionTableElement != null) {
            return competitionTableElement.getPosition();
        } else {
            return 0;
        }
    }
}
