package com.kenez92.betwinner.sheduler.football.data.table;

import com.kenez92.betwinner.domain.fotballdata.standings.FootballStandings;
import com.kenez92.betwinner.domain.fotballdata.standings.FootballTable;
import com.kenez92.betwinner.domain.table.CompetitionTableDto;
import com.kenez92.betwinner.domain.table.CompetitionTableElementDto;
import com.kenez92.betwinner.service.competition.CompetitionTableElementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SaveCompetitionTableElement {
    private final CompetitionTableElementService competitionTableElementService;

    public void saveCompetitionTableElement(final CompetitionTableDto competitionTableDto,
                                            final FootballTable footballTable, final Integer currentTableNumber) {
        FootballStandings footballStandings = footballTable.getFootballStandings()[currentTableNumber];
        int size = footballStandings.getFootballTableElement().length;
        for (int i = 0; i < size; i++) {
            CompetitionTableElementDto competitionTableElementDto = CompetitionTableElementDto.builder()
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
            if (competitionTableElementService.existByNameAndCompetitionTable(competitionTableElementDto.getName(),
                    competitionTableDto)) {
                CompetitionTableElementDto tmpCompetitionTableElementDto = competitionTableElementService
                        .getByNameAndCompetitionTable(competitionTableElementDto.getName(), competitionTableDto);
                log.info("Competition table element already exists: {}", tmpCompetitionTableElementDto);
                if (!tmpCompetitionTableElementDto.equals(competitionTableElementDto)) {
                    competitionTableElementDto.setId(tmpCompetitionTableElementDto.getId());
                    competitionTableElementService.saveCompetitionTableElement(competitionTableElementDto);
                    log.info("Updating competition table element: {}", competitionTableDto);
                }
            } else {
                competitionTableElementDto = competitionTableElementService.saveCompetitionTableElement(competitionTableElementDto);
                log.info("Saved competition table element: {}", competitionTableElementDto);
            }
        }
    }
}
