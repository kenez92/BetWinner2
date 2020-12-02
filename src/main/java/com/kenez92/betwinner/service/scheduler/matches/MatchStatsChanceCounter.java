package com.kenez92.betwinner.service.scheduler.matches;

import com.kenez92.betwinner.persistence.entity.matches.MatchStats;
import org.springframework.stereotype.Service;

@Service
public class MatchStatsChanceCounter {

    public void process(final MatchStats matchStats) {
        setChanceBasedOnTheTable(matchStats);

        int totalMatches = matchStats.getGamesPlayed();
        int resultHomeTeam = matchStats.getHomeTeamWins() - matchStats.getAwayTeamWins() - matchStats.getDraws();
        int resultAwayTeam = matchStats.getAwayTeamWins() - matchStats.getHomeTeamWins() - matchStats.getDraws();

        if (resultHomeTeam > 0) {
            matchStats.setHomeTeamChance(matchStats.getHomeTeamChance() + 10);
            matchStats.setAwayTeamChance(matchStats.getAwayTeamChance() - 10);
        } else if (resultAwayTeam > 0) {
            matchStats.setHomeTeamChance(matchStats.getHomeTeamChance() - 10);
            matchStats.setAwayTeamChance(matchStats.getAwayTeamChance() + 10);
        }
        if (matchStats.getHomeTeamWins() > totalMatches / 2) {
            matchStats.setHomeTeamChance(matchStats.getHomeTeamChance() + 10);
            matchStats.setAwayTeamChance(matchStats.getAwayTeamChance() - 10);
        } else if (matchStats.getAwayTeamWins() > totalMatches / 2) {
            matchStats.setHomeTeamChance(matchStats.getHomeTeamChance() - 10);
            matchStats.setAwayTeamChance(matchStats.getAwayTeamChance() + 10);
        }
    }

    private void setChanceBasedOnTheTable(MatchStats matchStats) {
        Double homeTeamChance = matchStats.getHomeTeamChance();
        Double awayTeamChance = matchStats.getAwayTeamChance();
        int differenceInTable = Math.abs(matchStats.getHomeTeamPositionInTable() - matchStats.getAwayTeamPositionInTable());
        if (matchStats.getHomeTeamPositionInTable() < matchStats.getAwayTeamPositionInTable()) {
            setChances(homeTeamChance, awayTeamChance, differenceInTable);
        } else {
            setChances(awayTeamChance, homeTeamChance, differenceInTable);
        }
        matchStats.setHomeTeamChance(homeTeamChance);
        matchStats.setAwayTeamChance(awayTeamChance);
    }

    private void setChances(Double higherTeamInTable, Double LowerTeamInTable, int differenceInTable) {
        if (differenceInTable <= 3) {
            higherTeamInTable += 5;
            LowerTeamInTable -= 5;
        } else if (differenceInTable > 3 && differenceInTable < 8) {
            higherTeamInTable += 10;
            LowerTeamInTable -= 10;
        } else if (differenceInTable >= 8) {
            higherTeamInTable += 20;
            LowerTeamInTable -= 20;
        }
    }
}
