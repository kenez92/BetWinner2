package com.kenez92.betwinner.service.matches;

import com.kenez92.betwinner.domain.matches.Match;
import com.kenez92.betwinner.domain.matches.MatchDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.matches.MatchMapper;
import com.kenez92.betwinner.repository.matches.MatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MatchService {
    private final MatchRepository matchRepository;
    private final MatchMapper matchMapper;

    public boolean existsByFields(final String homeTeam, final String awayTeam, final Integer round) {
        boolean result = matchRepository.existsByHomeTeamAndAwayTeamAndRound(homeTeam, awayTeam, round);
        log.info("Match exists in repository: {}", result);
        return result;
    }

    public MatchDto findByFields(final String homeTeam, final String awayTeam, final Integer round) {
        log.info("Find match by fields: {}{}{}", homeTeam, awayTeam, round);
        Match match = matchRepository.findByHomeTeamAndAwayTeamAndRound(homeTeam, awayTeam, round)
                .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_MATCH_NOT_FOUND_EXCEPTION));
        MatchDto matchDto = matchMapper.mapToMatchDto(match);
        log.info("Found match by fields: {}", matchDto);
        return matchDto;

    }

    public MatchDto saveMatch(final MatchDto matchDto) {
        log.info("Saving match: {}", matchDto);
        Match match = matchRepository.save(matchMapper.mapToMatch(matchDto));
        MatchDto savedMatchDto = matchMapper.mapToMatchDto(match);
        log.info("Return saved match: {}", savedMatchDto);
        return savedMatchDto;
    }
}
