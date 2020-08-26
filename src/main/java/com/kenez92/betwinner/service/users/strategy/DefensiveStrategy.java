package com.kenez92.betwinner.service.users.strategy;

import com.kenez92.betwinner.domain.matches.MatchDto;
import com.kenez92.betwinner.service.matches.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DefensiveStrategy implements UserStrategy {
    private final MatchService matchService;

    @Override
    public List<MatchDto> predictMatches() {
        return matchService.predictMatches(70.0, 100.0);
    }
}
