package com.kenez92.betwinner.sheduler.football.data.matches.count.chance;

import com.kenez92.betwinner.domain.fotballdata.FootballTeam;
import org.springframework.stereotype.Component;

@Component
public class SetChanceBasedOnTheTable {
    public void setChanceBasedOnTheTable(final FootballTeam homeTeam, final FootballTeam awayTeam) {
        FootballTeam higherInTableTeam;
        FootballTeam lowerInTableTeam;

        if (homeTeam.getPositionInTable() > awayTeam.getPositionInTable()) {
            higherInTableTeam = homeTeam;
            lowerInTableTeam = awayTeam;
        } else {
            higherInTableTeam = awayTeam;
            lowerInTableTeam = homeTeam;
        }
        int differenceInTable = higherInTableTeam.getPositionInTable() - lowerInTableTeam.getPositionInTable();

        if (differenceInTable <= 3) {
            higherInTableTeam.setChanceToWin(higherInTableTeam.getChanceToWin() + 5);
            lowerInTableTeam.setChanceToWin(lowerInTableTeam.getChanceToWin() - 5);
        } else if (differenceInTable > 3 && differenceInTable < 8) {
            higherInTableTeam.setChanceToWin(higherInTableTeam.getChanceToWin() + 10);
            lowerInTableTeam.setChanceToWin(lowerInTableTeam.getChanceToWin() - 10);
        } else if (differenceInTable >= 8) {
            higherInTableTeam.setChanceToWin(higherInTableTeam.getChanceToWin() + 20);
            lowerInTableTeam.setChanceToWin(lowerInTableTeam.getChanceToWin() - 20);
        }
    }
}
