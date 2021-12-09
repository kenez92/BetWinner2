package com.kenez92.betwinner.currentMatchDay;

import com.kenez92.betwinner.competitionSeason.CompetitionSeasonMapper;
import com.kenez92.betwinner.competitionTable.CompetitionTableMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {CompetitionSeasonMapper.class, CompetitionTableMapper.class})
@Component
public interface CurrentMatchDayMapper {
    CurrentMatchDay mapToCurrentMatchDay(final CurrentMatchDayDto currentMatchDayDto);

    @Mapping(target = "competitionTableList", qualifiedByName = "competitionTableDtosForCurrentMatchDayDto")
    @Mapping(target = "competitionSeason", qualifiedByName = "competitionSeasonDtoForCurrentMatchDayDto")
    CurrentMatchDayDto mapToCurrentMatchDayDto(final CurrentMatchDay currentMatchDay);

    @Mapping(target = "competitionTableList", ignore = true)
    @Mapping(target = "competitionSeason", ignore = true)
    CurrentMatchDayDto mapToCurrentMatchDayDtoForCompetitionSeason(final CurrentMatchDay currentMatchDay);

    @Named("currentMatchDayDtoForCompetitionTableDto")
    @Mapping(target = "competitionTableList", ignore = true)
    @Mapping(target = "competitionSeason", qualifiedByName = "competitionSeasonDtoForCurrentMatchDayDto")
    CurrentMatchDayDto mapToCurrentMatchDayDtoForCompetitionTable(final CurrentMatchDay currentMatchDay);


    default List<CurrentMatchDayDto> mapToCurrentMatchDayDtoList(final List<CurrentMatchDay> currentMatchDayList) {
        return new ArrayList<>(currentMatchDayList).stream()
                .map(this::mapToCurrentMatchDayDto)
                .collect(Collectors.toList());
    }

    @Named("currentMatchDayDtosForCompetitionSeason")
    default List<CurrentMatchDayDto> mapToCurrentMatchDayDtoListForCompetition(final List<CurrentMatchDay> currentMatchDayList) {
        return new ArrayList<>(currentMatchDayList).stream()
                .map(this::mapToCurrentMatchDayDtoForCompetitionSeason)
                .collect(Collectors.toList());

    }
}
