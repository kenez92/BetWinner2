package com.kenez92.betwinner.service.scheduler.table;

import com.kenez92.betwinner.domain.fotballdata.matches.FootballSeason;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.persistence.entity.table.Competition;
import com.kenez92.betwinner.persistence.entity.table.CompetitionSeason;
import com.kenez92.betwinner.persistence.repository.table.CompetitionSeasonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SaveCompetitionSeasonService {
    private final CompetitionSeasonRepository competitionSeasonRepository;

    public CompetitionSeason process(Competition competition, FootballSeason footballSeason) {
        String winner = footballSeason.getWinner() != null ? footballSeason.getWinner().getName() : "Season still in progress";
        CompetitionSeason competitionSeason = CompetitionSeason.builder()
                .footballId(footballSeason.getId())
                .startDate(footballSeason.getStartDate())
                .endDate(footballSeason.getEndDate())
                .winner(winner)
                .competition(competition)
                .build();

        if (competitionSeasonRepository.existsByFootballId(footballSeason.getId())) {
            CompetitionSeason competitionSeasonFromDb = competitionSeasonRepository.findByFootballId(footballSeason.getId())
                    .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_COMPETITION_SEASON_NOT_FOUND_EXCEPTION));
            competitionSeasonFromDb.setCompetition(competition);
            if (competitionSeasonFromDb.equals(competitionSeason)) {
                return competitionSeasonFromDb;
            } else {
                if (!competitionSeasonFromDb.getStartDate().equals(competitionSeason.getStartDate())) {
                    competitionSeasonFromDb.setStartDate(competitionSeason.getStartDate());
                }
                if (!competitionSeasonFromDb.getEndDate().equals(competitionSeason.getEndDate())) {
                    competitionSeasonFromDb.setEndDate(competitionSeason.getEndDate());
                }
                if (!competitionSeasonFromDb.getWinner().equals(competitionSeason.getWinner())) {
                    competitionSeasonFromDb.setWinner(competitionSeason.getWinner());
                }
                return competitionSeasonRepository.save(competitionSeasonFromDb);
            }
        } else {
            return competitionSeasonRepository.save(competitionSeason);
        }
    }
}
