package com.kenez92.betwinner.sheduler.football.data.matches.count.cours;

import com.kenez92.betwinner.domain.matches.MatchDto;
import org.springframework.stereotype.Service;

@Service
public class CourseCounter {

    public void process(MatchDto matchDto) {
        double homeTeamChance = matchDto.getHomeTeamChance();
        double awayTeamChance = matchDto.getAwayTeamChance();
        double drawChance;
        if (homeTeamChance >= awayTeamChance) {
            drawChance = homeTeamChance - awayTeamChance;
        } else {
            drawChance = awayTeamChance - homeTeamChance;
        }
        matchDto.setHomeTeamCourse(countCourse(homeTeamChance));
        matchDto.setDrawCourse(countCourse(drawChance));
        matchDto.setAwayTeamCourse(countCourse(awayTeamChance));


    }

    private double countCourse(double chance) {
        double result = (100.0 - chance) * 0.01 + 1;
        return result;
    }
}
