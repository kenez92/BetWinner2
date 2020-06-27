package com.kenez92.betwinner.service;

import com.kenez92.betwinner.domain.CompetitionSeason;
import com.kenez92.betwinner.domain.CompetitionSeasonDto;
import com.kenez92.betwinner.domain.CurrentMatchDay;
import com.kenez92.betwinner.domain.CurrentMatchDayDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.CompetitionSeasonMapper;
import com.kenez92.betwinner.mapper.CurrentMatchDayMapper;
import com.kenez92.betwinner.repository.CurrentMatchDayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CurrentMatchDayService {
    private final CurrentMatchDayRepository currentMatchDayRepository;
    private final CurrentMatchDayMapper currentMatchDayMapper;
    private final CompetitionSeasonMapper competitionSeasonMapper;

    public boolean currentMatchDayExistBySeasonAndMatchDay(CompetitionSeasonDto competitionSeasonDto, Integer matchDay) {
        CompetitionSeason competitionSeason = competitionSeasonMapper.mapToCompetitionSeason(competitionSeasonDto);
        boolean result = currentMatchDayRepository.existsByCompetitionSeasonAndMatchDay(competitionSeason, matchDay);
        return result;
    }

    public CurrentMatchDayDto saveCurrentMatchDay(CurrentMatchDayDto currentMatchDayDto) {
        CurrentMatchDay currentMatchDay = currentMatchDayRepository.save(currentMatchDayMapper.mapToCurrentMatchDay(currentMatchDayDto));
        return currentMatchDayMapper.mapToCurrentMatchDayDto(currentMatchDay);
    }

    public CurrentMatchDayDto getCurrentMatchDayBySeasonAndMatchDay(CompetitionSeasonDto competitionSeasonDto, Integer matchDay) {
        log.info("Getting current match day by season and matchDay: {}{}", competitionSeasonDto, matchDay);
        CompetitionSeason competitionSeason = competitionSeasonMapper.mapToCompetitionSeason(competitionSeasonDto);
        CurrentMatchDay currentMatchDay = currentMatchDayRepository.findByCompetitionSeasonAndMatchDay(competitionSeason, matchDay)
                .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_CURRENT_MATCH_DAY_NOT_FOUND_EXCEPTION));
        CurrentMatchDayDto currentMatchDayDto = currentMatchDayMapper.mapToCurrentMatchDayDto(currentMatchDay);
        return currentMatchDayDto;

    }
}
