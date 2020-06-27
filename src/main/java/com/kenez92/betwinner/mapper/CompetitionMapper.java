package com.kenez92.betwinner.mapper;

import com.kenez92.betwinner.domain.Competition;
import com.kenez92.betwinner.domain.CompetitionDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface CompetitionMapper {

    Competition mapToCompetition(CompetitionDto competitionDto);

    CompetitionDto mapToCompetitionDto(Competition competition);
}
