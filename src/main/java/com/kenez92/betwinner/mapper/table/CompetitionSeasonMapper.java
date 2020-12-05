package com.kenez92.betwinner.mapper.table;

import com.kenez92.betwinner.domain.table.CompetitionSeasonDto;
import com.kenez92.betwinner.persistence.entity.table.CompetitionSeason;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {CompetitionMapper.class, CurrentMatchDayMapper.class})
@Component
public interface CompetitionSeasonMapper {

    CompetitionSeason mapToCompetitionSeason(final CompetitionSeasonDto competitionSeasonDto);

    @Mapping(target = "competition", qualifiedByName = "competitionDtoForCompetitionSeasonDto")
    @Mapping(target = "currentMatchDayList", qualifiedByName = "currentMatchDayDtosForCompetitionSeason")
    CompetitionSeasonDto mapToCompetitionSeasonDto(CompetitionSeason competitionSeason);

    @Mapping(target = "competition", ignore = true)
    @Mapping(target = "currentMatchDayList", ignore = true)
    CompetitionSeasonDto mapToCompetitionSeasonDtoForCompetition(final CompetitionSeason competitionSeason);

    @Named("competitionSeasonDtoForCurrentMatchDayDto")
    @Mapping(target = "competition", qualifiedByName = "competitionDtoForCompetitionSeasonDto")
    @Mapping(target = "currentMatchDayList", ignore = true)
    CompetitionSeasonDto mapToCompetitionSeasonDtoForCurrentMatchDay(final CompetitionSeason competitionSeason);

    default List<CompetitionSeasonDto> mapToCompetitionSeasonDtoList(final List<CompetitionSeason> competitionSeasons) {
        return new ArrayList<>(competitionSeasons).stream()
                .map(this::mapToCompetitionSeasonDto)
                .collect(Collectors.toList());
    }

    @Named("competitionSeasonDtosForCompetitionDto")
    default List<CompetitionSeasonDto> mapToCompetitionSeasonDtoListForCompetition(
            final List<CompetitionSeason> competitionSeasons) {
        return new ArrayList<>(competitionSeasons).stream()
                .map(this::mapToCompetitionSeasonDtoForCompetition)
                .collect(Collectors.toList());
    }
}
