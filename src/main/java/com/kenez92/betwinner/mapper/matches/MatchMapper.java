package com.kenez92.betwinner.mapper.matches;

import com.kenez92.betwinner.domain.matches.MatchDto;
import com.kenez92.betwinner.persistence.entity.matches.Match;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper
public interface MatchMapper {
    Match mapToMatch(MatchDto matchDto);

    @Mapping(target = "weather", ignore = true)
    @Mapping(target = "matchDay", ignore = true)
    MatchDto mapToMatchDto(Match match);

    default List<MatchDto> mapToMatchDtoList(List<Match> matchList) {
        if (matchList == null) {
            return new ArrayList<>();
        } else {
            return matchList.stream()
                    .map(this::mapToMatchDto)
                    .collect(Collectors.toList());
        }
    }
}