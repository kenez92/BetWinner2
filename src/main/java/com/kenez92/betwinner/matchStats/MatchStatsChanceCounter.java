package com.kenez92.betwinner.matchStats;

import com.kenez92.betwinner.common.utilities.Utils;
import org.springframework.stereotype.Service;

@Service
public class MatchStatsChanceCounter {
    private static final int DECIMAL_VALUE_PLACE = 2;
    private static final double MIN_TEAM_CHANCE = 5.00;
    private static final double MAX_CHANCE = 100.00;
    public static final double MAX_DRAW_CHANCE = 90.00;

    public void process(final MatchStats matchStats) {
        setH2HChances(matchStats);
        setChanceBasedOnTheTable(matchStats);
        setDrawChance(matchStats);
        validateChances(matchStats);
    }

    private void setH2HChances(MatchStats matchStats) {
        int totalMatches = matchStats.getGamesPlayed();
        int resultHomeTeam = matchStats.getHomeTeamWins() - matchStats.getAwayTeamWins() - matchStats.getDraws();
        int resultAwayTeam = matchStats.getAwayTeamWins() - matchStats.getHomeTeamWins() - matchStats.getDraws();

        if (resultHomeTeam > 0) {
            matchStats.setHomeTeamChanceH2H(matchStats.getHomeTeamChanceH2H() + 10);
            matchStats.setAwayTeamChanceH2H(matchStats.getAwayTeamChanceH2H() - 10);
        } else if (resultAwayTeam > 0) {
            matchStats.setHomeTeamChanceH2H(matchStats.getHomeTeamChanceH2H() - 10);
            matchStats.setAwayTeamChanceH2H(matchStats.getAwayTeamChanceH2H() + 10);
        }
        if (matchStats.getHomeTeamWins() > totalMatches / 2) {
            matchStats.setHomeTeamChanceH2H(matchStats.getHomeTeamChanceH2H() + 10);
            matchStats.setAwayTeamChanceH2H(matchStats.getAwayTeamChanceH2H() - 10);
        } else if (matchStats.getAwayTeamWins() > totalMatches / 2) {
            matchStats.setHomeTeamChanceH2H(matchStats.getHomeTeamChanceH2H() - 10);
            matchStats.setAwayTeamChanceH2H(matchStats.getAwayTeamChanceH2H() + 10);
        }
        matchStats.setHomeTeamChance(matchStats.getHomeTeamChanceH2H());
        matchStats.setAwayTeamChance(matchStats.getAwayTeamChanceH2H());
    }

    private void setChanceBasedOnTheTable(MatchStats matchStats) {
        double homeTeamChance = matchStats.getHomeTeamChance();
        double awayTeamChance = matchStats.getAwayTeamChance();
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

    private void setDrawChance(MatchStats matchStats) {
        final double draws = matchStats.getDraws();
        final double gamesPlayed = matchStats.getGamesPlayed();
        if (matchStats.getGamesPlayed() != null && matchStats.getDraws() != null) {
            double result = 0.0;
            if (gamesPlayed != 0.0) {
                result = draws / gamesPlayed * 100;
            }
            matchStats.setHomeTeamChance(Utils.round(matchStats.getHomeTeamChance() - (result / 2), DECIMAL_VALUE_PLACE));
            matchStats.setAwayTeamChance(Utils.round(matchStats.getAwayTeamChance() - (result / 2), DECIMAL_VALUE_PLACE));
            matchStats.setDrawChance(Utils.round(result, DECIMAL_VALUE_PLACE));
        }
    }

    private void validateChances(MatchStats matchStats) {
        double homeTeamChance = matchStats.getHomeTeamChance();
        double awayTeamChance = matchStats.getAwayTeamChance();
        double drawChance = matchStats.getDrawChance();
        if (drawChance > MAX_DRAW_CHANCE) {
            drawChance = MAX_DRAW_CHANCE;
            matchStats.setDrawChance(MAX_DRAW_CHANCE);
        }
        if (homeTeamChance < MIN_TEAM_CHANCE || awayTeamChance < MIN_TEAM_CHANCE) {
            if (homeTeamChance < MIN_TEAM_CHANCE) {
                matchStats.setHomeTeamChance(MIN_TEAM_CHANCE);
                matchStats.setAwayTeamChance(MAX_CHANCE - MIN_TEAM_CHANCE - drawChance);
            } else if (awayTeamChance < MIN_TEAM_CHANCE) {
                matchStats.setHomeTeamChance(MAX_CHANCE - MIN_TEAM_CHANCE - drawChance);
                matchStats.setAwayTeamChance(MIN_TEAM_CHANCE);
            }
        }
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
