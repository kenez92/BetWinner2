package com.kenez92.betwinner.matchStats;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface MatchStatsMapper {

    MatchStats mapToMatchStatsDto(MatchStats matchStats);
}
