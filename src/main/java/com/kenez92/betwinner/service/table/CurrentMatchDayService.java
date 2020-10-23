package com.kenez92.betwinner.service.table;

import com.kenez92.betwinner.domain.table.CompetitionSeasonDto;
import com.kenez92.betwinner.domain.table.CurrentMatchDayDto;
import com.kenez92.betwinner.entity.table.CompetitionSeason;
import com.kenez92.betwinner.entity.table.CompetitionTable;
import com.kenez92.betwinner.entity.table.CurrentMatchDay;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.table.CompetitionSeasonMapper;
import com.kenez92.betwinner.mapper.table.CurrentMatchDayMapper;
import com.kenez92.betwinner.repository.table.CompetitionTableRepository;
import com.kenez92.betwinner.repository.table.CurrentMatchDayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CurrentMatchDayService {
    private final CurrentMatchDayRepository currentMatchDayRepository;
    private final CompetitionTableRepository competitionTableRepository;
    private final CurrentMatchDayMapper currentMatchDayMapper;
    private final CompetitionSeasonMapper competitionSeasonMapper;


    public List<CurrentMatchDayDto> getCurrentMatchDays() {
        log.debug("Getting all current match days");
        List<CurrentMatchDay> currentMatchDayList = currentMatchDayRepository.findAll();
        List<CurrentMatchDayDto> currentMatchDayDtoList = currentMatchDayMapper
                .mapToCurrentMatchDayDtoList(currentMatchDayList);
        log.debug("Return all current match days: {}", currentMatchDayDtoList);
        return currentMatchDayDtoList;
    }

    public CurrentMatchDayDto getCurrentMatchDay(final Long currentMatchDayId) {
        log.debug("Getting current match day by id: {}", currentMatchDayId);
        CurrentMatchDay currentMatchDay = currentMatchDayRepository.findById(currentMatchDayId).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_CURRENT_MATCH_DAY_NOT_FOUND_EXCEPTION));
        CurrentMatchDayDto currentMatchDayDto = currentMatchDayMapper.mapToCurrentMatchDayDto(currentMatchDay);
        log.debug("Return current match day: {}", currentMatchDayDto);
        return currentMatchDayDto;
    }

    public boolean currentMatchDayExistBySeasonAndMatchDay(final CompetitionSeasonDto competitionSeasonDto,
                                                           final Integer matchDay) {
        CompetitionSeason competitionSeason = competitionSeasonMapper.mapToCompetitionSeason(competitionSeasonDto);
        boolean result = currentMatchDayRepository.existsByCompetitionSeasonAndMatchDay(competitionSeason, matchDay);
        log.debug("Current match day exists in repository: {}", result);
        return result;
    }

    public CurrentMatchDayDto saveCurrentMatchDay(final CurrentMatchDayDto currentMatchDayDto) {
        log.debug("Saving current match day: {}", currentMatchDayDto);
        CurrentMatchDay currentMatchDay = currentMatchDayRepository.save(currentMatchDayMapper.mapToCurrentMatchDay(currentMatchDayDto));
        CurrentMatchDayDto savedCurrentMatchDayDto = currentMatchDayMapper.mapToCurrentMatchDayDto(currentMatchDay);
        log.debug("Return saved current match day: {}", savedCurrentMatchDayDto);
        return savedCurrentMatchDayDto;
    }

    public CurrentMatchDayDto getCurrentMatchDayBySeasonAndMatchDay(final CompetitionSeasonDto competitionSeasonDto,
                                                                    final Integer matchDay) {
        log.debug("Getting current match day by season and matchDay: {}{}", competitionSeasonDto, matchDay);
        CompetitionSeason competitionSeason = competitionSeasonMapper.mapToCompetitionSeason(competitionSeasonDto);
        CurrentMatchDay currentMatchDay = currentMatchDayRepository.findByCompetitionSeasonAndMatchDay(competitionSeason, matchDay)
                .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_CURRENT_MATCH_DAY_NOT_FOUND_EXCEPTION));
        fetchAndSetData(currentMatchDay);
        CurrentMatchDayDto currentMatchDayDto = currentMatchDayMapper.mapToCurrentMatchDayDto(currentMatchDay);
        log.debug("Return current match day by season and matchDay: {}", currentMatchDayDto);
        return currentMatchDayDto;
    }

    public List<CurrentMatchDayDto> getCurrentMatchDaysByCompetitionSeasonId(final Long competitionSeasonId) {
        log.debug("Getting current match days by competition season: {}", competitionSeasonId);
        List<CurrentMatchDay> currentMatchDayList = currentMatchDayRepository.findByCompetitionSeasonId(competitionSeasonId);
        for(CurrentMatchDay currentMatchDay : currentMatchDayList) {
            fetchAndSetData(currentMatchDay);
        }
        List<CurrentMatchDayDto> currentMatchDayDtoList = currentMatchDayMapper
                .mapToCurrentMatchDayDtoList(currentMatchDayList);
        log.debug("Return all current match days: {}", currentMatchDayDtoList);
        return currentMatchDayDtoList;
    }

    private void fetchAndSetData(final CurrentMatchDay currentMatchDay) {
        List<CompetitionTable> competitionTableList = competitionTableRepository.findByCurrentMatchDay(currentMatchDay);
        currentMatchDay.setCompetitionTableList(competitionTableList);
    }
}
