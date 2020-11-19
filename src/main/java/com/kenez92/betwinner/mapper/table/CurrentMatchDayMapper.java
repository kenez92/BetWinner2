package com.kenez92.betwinner.mapper.table;

import com.kenez92.betwinner.domain.table.CompetitionDto;
import com.kenez92.betwinner.domain.table.CompetitionSeasonDto;
import com.kenez92.betwinner.domain.table.CompetitionTableDto;
import com.kenez92.betwinner.domain.table.CurrentMatchDayDto;
import com.kenez92.betwinner.persistence.entity.table.Competition;
import com.kenez92.betwinner.persistence.entity.table.CompetitionSeason;
import com.kenez92.betwinner.persistence.entity.table.CompetitionTable;
import com.kenez92.betwinner.persistence.entity.table.CurrentMatchDay;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CurrentMatchDayMapper {

    public CurrentMatchDay mapToCurrentMatchDay(final CurrentMatchDayDto currentMatchDayDto) {
        return CurrentMatchDay.builder()
                .id(currentMatchDayDto.getId())
                .matchDay(currentMatchDayDto.getMatchDay())
                .competitionSeason(mapToCompetitionSeason(currentMatchDayDto))
                .competitionTableList(mapToCompetitionTableList(currentMatchDayDto))
                .build();
    }

    public CurrentMatchDayDto mapToCurrentMatchDayDto(final CurrentMatchDay currentMatchDay) {
        return CurrentMatchDayDto.builder()
                .id(currentMatchDay.getId())
                .matchDay(currentMatchDay.getMatchDay())
                .competitionSeason(mapToCompetitionSeasonDto(currentMatchDay))
                .competitionTableList(mapToCompetitionTableDtoList(currentMatchDay))
                .build();
    }

    private CompetitionSeason mapToCompetitionSeason(final CurrentMatchDayDto currentMatchDayDto) {
        return CompetitionSeason.builder()
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
                .build();
    }

    private CompetitionSeasonDto mapToCompetitionSeasonDto(final CurrentMatchDay currentMatchDay) {
        return CompetitionSeasonDto.builder()
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
                .build();
    }

    private List<CompetitionTable> mapToCompetitionTableList(final CurrentMatchDayDto currentMatchDayDto) {
        List<CompetitionTable> competitionTableList = new ArrayList<>();
        if (currentMatchDayDto.getCompetitionTableList() != null) {
            for (CompetitionTableDto competitionTableDto : currentMatchDayDto.getCompetitionTableList()) {
                competitionTableList.add(CompetitionTable.builder()
                        .id(competitionTableDto.getId())
                        .stage(competitionTableDto.getStage())
                        .type(competitionTableDto.getType())
                        .currentMatchDay(CurrentMatchDay.builder()
                                .id(currentMatchDayDto.getId())
                                .matchDay(currentMatchDayDto.getMatchDay())
                                .competitionSeason(mapToCompetitionSeason(currentMatchDayDto))
                                .competitionTableList(new ArrayList<>())
                                .build())
                        .build());
            }
        }
        return competitionTableList;
    }

    private List<CompetitionTableDto> mapToCompetitionTableDtoList(final CurrentMatchDay currentMatchDay) {
        return new ArrayList<>(currentMatchDay.getCompetitionTableList()).stream()
                .map(competitionTable -> CompetitionTableDto.builder()
                        .id(competitionTable.getId())
                        .stage(competitionTable.getStage())
                        .type(competitionTable.getType())
                        .currentMatchDay(CurrentMatchDayDto.builder()
                                .id(currentMatchDay.getId())
                                .matchDay(currentMatchDay.getMatchDay())
                                .competitionSeason(mapToCompetitionSeasonDto(currentMatchDay))
                                .competitionTableList(new ArrayList<>())
                                .build())
                        .build())
                .collect(Collectors.toList());

    }

    public List<CurrentMatchDayDto> mapToCurrentMatchDayDtoList(final List<CurrentMatchDay> currentMatchDayList) {
        return new ArrayList<>(currentMatchDayList).stream()
                .map(this::mapToCurrentMatchDayDto)
                .collect(Collectors.toList());
    }
}
