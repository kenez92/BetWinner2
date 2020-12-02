package com.kenez92.betwinner.mapper.table;

import com.kenez92.betwinner.domain.table.CompetitionTableElementDto;
import com.kenez92.betwinner.persistence.entity.table.CompetitionTableElement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
@Component
public interface CompetitionTableElementMapper {
    CompetitionTableElement mapToCompetitionTableElement(CompetitionTableElementDto competitionTableElementDto);

    @Mapping(target = "competitionTable", ignore = true)
    CompetitionTableElementDto mapToCompetitionTableElementDto(CompetitionTableElement competitionTableElement);

    default List<CompetitionTableElementDto> mapToCompetitionTableElementDtoList(List<CompetitionTableElement> competitionTableElementList) {
        return new ArrayList<>(competitionTableElementList.stream()
                .map(this::mapToCompetitionTableElementDto)
                .collect(Collectors.toList()));
    }
}
