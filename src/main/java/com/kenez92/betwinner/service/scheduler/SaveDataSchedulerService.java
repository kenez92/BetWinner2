package com.kenez92.betwinner.service.scheduler;

import com.kenez92.betwinner.domain.fotballdata.AvailableCompetitions;
import com.kenez92.betwinner.domain.fotballdata.standings.FootballTable;
import com.kenez92.betwinner.domain.table.CompetitionDto;
import com.kenez92.betwinner.domain.table.CompetitionSeasonDto;
import com.kenez92.betwinner.domain.table.CompetitionTableDto;
import com.kenez92.betwinner.domain.table.CurrentMatchDayDto;
import com.kenez92.betwinner.football.client.FootballClient;
import com.kenez92.betwinner.service.scheduler.tables.SaveCompetitionTable;
import com.kenez92.betwinner.service.scheduler.tables.SaveFootballCompetition;
import com.kenez92.betwinner.service.scheduler.tables.SaveFootballCompetitionSeason;
import com.kenez92.betwinner.service.scheduler.tables.SaveFootballCurrentMatchDay;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class SaveDataSchedulerService {
    private static final int DELAY = 6; // Delay in seconds !
    private final FootballClient footballClient;
    private final SaveFootballCompetition saveFootballCompetition;
    private final SaveFootballCompetitionSeason saveFootballCompetitionSeason;
    private final SaveFootballCurrentMatchDay saveFootballCurrentMatchDay;
    private final SaveCompetitionTable saveCompetitionTable;

    public void saveCompetitionData() throws InterruptedException {
        for (Long competitionId : AvailableCompetitions.availableCompetitionList) {
            CompetitionDto competitionDto = null;
            CompetitionSeasonDto competitionSeasonDto = null;
            CurrentMatchDayDto currentMatchDayDto = null;

            TimeUnit.SECONDS.sleep(DELAY);
            FootballTable footballTable = footballClient.getTable(competitionId);
            competitionDto = saveFootballCompetition.saveCompetition(footballTable);
            if (competitionDto != null) {
                competitionSeasonDto = saveFootballCompetitionSeason
                        .saveCompetitionSeason(footballTable.getSeason(), competitionDto);
            }
            if (competitionSeasonDto != null) {
                currentMatchDayDto = saveFootballCurrentMatchDay
                        .saveCurrentMatchDayDto(competitionSeasonDto, footballTable.getFootballStandings(),
                                footballTable.getSeason().getCurrentMatchday());
            }
            if (currentMatchDayDto != null) {
                List<CompetitionTableDto> competitionTableDtoList = new ArrayList<>();
                for (int i = 0; i < footballTable.getFootballStandings().length; i++) {
                    competitionTableDtoList.add(saveCompetitionTable.saveCompetitionTable(currentMatchDayDto,
                            footballTable.getFootballStandings()[i]));
                }
            }
        }
    }
}
