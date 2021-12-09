package com.kenez92.betwinner.competitionTableElement;

import com.kenez92.betwinner.competitionTable.CompetitionTableMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {CompetitionTableMapper.class})
@Component
public interface CompetitionTableElementMapper {
    CompetitionTableElement mapToCompetitionTableElement(CompetitionTableElementDto competitionTableElementDto);

    @Mapping(target = "competitionTable", qualifiedByName = "competitionTableDtoForCompetitionTableElementDto")
    CompetitionTableElementDto mapToCompetitionTableElementDto(CompetitionTableElement competitionTableElement);

    @Mapping(target = "competitionTable", ignore = true)
    CompetitionTableElementDto mapToCompetitionTableElementDtoForCompetitionTable(
            CompetitionTableElement competitionTableElement);

    default List<CompetitionTableElementDto> mapToCompetitionTableElementDtoList(List<CompetitionTableElement> competitionTableElementList) {
        return new ArrayList<>(competitionTableElementList.stream()
                .map(this::mapToCompetitionTableElementDto)
                .collect(Collectors.toList()));
    }

    @Named("competitionTableElementDtosForCompetitionTableDto")
    default List<CompetitionTableElementDto> mapToCompetitionTableElementDtosForCompetitionTable(
            List<CompetitionTableElement> competitionTableElementList) {
        return new ArrayList<>(competitionTableElementList.stream()
                .map(this::mapToCompetitionTableElementDtoForCompetitionTable)
                .collect(Collectors.toList()));
    }
}
