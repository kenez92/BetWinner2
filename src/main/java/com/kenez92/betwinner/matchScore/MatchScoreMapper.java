package com.kenez92.betwinner.matchScore;

import com.kenez92.betwinner.match.MatchMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {MatchMapper.class})
@Component
public interface MatchScoreMapper {
    MatchScore mapToMatchScore(MatchScoreDto matchScoreDto);

    MatchScoreDto mapToMatchScoreDto(MatchScore matchScore);

    default List<MatchScoreDto> mapToMatchScoreDtoList(List<MatchScore> matchScoreList) {
        return new ArrayList<>(matchScoreList).stream()
                .map(this::mapToMatchScoreDto)
                .collect(Collectors.toList());
    }
}
