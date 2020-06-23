package com.kenez92.betwinner.service;

import com.kenez92.betwinner.domain.Competition;
import com.kenez92.betwinner.domain.CompetitionDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.CompetitionMapper;
import com.kenez92.betwinner.repository.CompetitionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CompetitionService {
    private final CompetitionRepository competitionRepository;
    private final CompetitionMapper competitionMapper;

    public boolean competitionExistById(Long competitionId) {
        boolean result = competitionRepository.existsById(competitionId);
        return result;
    }

    public CompetitionDto saveCompetition(CompetitionDto competitionDto) {
        log.debug("Saving competition: {}", competitionDto);
        Competition competition = competitionRepository.save(competitionMapper.mapToCompetition(competitionDto));
        CompetitionDto savedCompetitionDto = competitionMapper.mapToCompetitionDto(competition);
        log.debug("Return saved competition: {}", savedCompetitionDto);
        return savedCompetitionDto;
    }

    public CompetitionDto getCompetitionById(Long competitionId) {
        log.info("Getting competition by id: {}", competitionId);
        Competition competition = competitionRepository.findById(competitionId).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_COMPETITION_NOT_FOUND_EXCEPTION));
        CompetitionDto competitionDto = competitionMapper.mapToCompetitionDto(competition);
        return competitionDto;
    }

}
