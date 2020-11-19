package com.kenez92.betwinner.service.table;

import com.kenez92.betwinner.domain.table.CompetitionSeasonDto;
import com.kenez92.betwinner.persistence.entity.table.CompetitionSeason;
import com.kenez92.betwinner.persistence.entity.table.CurrentMatchDay;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.table.CompetitionSeasonMapper;
import com.kenez92.betwinner.persistence.repository.table.CompetitionSeasonRepository;
import com.kenez92.betwinner.persistence.repository.table.CurrentMatchDayRepository;
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
        log.debug("Competition season exists in repository: {}", result);
        return result;
    }

    public CompetitionSeasonDto saveCompetitionSeason(final CompetitionSeasonDto competitionSeasonDto) {
        log.debug("Saving competition season : {}", competitionSeasonDto);
        CompetitionSeason competitionSeason = competitionSeasonMapper.mapToCompetitionSeason(competitionSeasonDto);
        CompetitionSeason savedCompetitionSeason = competitionSeasonRepository.save(competitionSeason);
        CompetitionSeasonDto savedCompetitionSeasonDto = competitionSeasonMapper.mapToCompetitionSeasonDto(savedCompetitionSeason);
        log.debug("Saved competition season: {}", savedCompetitionSeasonDto);
        return savedCompetitionSeasonDto;
    }

    public CompetitionSeasonDto getCompetitionSeasonByFootballId(final Long competitionSeasonId) {
        log.debug("Getting competition season by id: {}", competitionSeasonId);
        CompetitionSeason competitionSeason = competitionSeasonRepository.findByFootballId(competitionSeasonId).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_COMPETITION_SEASON_NOT_FOUND_EXCEPTION));
        fetchCurrentMatchDayList(competitionSeason);
        CompetitionSeasonDto competitionSeasonDto = competitionSeasonMapper.mapToCompetitionSeasonDto(competitionSeason);
        log.debug("Return competition season: {}", competitionSeasonDto);
        return competitionSeasonDto;
    }

    public CompetitionSeasonDto updateCompetitionSeasonDto(final CompetitionSeasonDto competitionSeasonDto) {
        log.debug("Updating competition season id : {}", competitionSeasonDto.getId());
        if (competitionSeasonRepository.existsById(competitionSeasonDto.getId())) {
            CompetitionSeason competitionSeason = competitionSeasonRepository.save(
                    competitionSeasonMapper.mapToCompetitionSeason(competitionSeasonDto));
            fetchCurrentMatchDayList(competitionSeason);
            CompetitionSeasonDto updatedCompetitionSeasonDto =
                    competitionSeasonMapper.mapToCompetitionSeasonDto(competitionSeason);
            log.debug("Return updated competition season : {}", updatedCompetitionSeasonDto);
            return updatedCompetitionSeasonDto;
        } else {
            throw new BetWinnerException(BetWinnerException.ERR_COMPETITION_SEASON_NOT_EXISTS_EXCEPTION);
        }
    }

    public void fetchCurrentMatchDayList(final CompetitionSeason competitionSeason) {
        List<CurrentMatchDay> currentMatchDayList = currentMatchDayRepository.findByCompetitionSeason(competitionSeason);
        competitionSeason.setCurrentMatchDayList(currentMatchDayList);

    }

    public List<CompetitionSeasonDto> getCompetitionSeasons() {
        log.debug("Get all competition seasons");
        List<CompetitionSeason> competitionSeasonList = competitionSeasonRepository.findAll();
        List<CompetitionSeasonDto> competitionSeasonDtoList = competitionSeasonMapper
                .mapToCompetitionSeasonDtoList(competitionSeasonList);
        log.debug("Return all competition season: {}", competitionSeasonList);
        return competitionSeasonDtoList;
    }

    public CompetitionSeasonDto getCompetitionSeason(Long competitionSeasonId) {
        log.debug("Get competition by id: {}", competitionSeasonId);
        CompetitionSeason competitionSeason = competitionSeasonRepository.findById(competitionSeasonId).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_COMPETITION_SEASON_NOT_FOUND_EXCEPTION));
        CompetitionSeasonDto competitionSeasonDto = competitionSeasonMapper.mapToCompetitionSeasonDto(competitionSeason);
        log.debug("Return competition season found by id: {}", competitionSeasonDto);
        return competitionSeasonDto;
    }
}
