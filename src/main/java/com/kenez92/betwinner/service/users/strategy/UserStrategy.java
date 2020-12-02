package com.kenez92.betwinner.service.users.strategy;

import com.kenez92.betwinner.domain.matches.MatchDto;

import java.util.List;

public interface UserStrategy {
    List<MatchDto> predictMatches();
}
