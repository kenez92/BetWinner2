package com.kenez92.betwinner.service;

import com.kenez92.betwinner.domain.CompetitionSeason;
import com.kenez92.betwinner.domain.CompetitionSeasonDto;
import com.kenez92.betwinner.domain.CurrentMatchDay;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.CompetitionSeasonMapper;
import com.kenez92.betwinner.repository.CompetitionSeasonRepository;
import com.kenez92.betwinner.repository.CurrentMatchDayRepository;
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

    public boolean competitionSeasonExistByFootballId(Long competitionSeasonId) {
        boolean result = competitionSeasonRepository.existsByFootballId(competitionSeasonId);
        return result;
    }

    public CompetitionSeasonDto saveCompetitionSeason(CompetitionSeasonDto competitionSeasonDto) {
        CompetitionSeason competitionSeason = competitionSeasonMapper.mapToCompetitionSeason(competitionSeasonDto);
        log.info("competition seasn ===============: {}", competitionSeason);
        CompetitionSeason savedCompetitionDto = competitionSeasonRepository.save(competitionSeason);
        return competitionSeasonMapper.mapToCompetitionSeasonDto(savedCompetitionDto);
    }

    public CompetitionSeasonDto getCompetitionSeasonByFootballId(Long competitionSeasonId) {
        log.info("Getting competition season by id: {}", competitionSeasonId);
        CompetitionSeason competitionSeason = competitionSeasonRepository.findByFootballId(competitionSeasonId).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_COMPETITION_SEASON_NOT_FOUND_EXCEPTION));
        fetchCurrentMatchDayList(competitionSeason);
        CompetitionSeasonDto competitionSeasonDto = competitionSeasonMapper.mapToCompetitionSeasonDto(competitionSeason);
        return competitionSeasonDto;
    }

    public void fetchCurrentMatchDayList(CompetitionSeason competitionSeason) {
        List<CurrentMatchDay> currentMatchDayList = currentMatchDayRepository.findByCompetitionSeason(competitionSeason);
//        for(CurrentMatchDay currentMatchDay : currentMatchDayList) {
//            currentMatchDay.getCompetitionSeason().getCompetition().setCompetitionSeasonList(new ArrayList<>());
//            currentMatchDay.setCompetitionSeason(null);
//            currentMatchDay.setCompetitionTableList(new ArrayList<>());
//        }
//        competitionSeason.getCompetition().setCompetitionSeasonList(new ArrayList<>());
        competitionSeason.setCurrentMatchDayList(currentMatchDayList);

    }
}
