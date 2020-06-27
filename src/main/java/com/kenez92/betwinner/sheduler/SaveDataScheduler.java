package com.kenez92.betwinner.sheduler;

import com.kenez92.betwinner.domain.fotballdata.AvailableCompetitions;
import com.kenez92.betwinner.football.client.FootballClient;
import com.kenez92.betwinner.sheduler.football.data.SaveAvailableCompetitions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class SaveDataScheduler {
    private static final int DELAY = 6; // Delay in seconds !
    private final AvailableCompetitions availableCompetitions;
    private final FootballClient footballClient;
    private final SaveAvailableCompetitions saveAvailableCompetitions;

    @Scheduled(fixedDelay = 10000)
    private void saveTables() throws InterruptedException {
        log.info("Starting saving data");
        for (Long competitionId : availableCompetitions.getAvailableCompetitionList()) {
            log.info("Saving table id: {}", competitionId);
            saveAvailableCompetitions.saveCompetitions(footballClient.getTable(competitionId));
            log.info("Saving table complete");
            TimeUnit.SECONDS.sleep(DELAY);
        }
    }
}
