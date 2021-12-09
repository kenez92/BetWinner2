package com.kenez92.betwinner.matchStats;

import com.kenez92.betwinner.api.footballData.dto.match.FootballHead2Head;
import com.kenez92.betwinner.api.footballData.dto.match.FootballMatchById;
import com.kenez92.betwinner.competitionTable.CompetitionTable;
import com.kenez92.betwinner.competitionTableElement.CompetitionTableElement;
import com.kenez92.betwinner.exception.BetWinnerException;
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
                .homeTeamChance(50.00d)
                .drawChance(0.00d)
                .awayTeamChance(50.00d)
                .homeTeamChanceH2H(50.00d)
                .awayTeamChanceH2H(50.00d)
                .homeTeamCourse(0.00d)
                .drawCourse(0.00d)
                .awayTeamCourse(0.00d)
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
            return matchStats;
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
