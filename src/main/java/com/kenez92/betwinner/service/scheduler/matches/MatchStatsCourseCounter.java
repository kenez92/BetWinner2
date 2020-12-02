package com.kenez92.betwinner.service.scheduler.matches;

import com.kenez92.betwinner.persistence.entity.matches.MatchStats;
import org.springframework.stereotype.Service;

@Service
public class MatchStatsCourseCounter {
    public void process(MatchStats matchStats) {
        double homeTeamChance = matchStats.getHomeTeamChance();
        double awayTeamChance = matchStats.getAwayTeamChance();
        double drawChance;
        if (homeTeamChance >= awayTeamChance) {
            drawChance = homeTeamChance - awayTeamChance;
        } else {
            drawChance = awayTeamChance - homeTeamChance;
        }
        matchStats.setHomeTeamCourse(countCourse(homeTeamChance));
        matchStats.setDrawCourse(countCourse(drawChance));
        matchStats.setAwayTeamCourse(countCourse(awayTeamChance));
    }

    private double countCourse(double chance) {
        double result = (100.0 - chance) * 0.01 + 1;
        return result;
    }
}
