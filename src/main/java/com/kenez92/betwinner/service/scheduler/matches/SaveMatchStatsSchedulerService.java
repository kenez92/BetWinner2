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
                .homeTeamWins(footballHead2Head.getHomeTeam().getWins())
                .draws(footballHead2Head.getHomeTeam().getDraws())
                .awayTeamWins(footballHead2Head.getAwayTeam().getWins())
                .gamesPlayed(footballHead2Head.getNumberOfMatches())
                .homeTeamPositionInTable(getPositionInTable(competitionTable, footballMatchById.getMatch()
                        .getHomeTeam().getName()))
                .awayTeamPositionInTable(getPositionInTable(competitionTable, footballMatchById.getMatch()
                        .getAwayTeam().getName()))
                .homeTeamChance(50D)
                .awayTeamChance(50D)
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
                System.out.println("RETURN SAVED");
                return matchStatsFromDb;
            } else {
                System.out.println("UPDATED");
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

    public void setCourse(MatchStats matchStats) {

    }
}
