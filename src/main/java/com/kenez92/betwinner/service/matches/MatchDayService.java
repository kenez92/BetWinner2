package com.kenez92.betwinner.service.matches;

import com.kenez92.betwinner.domain.matches.Match;
import com.kenez92.betwinner.domain.matches.MatchDay;
import com.kenez92.betwinner.domain.matches.MatchDayDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.matches.MatchDayMapper;
import com.kenez92.betwinner.repository.matches.MatchDayRepository;
import com.kenez92.betwinner.repository.matches.MatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MatchDayService {
    private final MatchDayRepository matchDayRepository;
    private final MatchRepository matchRepository;
    private final MatchDayMapper matchDayMapper;

    public Boolean existByLocalDate(LocalDate localDate) {
        Boolean result = matchDayRepository.existsByLocalDate(localDate);
        return result;
    }

    public MatchDayDto getByLocalDate(LocalDate localDate) {
        log.info("Getting match day by local date: {}", localDate);
        MatchDay matchDay = matchDayRepository.findByLocalDate(localDate).orElseThrow(() ->
                new BetWinnerException(BetWinnerException.ERR_MATCH_NOT_FOUND_EXCEPTION));
        fetchMatches(matchDay);
        MatchDayDto matchDayDto = matchDayMapper.mapToMatchDayDto(matchDay);
        log.info("Return match found by local date: {}", matchDayDto);
        return matchDayDto;
    }

    public MatchDayDto saveMatchDay(MatchDayDto matchDayDto) {
        log.info("Saving match day: {}", matchDayDto);
        MatchDay matchDay = matchDayMapper.mapToMatchDay(matchDayDto);
        MatchDay savedMatchDay = matchDayRepository.save(matchDay);
        MatchDayDto savedMatchDayDto = matchDayMapper.mapToMatchDayDto(savedMatchDay);
        log.info("Return saved match day: {}", savedMatchDay);
        return savedMatchDayDto;
    }

    private void fetchMatches(MatchDay matchDay) {
        List<Match> matches = matchRepository.findByMatchDay(matchDay);
        matchDay.setMatchesList(matches);
    }
}
