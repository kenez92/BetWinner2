package com.kenez92.betwinner.service.scheduler.table;

import com.kenez92.betwinner.domain.fotballdata.standings.FootballStandings;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.persistence.entity.table.CompetitionTable;
import com.kenez92.betwinner.persistence.entity.table.CurrentMatchDay;
import com.kenez92.betwinner.persistence.repository.table.CompetitionTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SaveCompetitionTableService {
    private final CompetitionTableRepository competitionTableRepository;

    public CompetitionTable process(CurrentMatchDay currentMatchDay, FootballStandings footballStanding) {
        CompetitionTable competitionTable = CompetitionTable.builder()
                .stage(footballStanding.getStage())
                .type(footballStanding.getType())
                .currentMatchDay(currentMatchDay)
                .build();
        if (competitionTableRepository.existsByStageAndTypeAndCurrentMatchDay(competitionTable.getStage(),
                competitionTable.getType(), currentMatchDay)) {
            CompetitionTable competitionTableFromDb = competitionTableRepository.findByStageAndTypeAndCurrentMatchDay(
                    competitionTable.getStage(), competitionTable.getType(), currentMatchDay).orElseThrow(()
                    -> new BetWinnerException(BetWinnerException.ERR_COMPETITION_TABLE_NOT_FOUND_EXCEPTION));
            if (!competitionTableFromDb.equals(competitionTable)) {
                if (!competitionTableFromDb.getStage().equals(competitionTable.getStage())) {
                    competitionTableFromDb.setStage(competitionTable.getStage());
                }
                if (!competitionTableFromDb.getType().equals(competitionTable.getType())) {
                    competitionTableFromDb.setType(competitionTable.getType());
                }
                if (!competitionTableFromDb.getCurrentMatchDay().equals(competitionTable.getCurrentMatchDay())) {
                    competitionTableFromDb.setCurrentMatchDay(competitionTable.getCurrentMatchDay());
                }
                return competitionTableRepository.save(competitionTableFromDb);
            }
            return competitionTableFromDb;
        } else {
            return competitionTableRepository.save(competitionTable);
        }
    }
}
