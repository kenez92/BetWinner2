package com.kenez92.betwinner.service.scheduler;

import com.kenez92.betwinner.domain.fotballdata.AvailableCompetitions;
import com.kenez92.betwinner.domain.fotballdata.standings.FootballTable;
import com.kenez92.betwinner.domain.table.CompetitionDto;
import com.kenez92.betwinner.domain.table.CompetitionSeasonDto;
import com.kenez92.betwinner.domain.table.CurrentMatchDayDto;
import com.kenez92.betwinner.football.client.FootballClient;
import com.kenez92.betwinner.service.scheduler.tables.SaveFootballCompetition;
import com.kenez92.betwinner.service.scheduler.tables.SaveFootballCompetitionSeason;
import com.kenez92.betwinner.service.scheduler.tables.SaveFootballCurrentMatchDay;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class SaveDataSchedulerService {
    private static final int DELAY = 6; // Delay in seconds !
    private final FootballClient footballClient;
    private final SaveFootballCompetition saveFootballCompetition;
    private final SaveFootballCompetitionSeason saveFootballCompetitionSeason;
    private final SaveFootballCurrentMatchDay saveFootballCurrentMatchDay;

    public void saveCompetitionData() throws InterruptedException {
        for (Long competitionId : AvailableCompetitions.availableCompetitionList) {
            TimeUnit.SECONDS.sleep(DELAY);
            FootballTable footballTable = footballClient.getTable(competitionId);
            CompetitionDto competitionDto = saveFootballCompetition.saveCompetition(footballTable);
            if (competitionDto != null) {
                CompetitionSeasonDto competitionSeasonDto = saveFootballCompetitionSeason
                        .saveCompetitionSeason(footballTable.getSeason(), competitionDto);
                if (competitionSeasonDto != null) {
                    CurrentMatchDayDto currentMatchDayDto = saveFootballCurrentMatchDay
                            .currentMatchDayDto(competitionSeasonDto, footballTable.getFootballStandings());
                }
            }
        }
    }
}
