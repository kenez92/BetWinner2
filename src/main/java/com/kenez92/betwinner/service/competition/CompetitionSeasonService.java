package com.kenez92.betwinner.service.competition;

import com.kenez92.betwinner.domain.table.CompetitionSeason;
import com.kenez92.betwinner.domain.table.CompetitionSeasonDto;
import com.kenez92.betwinner.domain.table.CurrentMatchDay;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.table.CompetitionSeasonMapper;
import com.kenez92.betwinner.repository.table.CompetitionSeasonRepository;
import com.kenez92.betwinner.repository.table.CurrentMatchDayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompetitionSeasonService {
    private final CompetitionSeasonRepository competitionSeasonRepository;
    private final CurrentMatchDayRepository currentMatchDayRepository;
    private final CompetitionSeasonMapper competitionSeasonMapper;

    public boolean competitionSeasonExistByFootballId(final Long competitionSeasonId) {
        boolean result = competitionSeasonRepository.existsByFootballId(competitionSeasonId);
        return result;
    }

    public CompetitionSeasonDto saveCompetitionSeason(final CompetitionSeasonDto competitionSeasonDto) {
        log.info("Saving competition season : {}", competitionSeasonDto);
        CompetitionSeason competitionSeason = competitionSeasonMapper.mapToCompetitionSeason(competitionSeasonDto);
        CompetitionSeason savedCompetitionSeason = competitionSeasonRepository.save(competitionSeason);
        CompetitionSeasonDto savedCompetitionSeasonDto = competitionSeasonMapper.mapToCompetitionSeasonDto(savedCompetitionSeason);
        log.info("Saved competition season: {}", savedCompetitionSeasonDto);
        return savedCompetitionSeasonDto;
    }

    public CompetitionSeasonDto getCompetitionSeasonByFootballId(final Long competitionSeasonId) {
        log.info("Getting competition season by id: {}", competitionSeasonId);
        CompetitionSeason competitionSeason = competitionSeasonRepository.findByFootballId(competitionSeasonId).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_COMPETITION_SEASON_NOT_FOUND_EXCEPTION));
        fetchCurrentMatchDayList(competitionSeason);
        CompetitionSeasonDto competitionSeasonDto = competitionSeasonMapper.mapToCompetitionSeasonDto(competitionSeason);
        log.info("Return competition season: {}", competitionSeasonDto);
        return competitionSeasonDto;
    }

    public void fetchCurrentMatchDayList(final CompetitionSeason competitionSeason) {
        List<CurrentMatchDay> currentMatchDayList = currentMatchDayRepository.findByCompetitionSeason(competitionSeason);
        competitionSeason.setCurrentMatchDayList(currentMatchDayList);

    }
}
