package com.kenez92.betwinner.service.scheduler.matches;

import com.kenez92.betwinner.persistence.entity.matches.MatchStats;
import org.springframework.stereotype.Service;

@Service
public class MatchStatsChanceCounter {

    public void process(final MatchStats matchStats) {
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
        matchStats.setHomeTeamChanceH2H(matchStats.getHomeTeamChance());
        matchStats.setAwayTeamChanceH2H(matchStats.getAwayTeamChance());
        setChanceBasedOnTheTable(matchStats);
    }

    private void setChanceBasedOnTheTable(MatchStats matchStats) {
        Double homeTeamChance = matchStats.getHomeTeamChance();
        Double awayTeamChance = matchStats.getAwayTeamChance();
        int differenceInTable = Math.abs(matchStats.getHomeTeamPositionInTable() - matchStats.getAwayTeamPositionInTable());
        int chancesBasedOnDifferenceInTable = getChances(differenceInTable);
        if (matchStats.getHomeTeamPositionInTable() < matchStats.getAwayTeamPositionInTable()) {
            homeTeamChance += chancesBasedOnDifferenceInTable;
            awayTeamChance -= chancesBasedOnDifferenceInTable;
        } else {
            homeTeamChance -= chancesBasedOnDifferenceInTable;
            awayTeamChance += chancesBasedOnDifferenceInTable;
        }
        matchStats.setHomeTeamChance(homeTeamChance);
        matchStats.setAwayTeamChance(awayTeamChance);
    }

    private int getChances(int differenceInTable) {
        if (differenceInTable == 1) {
            return 1;
        } else if (differenceInTable <= 3) {
            return 5;
        } else if (differenceInTable < 8) {
            return 10;
        } else if (differenceInTable >= 8) {
            return 20;
        } else {
            return 0;
        }
    }
}
