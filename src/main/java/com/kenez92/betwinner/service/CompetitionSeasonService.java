package com.kenez92.betwinner.service;

import com.kenez92.betwinner.domain.CompetitionSeason;
import com.kenez92.betwinner.domain.CompetitionSeasonDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.CompetitionSeasonMapper;
import com.kenez92.betwinner.repository.CompetitionSeasonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompetitionSeasonService {
    private final CompetitionSeasonRepository competitionSeasonRepository;
    private final CompetitionSeasonMapper competitionSeasonMapper;

    public boolean competitionSeasonExistByFootballId(Long competitionSeasonId) {
        boolean result = competitionSeasonRepository.existsByFootballId(competitionSeasonId);
        return result;
    }

    public CompetitionSeasonDto saveCompetitionSeason(CompetitionSeasonDto competitionSeasonDto) {
        CompetitionSeason competitionSeason = competitionSeasonMapper.mapToCompetitionSeason(competitionSeasonDto);
        CompetitionSeason savedCompetitionDto = competitionSeasonRepository.save(competitionSeason);
        return competitionSeasonMapper.mapToCompetitionSeasonDto(savedCompetitionDto);
    }

    public CompetitionSeasonDto getCompetitionSeasonByFootballId(Long competitionSeasonId) {
        log.info("Getting competition season by id: {}", competitionSeasonId);
        CompetitionSeason competitionSeason = competitionSeasonRepository.findByFootballId(competitionSeasonId).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_COMPETITION_SEASON_NOT_FOUND_EXCEPTION));
        CompetitionSeasonDto competitionSeasonDto = competitionSeasonMapper.mapToCompetitionSeasonDto(competitionSeason);
        return competitionSeasonDto;
    }
}
