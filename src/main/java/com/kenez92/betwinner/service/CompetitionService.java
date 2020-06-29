package com.kenez92.betwinner.service;

import com.kenez92.betwinner.domain.Competition;
import com.kenez92.betwinner.domain.CompetitionDto;
import com.kenez92.betwinner.domain.CompetitionSeason;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.CompetitionMapper;
import com.kenez92.betwinner.repository.CompetitionRepository;
import com.kenez92.betwinner.repository.CompetitionSeasonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CompetitionService {
    private final CompetitionRepository competitionRepository;
    private final CompetitionSeasonRepository competitionSeasonRepository;
    private final CompetitionMapper competitionMapper;

    public boolean competitionExistByFootballId(final Long competitionId) {
        boolean result = competitionRepository.existsByFootballId(competitionId);
        return result;
    }

    public CompetitionDto saveCompetition(final CompetitionDto competitionDto) {
        log.debug("Saving competition: {}", competitionDto);
        Competition competition = competitionRepository.save(competitionMapper.mapToCompetition(competitionDto));
        CompetitionDto savedCompetitionDto = competitionMapper.mapToCompetitionDto(competition);
        log.debug("Return saved competition: {}", savedCompetitionDto);
        return savedCompetitionDto;
    }

    public CompetitionDto getCompetitionByFootballId(final Long competitionId) {
        log.info("Getting competition by id: {}", competitionId);
        Competition competition = competitionRepository.findByFootballId(competitionId).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_COMPETITION_NOT_FOUND_EXCEPTION));
        fetchCompetitionSeason(competition);
        CompetitionDto competitionDto = competitionMapper.mapToCompetitionDto(competition);
        return competitionDto;
    }

    private void fetchCompetitionSeason(final Competition competition) {
        List<CompetitionSeason> competitionSeasons = competitionSeasonRepository.findByCompetition(competition);
        competition.setCompetitionSeasonList(competitionSeasons);
    }

}
