package com.kenez92.betwinner.mapper;

import com.kenez92.betwinner.domain.CompetitionTable;
import com.kenez92.betwinner.domain.CompetitionTableDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface CompetitionTableMapper {
    CompetitionTable mapToCompetitionTable(CompetitionTableDto competitionTableDto);

    CompetitionTableDto mapToCompetitionTableDto(CompetitionTable competitionTable);
}
