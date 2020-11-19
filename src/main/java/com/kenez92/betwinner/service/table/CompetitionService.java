package com.kenez92.betwinner.service.table;

import com.kenez92.betwinner.domain.table.CompetitionDto;
import com.kenez92.betwinner.persistence.entity.table.Competition;
import com.kenez92.betwinner.persistence.entity.table.CompetitionSeason;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.table.CompetitionMapper;
import com.kenez92.betwinner.persistence.repository.table.CompetitionRepository;
import com.kenez92.betwinner.persistence.repository.table.CompetitionSeasonRepository;
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

    public List<CompetitionDto> getCompetitions() {
        log.debug("Get all competitions");
        List<Competition> competitions = competitionRepository.findAll();
        List<CompetitionDto> competitionDtoList = competitionMapper.mapToCompetitionDtoList(competitions);
        log.debug("Return all competitions: {}", competitionDtoList);
        return competitionDtoList;
    }

    public CompetitionDto getCompetition(final Long id) {
        log.debug("Get competition by id: {}", id);
        Competition competition = competitionRepository.findById(id).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_COMPETITION_NOT_FOUND_EXCEPTION));
        CompetitionDto competitionDto = competitionMapper.mapToCompetitionDto(competition);
        log.debug("Return competition found by id: {}", competitionDto);
        return competitionDto;
    }

    public boolean competitionExistByFootballId(final Long competitionId) {
        boolean result = competitionRepository.existsByFootballId(competitionId);
        log.debug("Competition exits in repository: {}", result);
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
        log.debug("Getting competition by id: {}", competitionId);
        Competition competition = competitionRepository.findByFootballId(competitionId).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_COMPETITION_NOT_FOUND_EXCEPTION));
        fetchCompetitionSeason(competition);
        CompetitionDto competitionDto = competitionMapper.mapToCompetitionDto(competition);
        log.debug("Return competition: {}", competitionDto);
        return competitionDto;
    }

    public CompetitionDto getByName(final String name) {
        log.debug("Getting competition by name: {}", name);
        Competition competition = competitionRepository.findByName(name).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_COMPETITION_NOT_FOUND_EXCEPTION));
        fetchCompetitionSeason(competition);
        CompetitionDto competitionDto = competitionMapper.mapToCompetitionDto(competition);
        log.debug("Return competition: {}", competitionDto);
        return competitionDto;
    }

    private void fetchCompetitionSeason(final Competition competition) {
        List<CompetitionSeason> competitionSeasons = competitionSeasonRepository.findByCompetition(competition);
        competition.setCompetitionSeasonList(competitionSeasons);
    }
}
