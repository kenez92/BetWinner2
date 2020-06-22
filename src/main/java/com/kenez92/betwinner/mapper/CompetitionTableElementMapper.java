package com.kenez92.betwinner.mapper;

import com.kenez92.betwinner.domain.CompetitionTableElement;
import com.kenez92.betwinner.domain.CompetitionTableElementDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CompetitionTableElementMapper {

    CompetitionTableElement mapToCompetitionTableElement(CompetitionTableElementDto competitionTableElementDto);

    CompetitionTableElementDto mapToCompetitionTableElementDto(CompetitionTableElement competitionTableElement);
}
