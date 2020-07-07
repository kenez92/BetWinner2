package com.kenez92.betwinner.mapper.table;

import com.kenez92.betwinner.domain.table.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompetitionSeasonMapper {

    public CompetitionSeason mapToCompetitionSeason(final CompetitionSeasonDto competitionSeasonDto) {
        return CompetitionSeason.builder()
                .id(competitionSeasonDto.getId())
                .footballId(competitionSeasonDto.getFootballId())
                .startDate(competitionSeasonDto.getStartDate())
                .endDate(competitionSeasonDto.getEndDate())
                .winner(competitionSeasonDto.getWinner())
                .competition(mapToCompetition(competitionSeasonDto))
                .currentMatchDayList(currentMatchDayList(competitionSeasonDto))
                .build();
    }

    public CompetitionSeasonDto mapToCompetitionSeasonDto(final CompetitionSeason competitionSeason) {
        return CompetitionSeasonDto.builder()
                .id(competitionSeason.getId())
                .footballId(competitionSeason.getFootballId())
                .startDate(competitionSeason.getStartDate())
                .endDate(competitionSeason.getEndDate())
                .winner(competitionSeason.getWinner())
                .competition(mapToCompetitionDto(competitionSeason))
                .currentMatchDayList(currentMatchDayDtoList(competitionSeason))
                .build();
    }

    public List<CompetitionSeasonDto> mapToCompetitionSeasonDtoList(final List<CompetitionSeason> competitionSeasons) {
        return new ArrayList<>(competitionSeasons).stream()
                .map(this::mapToCompetitionSeasonDto)
                .collect(Collectors.toList());
    }

    private List<CurrentMatchDay> currentMatchDayList(final CompetitionSeasonDto competitionSeasonDto) {
        List<CurrentMatchDay> currentMatchDayList = new ArrayList<>();
        if (competitionSeasonDto.getCurrentMatchDayList() != null) {
            for (CurrentMatchDayDto currentMatchDayDto : competitionSeasonDto.getCurrentMatchDayList()) {
                currentMatchDayList.add(CurrentMatchDay.builder()
                        .id(currentMatchDayDto.getId())
                        .matchDay(currentMatchDayDto.getMatchDay())
                        .competitionSeason(CompetitionSeason.builder().id(competitionSeasonDto.getId())
                                .footballId(competitionSeasonDto.getFootballId())
                                .startDate(competitionSeasonDto.getStartDate())
                                .endDate(competitionSeasonDto.getEndDate())
                                .winner(competitionSeasonDto.getWinner())
                                .build())
                        .competitionTableList(new ArrayList<>())
                        .build());
            }
        }
        return currentMatchDayList;
    }

    private List<CurrentMatchDayDto> currentMatchDayDtoList(final CompetitionSeason competitionSeason) {
        List<CurrentMatchDayDto> currentMatchDayDtoList = new ArrayList<>();
        for (CurrentMatchDay currentMatchDay : competitionSeason.getCurrentMatchDayList()) {
            currentMatchDayDtoList.add(CurrentMatchDayDto.builder()
                    .id(currentMatchDay.getId())
                    .matchDay(currentMatchDay.getMatchDay())
                    .competitionSeason(CompetitionSeasonDto.builder()
                            .id(competitionSeason.getId())
                            .footballId(competitionSeason.getFootballId())
                            .startDate(competitionSeason.getStartDate())
                            .endDate(competitionSeason.getEndDate())
                            .winner(competitionSeason.getWinner())
                            .build())
                    .competitionTableList(new ArrayList<>())
                    .build());
        }
        return currentMatchDayDtoList;
    }

    private Competition mapToCompetition(final CompetitionSeasonDto competitionSeasonDto) {
        return Competition.builder()
                .id(competitionSeasonDto.getCompetition().getId())
                .footballId(competitionSeasonDto.getCompetition().getFootballId())
                .name(competitionSeasonDto.getCompetition().getName())
                .competitionSeasonList(new ArrayList<>())
                .build();
    }

    private CompetitionDto mapToCompetitionDto(final CompetitionSeason competitionSeason) {
        return CompetitionDto.builder()
                .id(competitionSeason.getCompetition().getId())
                .footballId(competitionSeason.getCompetition().getFootballId())
                .name(competitionSeason.getCompetition().getName())
                .competitionSeasonList(new ArrayList<>())
                .build();
    }
}
