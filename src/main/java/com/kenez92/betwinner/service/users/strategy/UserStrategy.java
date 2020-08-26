package com.kenez92.betwinner.service.users.strategy;

import com.kenez92.betwinner.domain.matches.MatchDto;
import org.springframework.stereotype.Component;

import java.util.List;

public interface UserStrategy {
    List<MatchDto> predictMatches();
}
