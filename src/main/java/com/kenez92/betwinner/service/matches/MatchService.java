package com.kenez92.betwinner.service.matches;

import com.kenez92.betwinner.domain.matches.MatchDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.matches.MatchMapper;
import com.kenez92.betwinner.persistence.entity.matches.Match;
import com.kenez92.betwinner.persistence.repository.matches.MatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MatchService {
    private final MatchRepository matchRepository;
    private final MatchMapper matchMapper;

    public List<MatchDto> getMatches() {
        log.debug("Getting all matches");
        List<Match> matchList = matchRepository.findAll();
        List<MatchDto> matchDtoList = matchMapper.mapToMatchDtoList(matchList);
        log.debug("Return all matches: {}", matchDtoList);
        return matchDtoList;
    }

    public MatchDto getMatch(final Long matchId) {
        log.debug("Getting match by id: {}", matchId);
        Match match = matchRepository.findById(matchId).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_MATCH_NOT_FOUND_EXCEPTION));
        MatchDto matchDto = matchMapper.mapToMatchDto(match);
        log.debug("Return match: {}", matchDto);
        return matchDto;
    }

    public MatchDto findByFields(final String homeTeam, final String awayTeam, final Integer round) {
        log.debug("Find match by fields: {}{}{}", homeTeam, awayTeam, round);
        Match match = matchRepository.findByHomeTeamAndAwayTeamAndRound(homeTeam, awayTeam, round)
                .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_MATCH_NOT_FOUND_EXCEPTION));
        MatchDto matchDto = matchMapper.mapToMatchDto(match);
        log.debug("Found match by fields: {}", matchDto);
        return matchDto;

    }

    public MatchDto saveMatch(final MatchDto matchDto) {
        log.debug("Saving match: {}", matchDto);
        Match match = matchRepository.save(matchMapper.mapToMatch(matchDto));
        MatchDto savedMatchDto = matchMapper.mapToMatchDto(match);
        log.debug("Return saved match: {}", savedMatchDto);
        return savedMatchDto;
    }

    public List<MatchDto> getMatchesAtDay(final String date) {
        log.debug("Getting matches at: {}", date);
        Date sqlDate;
        try {
            sqlDate = Date.valueOf(date);
        } catch (IllegalArgumentException e) {
            throw new BetWinnerException(BetWinnerException.ERR_ILLEGAL_ARGUMENT_EXCEPTION);
        }
        List<Match> matches = matchRepository.findMatchesAtDate(sqlDate);
        List<MatchDto> matchDtoList = matchMapper.mapToMatchDtoList(matches);
        log.debug("Return matches: {}", matchDtoList);
        return matchDtoList;
    }
}
