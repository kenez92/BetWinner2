package com.kenez92.betwinner.sheduler;

import com.kenez92.betwinner.domain.fotballdata.AvailableCompetitions;
import com.kenez92.betwinner.domain.fotballdata.matches.FootballMatchList;
import com.kenez92.betwinner.football.client.FootballClient;
import com.kenez92.betwinner.service.MatchService;
import com.kenez92.betwinner.sheduler.football.data.SaveAvailableCompetitions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class SaveDataScheduler {
    private final AvailableCompetitions availableCompetitions;
    private final FootballClient footballClient;
    private final MatchService matchService;
    private final SaveAvailableCompetitions saveAvailableCompetitions;


    private List<Long> getAllMatchId() {
        LocalDate localDate = LocalDate.now();
        FootballMatchList footballMatchList = footballClient.getMatches(localDate);
        List<Long> matchIdList = new ArrayList<>();
        Long matchId;
        int size = footballMatchList.getCount();
        for (int i = 0; i < size; i++) {
            matchId = footballMatchList.getMatches()[i].getId();
            matchIdList.add(matchId);
        }
        return matchIdList;
    }

    @Scheduled(fixedDelay = 10000)
    private void saveCompetition() throws InterruptedException {
        log.info("Saving competitions");
        saveAvailableCompetitions.getCompetitions(6); // Delay in seconds !
        log.info("Saving competitions complete");
    }
}
