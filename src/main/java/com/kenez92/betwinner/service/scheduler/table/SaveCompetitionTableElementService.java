package com.kenez92.betwinner.service.scheduler.table;

import com.kenez92.betwinner.domain.fotballdata.standings.FootballTableElement;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.persistence.entity.table.CompetitionTable;
import com.kenez92.betwinner.persistence.entity.table.CompetitionTableElement;
import com.kenez92.betwinner.persistence.repository.table.CompetitionTableElementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SaveCompetitionTableElementService {
    private final CompetitionTableElementRepository competitionTableElementRepository;

    public void process(CompetitionTable competitionTable, FootballTableElement[] footballTableElements) {
        for (FootballTableElement footballTableElement : footballTableElements) {
            String name = footballTableElement.getTeam() != null ? footballTableElement.getTeam().getName() : "Unknown";
            CompetitionTableElement competitionTableElement = CompetitionTableElement.builder()
                    .competitionTable(competitionTable)
                    .name(name)
                    .position(footballTableElement.getPosition())
                    .playedGames(footballTableElement.getPlayedGames())
                    .won(footballTableElement.getWon())
                    .draw(footballTableElement.getDraw())
                    .lost(footballTableElement.getLost())
                    .points(footballTableElement.getPoints())
                    .goalsFor(footballTableElement.getGoalsScored())
                    .goalsAgainst(footballTableElement.getGoalsLost())
                    .goalDifference(footballTableElement.getGoalDifference())
                    .build();
            if (competitionTableElementRepository.existsByNameAndCompetitionTable(name, competitionTable)) {
                try {
                    CompetitionTableElement competitionTableElementFromDb = competitionTableElementRepository
                            .findByNameAndCompetitionTable(name, competitionTable).orElseThrow(()
                                    -> new BetWinnerException(BetWinnerException.ERR_COMPETITION_TABLE_ELEMENT_NOT_FOUND_EXCEPTION));
                if (!competitionTableElementFromDb.equals(competitionTableElement)) {
                    competitionTableElement.setId(competitionTableElementFromDb.getId());
                    competitionTableElementRepository.save(competitionTableElement);
                }
                } catch (Exception ex) {
                    System.out.println("Error dla : " + name + " - id:" + competitionTable.getId());
                }

            } else {
                competitionTableElementRepository.save(competitionTableElement);
            }
        }
    }
}
