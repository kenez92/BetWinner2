package com.kenez92.betwinner.currentMatchDay;

import com.kenez92.betwinner.competitionSeason.CompetitionSeason;
import com.kenez92.betwinner.exception.BetWinnerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SaveCurrentMatchDayService {
    private final CurrentMatchDayRepository currentMatchDayRepository;

    public CurrentMatchDay process(CompetitionSeason competitionSeason, Integer currentMatchDayNumber) {
        CurrentMatchDay currentMatchDay = CurrentMatchDay.builder()
                .matchDay(currentMatchDayNumber)
                .competitionSeason(competitionSeason)
                .build();
        if (currentMatchDayRepository.existsByCompetitionSeasonAndMatchDay(competitionSeason, currentMatchDayNumber)) {
            CurrentMatchDay currentMatchDayFromDb = currentMatchDayRepository.findByCompetitionSeasonAndMatchDay(
                    competitionSeason, currentMatchDayNumber).orElseThrow(()
                    -> new BetWinnerException(BetWinnerException.ERR_CURRENT_MATCH_DAY_NOT_FOUND_EXCEPTION));
            currentMatchDayFromDb.setCompetitionSeason(competitionSeason);
            if (!currentMatchDayFromDb.equals(currentMatchDay)) {
                if (!currentMatchDayFromDb.getMatchDay().equals(currentMatchDay.getMatchDay())) {
                    currentMatchDayFromDb.setMatchDay(currentMatchDay.getMatchDay());
                }
                if (!currentMatchDayFromDb.getCompetitionSeason().equals(currentMatchDay.getCompetitionSeason())) {
                    currentMatchDayFromDb.setCompetitionSeason(currentMatchDay.getCompetitionSeason());
                }
                return currentMatchDayRepository.save(currentMatchDayFromDb);
            }
            return currentMatchDayFromDb;
        } else {
            return currentMatchDayRepository.save(currentMatchDay);
        }
    }
}
