package com.kenez92.betwinner.service.matches;

import com.kenez92.betwinner.domain.matches.MatchDayDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.matches.MatchDayMapper;
import com.kenez92.betwinner.persistence.entity.matches.Match;
import com.kenez92.betwinner.persistence.entity.matches.MatchDay;
import com.kenez92.betwinner.persistence.repository.matches.MatchDayRepository;
import com.kenez92.betwinner.persistence.repository.matches.MatchRepository;
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

    public boolean existByLocalDate(final LocalDate localDate) {
        boolean result = matchDayRepository.existsByLocalDate(localDate);
        log.info("Match day exists in repository: {}", result);
        return result;
    }

    public MatchDayDto getByLocalDate(final LocalDate localDate) {
        log.info("Getting match day by local date: {}", localDate);
        MatchDay matchDay = matchDayRepository.findByLocalDate(localDate).orElseThrow(() ->
                new BetWinnerException(BetWinnerException.ERR_MATCH_DAY_NOT_FOUND_EXCEPTION));
        fetchMatches(matchDay);
        MatchDayDto matchDayDto = matchDayMapper.mapToMatchDayDto(matchDay);
        log.info("Return match found by local date: {}", matchDayDto);
        return matchDayDto;
    }

    public MatchDayDto saveMatchDay(final MatchDayDto matchDayDto) {
        log.info("Saving match day: {}", matchDayDto);
        MatchDay matchDay = matchDayMapper.mapToMatchDay(matchDayDto);
        MatchDay savedMatchDay = matchDayRepository.save(matchDay);
        MatchDayDto savedMatchDayDto = matchDayMapper.mapToMatchDayDto(savedMatchDay);
        log.info("Return saved match day: {}", savedMatchDay);
        return savedMatchDayDto;
    }

    public List<MatchDayDto> getMatchDays() {
        log.debug("Getting all match days");
        List<MatchDay> matchDayList = matchDayRepository.findAll();
        List<MatchDayDto> matchDayDtoList = matchDayMapper.mapToMatchDayDtoList(matchDayList);
        log.debug("Return all match days: {}", matchDayDtoList);
        return matchDayDtoList;
    }

    public MatchDayDto getMatchDay(final Long matchDayId) {
        log.debug("Get match day id: {}", matchDayId);
        MatchDay matchDay = matchDayRepository.findById(matchDayId).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_MATCH_DAY_NOT_FOUND_EXCEPTION));
        MatchDayDto matchDayDto = matchDayMapper.mapToMatchDayDto(matchDay);
        log.debug("Return match day: {}", matchDayDto);
        return matchDayDto;
    }

    public MatchDay getMatchDayByLocalDate(final LocalDate localDate) {
        log.info("Getting match day by local date: {}", localDate);
        MatchDay matchDay = matchDayRepository.findByLocalDate(localDate).orElseThrow(() ->
                new BetWinnerException(BetWinnerException.ERR_MATCH_DAY_NOT_FOUND_EXCEPTION));
        fetchMatches(matchDay);
        log.info("Return match found by local date: {}", matchDay);
        return matchDay;
    }

    public MatchDay saveMatchDay(MatchDay matchDay) {
        log.info("Saving new match day: {}", matchDay);
        MatchDay savedMatchDay = matchDayRepository.save(matchDay);
        log.info("Return match day: {}", savedMatchDay);
        return savedMatchDay;
    }

    private void fetchMatches(final MatchDay matchDay) {
        List<Match> matches = matchRepository.findByMatchDay(matchDay);
        matchDay.setMatchesList(matches);
    }
}
