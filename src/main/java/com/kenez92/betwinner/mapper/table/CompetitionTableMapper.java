package com.kenez92.betwinner.mapper.table;

import com.kenez92.betwinner.domain.table.CompetitionTableDto;
import com.kenez92.betwinner.persistence.entity.table.CompetitionTable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
@Component
public interface CompetitionTableMapper {
    CompetitionTable mapToCompetitionTable(final CompetitionTableDto competitionTableDto);

    @Mapping(target = "competitionTableElements", ignore = true)
    @Mapping(target = "currentMatchDay", ignore = true)
    CompetitionTableDto mapToCompetitionTableDto(final CompetitionTable competitionTable);

    default List<CompetitionTableDto> mapToCompetitionTableDtoList(final List<CompetitionTable> competitionTableList) {
        return new ArrayList<>(competitionTableList).stream()
                .map(this::mapToCompetitionTableDto)
                .collect(Collectors.toList());
    }
}
