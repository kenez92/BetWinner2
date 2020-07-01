package com.kenez92.betwinner.logic;

import com.kenez92.betwinner.domain.fotballdata.FootballTeam;
import com.kenez92.betwinner.domain.fotballdata.match.FootballMatchById;
import com.kenez92.betwinner.domain.matches.MatchDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Getter
@RequiredArgsConstructor
public class CountChance {
    private final SetPositionTeam setPositionTeam;
    private final SetChanceBasedOnTheTable setChanceBasedOnTheTable;

    public void countChance(FootballMatchById footballMatch, MatchDto matchDto) {
        setPositionTeam.setPosition(matchDto);
        if (footballMatch.getMatch().getScore().getWinner() != null) {
            throw new BetWinnerException(BetWinnerException.ERR_FOOTBALL_MATCH_IS_FINISHED);
        }

        FootballTeam homeTeam = footballMatch.getHead2head().getHomeTeam();
        FootballTeam awayTeam = footballMatch.getHead2head().getAwayTeam();

        setChanceBasedOnTheTable.setChanceBasedOnTheTable(homeTeam, awayTeam);

        int totalMatches = footballMatch.getHead2head().getNumberOfMatches();
        int resultHomeTeam = homeTeam.getWins() - homeTeam.getLosses() - homeTeam.getDraws();
        int resultAwayTeam = awayTeam.getWins() - awayTeam.getLosses() - awayTeam.getDraws();

        if (resultHomeTeam > 0) {
            homeTeam.setChanceToWin(homeTeam.getChanceToWin() + 10);
            awayTeam.setChanceToWin(awayTeam.getChanceToWin() - 10);
        }
        if (resultAwayTeam < 0) {
            homeTeam.setChanceToWin(homeTeam.getChanceToWin() - 10);
            awayTeam.setChanceToWin(awayTeam.getChanceToWin() + 10);
        }
        if (homeTeam.getWins() > totalMatches / 2) {
            homeTeam.setChanceToWin(homeTeam.getChanceToWin() + 10);
            awayTeam.setChanceToWin(awayTeam.getChanceToWin() - 10);
        }
        if (awayTeam.getWins() > totalMatches / 2) {
            homeTeam.setChanceToWin(homeTeam.getChanceToWin() - 10);
            awayTeam.setChanceToWin(awayTeam.getChanceToWin() + 10);
        }
        matchDto.setHomeTeamChance(homeTeam.getChanceToWin());
        matchDto.setAwayTeamChance(awayTeam.getChanceToWin());
    }
}
