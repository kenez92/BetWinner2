package com.kenez92.betwinner.service.scheduler.table;

import com.kenez92.betwinner.domain.fotballdata.standings.FootballTable;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.persistence.entity.table.Competition;
import com.kenez92.betwinner.persistence.repository.table.CompetitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SaveCompetitionService {
    private final CompetitionRepository competitionRepository;

    public Competition process(FootballTable footballTable) {
        Long competitionFootballId = footballTable.getFootballCompetition().getId();
        if (competitionRepository.existsByFootballId(competitionFootballId)) {
            Competition competitionFromDb = competitionRepository.findByFootballId(competitionFootballId)
                    .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_COMPETITION_NOT_FOUND_EXCEPTION));
            if (competitionFromDb.getLastSavedRound().equals(footballTable.getSeason().getCurrentMatchday())) {
                return competitionFromDb;
            } else {
                competitionFromDb.setLastSavedRound(footballTable.getSeason().getCurrentMatchday());
                return competitionRepository.save(competitionFromDb);
            }
        } else {
            Competition competition = Competition.builder()
                    .footballId(footballTable.getFootballCompetition().getId())
                    .name(footballTable.getFootballCompetition().getName())
                    .lastSavedRound(footballTable.getSeason().getCurrentMatchday())
                    .build();
            return competitionRepository.save(competition);
        }
    }
}
