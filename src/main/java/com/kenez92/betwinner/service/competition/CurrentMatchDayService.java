package com.kenez92.betwinner.service.competition;

import com.kenez92.betwinner.domain.table.*;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.table.CompetitionSeasonMapper;
import com.kenez92.betwinner.mapper.table.CurrentMatchDayMapper;
import com.kenez92.betwinner.repository.table.CompetitionTableRepository;
import com.kenez92.betwinner.repository.table.CurrentMatchDayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CurrentMatchDayService {
    private final CurrentMatchDayRepository currentMatchDayRepository;
    private final CompetitionTableRepository competitionTableRepository;
    private final CurrentMatchDayMapper currentMatchDayMapper;
    private final CompetitionSeasonMapper competitionSeasonMapper;

    public boolean currentMatchDayExistBySeasonAndMatchDay(final CompetitionSeasonDto competitionSeasonDto,
                                                           final Integer matchDay) {
        CompetitionSeason competitionSeason = competitionSeasonMapper.mapToCompetitionSeason(competitionSeasonDto);
        boolean result = currentMatchDayRepository.existsByCompetitionSeasonAndMatchDay(competitionSeason, matchDay);
        log.info("Current match day exists in repository: {}", result);
        return result;
    }

    public CurrentMatchDayDto saveCurrentMatchDay(final CurrentMatchDayDto currentMatchDayDto) {
        log.info("Saving current match day: {}", currentMatchDayDto);
        CurrentMatchDay currentMatchDay = currentMatchDayRepository.save(currentMatchDayMapper.mapToCurrentMatchDay(currentMatchDayDto));
        CurrentMatchDayDto savedCurrentMatchDayDto = currentMatchDayMapper.mapToCurrentMatchDayDto(currentMatchDay);
        log.info("Return saved current match day: {}", savedCurrentMatchDayDto);
        return savedCurrentMatchDayDto;
    }

    public CurrentMatchDayDto getCurrentMatchDayBySeasonAndMatchDay(final CompetitionSeasonDto competitionSeasonDto,
                                                                    final Integer matchDay) {
        log.info("Getting current match day by season and matchDay: {}{}", competitionSeasonDto, matchDay);
        CompetitionSeason competitionSeason = competitionSeasonMapper.mapToCompetitionSeason(competitionSeasonDto);
        CurrentMatchDay currentMatchDay = currentMatchDayRepository.findByCompetitionSeasonAndMatchDay(competitionSeason, matchDay)
                .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_CURRENT_MATCH_DAY_NOT_FOUND_EXCEPTION));
        fetchAndSetData(currentMatchDay);
        CurrentMatchDayDto currentMatchDayDto = currentMatchDayMapper.mapToCurrentMatchDayDto(currentMatchDay);
        log.info("Return current match day by season and matchDay: {}", currentMatchDayDto);
        return currentMatchDayDto;
    }

    private void fetchAndSetData(final CurrentMatchDay currentMatchDay) {
        List<CompetitionTable> competitionTableList = competitionTableRepository.findByCurrentMatchDay(currentMatchDay);
        currentMatchDay.setCompetitionTableList(competitionTableList);
    }
}
