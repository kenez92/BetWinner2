package com.kenez92.betwinner.competitionTable;

import com.kenez92.betwinner.competitionTableElement.CompetitionTableElementMapper;
import com.kenez92.betwinner.currentMatchDay.CurrentMatchDayMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {CurrentMatchDayMapper.class, CompetitionTableElementMapper.class})
@Component
public interface CompetitionTableMapper {
    CompetitionTable mapToCompetitionTable(final CompetitionTableDto competitionTableDto);

    @Mapping(target = "currentMatchDay", qualifiedByName = "currentMatchDayDtoForCompetitionTableDto")
    @Mapping(target = "competitionTableElements", qualifiedByName = "competitionTableElementDtosForCompetitionTableDto")
    CompetitionTableDto mapToCompetitionTableDto(final CompetitionTable competitionTable);

    @Mapping(target = "currentMatchDay", ignore = true)
    @Mapping(target = "competitionTableElements", ignore = true)
    CompetitionTableDto mapToCompetitionTableDtoForCurrentMatchDay(final CompetitionTable competitionTable);

    @Named("competitionTableDtoForCompetitionTableElementDto")
    @Mapping(target = "currentMatchDay", qualifiedByName = "currentMatchDayDtoForCompetitionTableDto")
    @Mapping(target = "competitionTableElements", ignore = true)
    CompetitionTableDto mapToCompetitionTableDtoForCompetitionTableDto(final CompetitionTable competitionTable);

    default List<CompetitionTableDto> mapToCompetitionTableDtoList(final List<CompetitionTable> competitionTableList) {
        return new ArrayList<>(competitionTableList).stream()
                .map(this::mapToCompetitionTableDto)
                .collect(Collectors.toList());
    }

    @Named("competitionTableDtosForCurrentMatchDayDto")
    default List<CompetitionTableDto> mapToCompetitionTableDtoListForCurrentMatchDay(
            final List<CompetitionTable> competitionTableList) {
        return new ArrayList<>(competitionTableList).stream()
                .map(this::mapToCompetitionTableDtoForCurrentMatchDay)
                .collect(Collectors.toList());
    }
}
