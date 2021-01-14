package com.kenez92.betwinner.config;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class AvailableCompetitions {
    private final List<Long> availableCompetitionList = new ArrayList<>();

    public AvailableCompetitions() {
        availableCompetitionList.add(2002L);          // GERMANY - BUNDESLIGA
        availableCompetitionList.add(2003L);          // NETHERLAND - EREDIVISIE
        availableCompetitionList.add(2014L);          // SPAIN - LA_LIGA
        availableCompetitionList.add(2017L);          // PORTUGAL - PRIMEIRA_LIGA
        availableCompetitionList.add(2021L);          // ENGLAND - PREMIER_LEAGUE
        availableCompetitionList.add(2015L);          // FRANCE - LIGUE 1
    }

}
