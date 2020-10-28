package com.kenez92.betwinner.domain.fotballdata;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AvailableCompetitions {
    public static final List<Long> availableCompetitionList = new ArrayList<>(List.of(
//            2002L, // GERMANY - BUNDESLIGA
//            2003L, // NETHERLAND - EREDIVISIE
//            2014L, // SPAIN - LA_LIGA
//            2017L, // PORTUGAL - PRIMEIRA_LIGA
//            2021L, // ENGLAND - PREMIER_LEAGUE
            2015L // FRANCE - LIGUE 1
    ));
}
