package com.kenez92.betwinner.service.scheduler.tables;

import com.kenez92.betwinner.domain.fotballdata.standings.FootballStandings;
import com.kenez92.betwinner.domain.table.CompetitionTableDto;
import com.kenez92.betwinner.domain.table.CompetitionTableElementDto;
import com.kenez92.betwinner.service.table.CompetitionTableElementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SaveCompetitionTableElement {
    private final CompetitionTableElementService competitionTableElementService;

    public void saveCompetitionTableElements(CompetitionTableDto competitionTableDto, FootballStandings footballStandings) {
        int size = footballStandings.getFootballTableElement().length;
        for (int i = 0; i < size; i++) {
            if (competitionTableElementService.existByNameAndCompetitionTable(footballStandings.getFootballTableElement()[i]
                    .getTeam().getName(), competitionTableDto)) {
                log.info("This element of the table already exists");
            } else {

                CompetitionTableElementDto tmpCompetitionTableElementDto = CompetitionTableElementDto.builder()
                        .competitionTable(competitionTableDto)
                        .name(footballStandings.getFootballTableElement()[i].getTeam().getName())
                        .position(footballStandings.getFootballTableElement()[i].getPosition())
                        .playedGames(footballStandings.getFootballTableElement()[i].getPlayedGames())
                        .won(footballStandings.getFootballTableElement()[i].getWon())
                        .draw(footballStandings.getFootballTableElement()[i].getDraw())
                        .lost(footballStandings.getFootballTableElement()[i].getLost())
                        .points(footballStandings.getFootballTableElement()[i].getPoints())
                        .goalsFor(footballStandings.getFootballTableElement()[i].getGoalsScored())
                        .goalsAgainst(footballStandings.getFootballTableElement()[i].getGoalsLost())
                        .goalDifference(footballStandings.getFootballTableElement()[i].getGoalDifference())
                        .build();
                CompetitionTableElementDto competitionTableElementDto = competitionTableElementService
                        .saveCompetitionTableElement(tmpCompetitionTableElementDto);
                log.info("Saved competition table element : {}", competitionTableElementDto);
            }
        }
    }
}
