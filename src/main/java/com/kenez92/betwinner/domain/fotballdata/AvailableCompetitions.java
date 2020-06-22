package com.kenez92.betwinner.domain.fotballdata;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class AvailableCompetitions {
    private List<Long> availableCompetitionList = new ArrayList<>();

    public AvailableCompetitions() {
        availableCompetitionList.add(2002L); // GERMANY - BUNDESLIGA
        availableCompetitionList.add(2003L); // NETHERLAND - EREDIVISIE
        availableCompetitionList.add(2014L); // SPAIN - PRIMEIRA_LIGA
        availableCompetitionList.add(2017L); // PORTUGAL - PRIMEIRA_LIGA
        availableCompetitionList.add(2021L); // ENGLAND - PREMIER_LEAGUE
    }
}
