package com.kenez92.betwinner.competitions;

import com.kenez92.betwinner.api.footballData.dto.standings.FootballTable;
import com.kenez92.betwinner.exception.BetWinnerException;
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
            if (competitionFromDb.getLastSavedRound() != null
                    && competitionFromDb.getLastSavedRound().equals(footballTable.getSeason().getCurrentMatchday())) {
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
