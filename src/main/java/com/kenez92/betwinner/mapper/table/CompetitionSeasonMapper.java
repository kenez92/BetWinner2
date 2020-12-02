package com.kenez92.betwinner.mapper.table;

import com.kenez92.betwinner.domain.table.CompetitionSeasonDto;
import com.kenez92.betwinner.persistence.entity.table.CompetitionSeason;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
@Component
public interface CompetitionSeasonMapper {
    CompetitionSeason mapToCompetitionSeason(final CompetitionSeasonDto competitionSeasonDto);

    @Mapping(target = "currentMatchDayList", ignore = true)
    @Mapping(target = "competition", ignore = true)
    CompetitionSeasonDto mapToCompetitionSeasonDto(final CompetitionSeason competitionSeason);

    default List<CompetitionSeasonDto> mapToCompetitionSeasonDtoList(final List<CompetitionSeason> competitionSeasons) {
        return new ArrayList<>(competitionSeasons).stream()
                .map(this::mapToCompetitionSeasonDto)
                .collect(Collectors.toList());
    }
}
