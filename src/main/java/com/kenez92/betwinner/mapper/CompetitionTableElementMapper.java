package com.kenez92.betwinner.mapper;

import com.kenez92.betwinner.domain.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CompetitionTableElementMapper {

    public CompetitionTableElement mapToCompetitionTableElement(CompetitionTableElementDto competitionTableElementDto) {
        return CompetitionTableElement.builder()
                .id(competitionTableElementDto.getId())
                .competitionTable(mapToCompetitionTable(competitionTableElementDto.getCompetitionTable()))
                .name(competitionTableElementDto.getName())
                .position(competitionTableElementDto.getPosition())
                .playedGames(competitionTableElementDto.getPlayedGames())
                .won(competitionTableElementDto.getWon())
                .draw(competitionTableElementDto.getDraw())
                .lost(competitionTableElementDto.getLost())
                .points(competitionTableElementDto.getPoints())
                .goalsFor(competitionTableElementDto.getGoalsFor())
                .goalsAgainst(competitionTableElementDto.getGoalsAgainst())
                .goalDifference(competitionTableElementDto.getGoalDifference())
                .build();
    }

    public CompetitionTableElementDto mapToCompetitionTableElementDto(CompetitionTableElement competitionTableElement) {
        return CompetitionTableElementDto.builder()
                .id(competitionTableElement.getId())
                .competitionTable(mapToCompetitionTableDto(competitionTableElement.getCompetitionTable()))
                .name(competitionTableElement.getName())
                .position(competitionTableElement.getPosition())
                .playedGames(competitionTableElement.getPlayedGames())
                .won(competitionTableElement.getWon())
                .draw(competitionTableElement.getDraw())
                .lost(competitionTableElement.getLost())
                .points(competitionTableElement.getPoints())
                .goalsFor(competitionTableElement.getGoalsFor())
                .goalsAgainst(competitionTableElement.getGoalsAgainst())
                .goalDifference(competitionTableElement.getGoalDifference())
                .build();
    }

    private CompetitionTable mapToCompetitionTable(CompetitionTableDto competitionTableDto) {
        return CompetitionTable.builder()
                .id(competitionTableDto.getId())
                .stage(competitionTableDto.getStage())
                .type(competitionTableDto.getType())
                .currentMatchDay(CurrentMatchDay.builder()
                        .id(competitionTableDto.getCurrentMatchDay().getId())
                        .matchDay(competitionTableDto.getCurrentMatchDay().getMatchDay())
                        .competitionSeason(new CompetitionSeason())
                        .competitionTableList(new ArrayList<>())
                        .build())
                .competitionTableElements(new ArrayList<>())
                .build();
    }

    private CompetitionTableDto mapToCompetitionTableDto(CompetitionTable competitionTable) {
        return CompetitionTableDto.builder()
                .id(competitionTable.getId())
                .stage(competitionTable.getStage())
                .type(competitionTable.getType())
                .currentMatchDay(CurrentMatchDayDto.builder()
                        .id(competitionTable.getCurrentMatchDay().getId())
                        .matchDay(competitionTable.getCurrentMatchDay().getMatchDay())
                        .competitionSeason(new CompetitionSeasonDto())
                        .competitionTableList(new ArrayList<>())
                        .build())
                .competitionTableElements(new ArrayList<>())
                .build();
    }
}
