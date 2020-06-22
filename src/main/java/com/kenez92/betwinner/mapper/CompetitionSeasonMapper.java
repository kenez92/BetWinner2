package com.kenez92.betwinner.mapper;

import com.kenez92.betwinner.domain.CompetitionSeason;
import com.kenez92.betwinner.domain.CompetitionSeasonDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface CompetitionSeasonMapper {

    CompetitionSeason mapToCompetitionSeason(CompetitionSeasonDto competitionSeasonDto);

    CompetitionSeasonDto mapToCompetitionSeasonDto(CompetitionSeason competitionSeason);
}
