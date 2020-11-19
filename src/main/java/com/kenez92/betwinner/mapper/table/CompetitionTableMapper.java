package com.kenez92.betwinner.mapper.table;

import com.kenez92.betwinner.domain.table.*;
import com.kenez92.betwinner.persistence.entity.table.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompetitionTableMapper {
    public CompetitionTable mapToCompetitionTable(final CompetitionTableDto competitionTableDto) {
        return CompetitionTable.builder()
                .id(competitionTableDto.getId())
                .stage(competitionTableDto.getStage())
                .type(competitionTableDto.getType())
                .currentMatchDay(mapToCurrentMatchDay(competitionTableDto.getCurrentMatchDay()))
                .competitionTableElements(mapToCompetitionTableElements(competitionTableDto.getCompetitionTableElements()))
                .build();
    }

    public CompetitionTableDto mapToCompetitionTableDto(final CompetitionTable competitionTable) {
        return CompetitionTableDto.builder()
                .id(competitionTable.getId())
                .stage(competitionTable.getStage())
                .type(competitionTable.getType())
                .currentMatchDay(mapToCurrentMatchDayDto(competitionTable.getCurrentMatchDay()))
                .competitionTableElements(mapToCompetitionTableElementsDtos(competitionTable.getCompetitionTableElements()))
                .build();
    }

    public List<CompetitionTableDto> mapToCompetitionTableDtoList(final List<CompetitionTable> competitionTableList) {
        return new ArrayList<>(competitionTableList).stream()
                .map(this::mapToCompetitionTableDto)
                .collect(Collectors.toList());
    }

    private CurrentMatchDay mapToCurrentMatchDay(final CurrentMatchDayDto currentMatchDayDto) {
        return CurrentMatchDay.builder()
                .id(currentMatchDayDto.getId())
                .matchDay(currentMatchDayDto.getMatchDay())
                .competitionSeason(CompetitionSeason.builder()
                        .id(currentMatchDayDto.getCompetitionSeason().getId())
                        .footballId(currentMatchDayDto.getCompetitionSeason().getFootballId())
                        .startDate(currentMatchDayDto.getCompetitionSeason().getStartDate())
                        .endDate(currentMatchDayDto.getCompetitionSeason().getEndDate())
                        .winner(currentMatchDayDto.getCompetitionSeason().getWinner())
                        .competition(Competition.builder()
                                .id(currentMatchDayDto.getCompetitionSeason().getCompetition().getId())
                                .footballId(currentMatchDayDto.getCompetitionSeason().getCompetition().getFootballId())
                                .name(currentMatchDayDto.getCompetitionSeason().getCompetition().getName())
                                .competitionSeasonList(new ArrayList<>())
                                .build())
                        .currentMatchDayList(new ArrayList<>())
                        .build())
                .build();
    }

    private CurrentMatchDayDto mapToCurrentMatchDayDto(final CurrentMatchDay currentMatchDay) {
        return CurrentMatchDayDto.builder()
                .id(currentMatchDay.getId())
                .matchDay(currentMatchDay.getMatchDay())
                .competitionSeason(CompetitionSeasonDto.builder()
                        .id(currentMatchDay.getCompetitionSeason().getId())
                        .footballId(currentMatchDay.getCompetitionSeason().getFootballId())
                        .startDate(currentMatchDay.getCompetitionSeason().getStartDate())
                        .endDate(currentMatchDay.getCompetitionSeason().getEndDate())
                        .winner(currentMatchDay.getCompetitionSeason().getWinner())
                        .competition(CompetitionDto.builder()
                                .id(currentMatchDay.getCompetitionSeason().getCompetition().getId())
                                .footballId(currentMatchDay.getCompetitionSeason().getCompetition().getFootballId())
                                .name(currentMatchDay.getCompetitionSeason().getCompetition().getName())
                                .competitionSeasonList(new ArrayList<>())
                                .build())
                        .currentMatchDayList(new ArrayList<>())
                        .build())
                .build();
    }

    public List<CompetitionTableElement> mapToCompetitionTableElements(List<CompetitionTableElementDto> competitionTableElementDtoList) {
        List<CompetitionTableElement> competitionTableElements = new ArrayList<>();
        if (competitionTableElementDtoList != null) {
            for (CompetitionTableElementDto competitionTableElementDto : competitionTableElementDtoList) {
                competitionTableElements.add(CompetitionTableElement.builder()
                        .id(competitionTableElementDto.getId())
                        .competitionTable(CompetitionTable.builder()
                                .id(competitionTableElementDto.getCompetitionTable().getId())
                                .stage(competitionTableElementDto.getCompetitionTable().getStage())
                                .type(competitionTableElementDto.getCompetitionTable().getType())
                                .currentMatchDay(new CurrentMatchDay())
                                .competitionTableElements(new ArrayList<>())
                                .build())
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
                        .build());
            }
        }
        return competitionTableElements;
    }

    private List<CompetitionTableElementDto> mapToCompetitionTableElementsDtos(final List<CompetitionTableElement> competitionTableElements) {
        List<CompetitionTableElementDto> competitionTableElementDtos = new ArrayList<>();
        for (CompetitionTableElement competitionTableElement : competitionTableElements) {
            competitionTableElementDtos.add(CompetitionTableElementDto.builder()
                    .id(competitionTableElement.getId())
                    .competitionTable(CompetitionTableDto.builder()
                            .id(competitionTableElement.getCompetitionTable().getId())
                            .stage(competitionTableElement.getCompetitionTable().getStage())
                            .type(competitionTableElement.getCompetitionTable().getType())
                            .currentMatchDay(new CurrentMatchDayDto())
                            .competitionTableElements(new ArrayList<>())
                            .build())
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
                    .build());
        }
        return competitionTableElementDtos;
    }
}
