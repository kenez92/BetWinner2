package com.kenez92.betwinner.service.scheduler.matches;

import com.kenez92.betwinner.common.utilities.Utils;
import com.kenez92.betwinner.persistence.entity.matches.MatchStats;
import org.springframework.stereotype.Service;

@Service
public class MatchStatsCourseCounter {
    private static final int DECIMAL_VALUE_PLACE = 2;
    private static final double COURSE_RATE = 3.55;
    private static final double MIN_COURSE = 1.1;
    private static final double MAX_CHANCE = 100.00;
    private final double DRAW_BONUS = 0.5;

    public void process(MatchStats matchStats) {
        double homeTeamChance = matchStats.getHomeTeamChance();
        double awayTeamChance = matchStats.getAwayTeamChance();
        double drawChance = matchStats.getDrawChance();
        matchStats.setHomeTeamCourse(countCourse(homeTeamChance));
        matchStats.setDrawCourse(countCourse(drawChance) + DRAW_BONUS);
        matchStats.setAwayTeamCourse(countCourse(awayTeamChance));
    }

    private double countCourse(double chance) {
        double result = Utils.round(COURSE_RATE * (MAX_CHANCE - chance) * 0.01, DECIMAL_VALUE_PLACE);
        return Math.max(result, MIN_COURSE);
    }
}
