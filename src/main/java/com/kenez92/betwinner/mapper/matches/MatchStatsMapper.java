package com.kenez92.betwinner.mapper.matches;

import com.kenez92.betwinner.persistence.entity.matches.MatchStats;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface MatchStatsMapper {

    MatchStats mapToMatchStatsDto(MatchStats matchStats);
}
