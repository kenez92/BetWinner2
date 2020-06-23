package com.kenez92.betwinner.sheduler.football.data;

import com.kenez92.betwinner.domain.CompetitionDto;
import com.kenez92.betwinner.domain.fotballdata.AvailableCompetitions;
import com.kenez92.betwinner.domain.fotballdata.standings.FootballTables;
import com.kenez92.betwinner.football.client.FootballClient;
import com.kenez92.betwinner.service.CompetitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class SaveAvailableCompetitions {
    private final AvailableCompetitions availableCompetitions;
    private final CompetitionService competitionService;
    private final FootballClient footballClient;

    public List<CompetitionDto> getCompetitions(long delay) throws InterruptedException {
        List<CompetitionDto> competitionDtoList = new ArrayList<>();
        List<Long> availableCompetition = availableCompetitions.getAvailableCompetitionList();
        for (Long competitionId : availableCompetition) {
            if (competitionService.competitionExistById(competitionId)) {
                CompetitionDto competitionDto = competitionService.getCompetitionById(competitionId);
                competitionDtoList.add(competitionDto);
                log.info("Added existing competition to the list: {}", competitionDto);
            } else {
                FootballTables footballTables = footballClient.getTable(competitionId);
                TimeUnit.SECONDS.sleep(delay);
                CompetitionDto competitionDto = competitionService.saveCompetition(CompetitionDto.builder()
                        .id(footballTables.getFootballCompetition().getId())
                        .name(footballTables.getFootballCompetition().getName())
                        .build());
                competitionDtoList.add(competitionDto);
                log.info("Added new competition to the list: {}", competitionDto);
            }
        }
        log.info("Return competition list: {}", competitionDtoList);
        return competitionDtoList;
    }
}
