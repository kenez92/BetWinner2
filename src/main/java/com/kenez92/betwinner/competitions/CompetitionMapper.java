package com.kenez92.betwinner.competitions;

import com.kenez92.betwinner.competitionSeason.CompetitionSeasonMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {CompetitionSeasonMapper.class})
@Component
public interface CompetitionMapper {
    Competition mapToCompetition(final CompetitionDto competitionDto);

    @Mapping(target = "competitionSeasonList", qualifiedByName = "competitionSeasonDtosForCompetitionDto")
    CompetitionDto mapToCompetitionDto(final Competition competition);

    @Named("competitionDtoForCompetitionSeasonDto")
    @Mapping(target = "competitionSeasonList", ignore = true)
    CompetitionDto mapToCompetitionDtoForCompetitionSeason(final Competition competition);

    default List<CompetitionDto> mapToCompetitionDtoList(final List<Competition> competitionList) {
        return new ArrayList<>(competitionList).stream()
                .map(this::mapToCompetitionDto)
                .collect(Collectors.toList());
    }
}
