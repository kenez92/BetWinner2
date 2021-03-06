package com.kenez92.betwinner.controller.scheduler;

import com.kenez92.betwinner.service.scheduler.SaveMatchesSchedulerService;
import com.kenez92.betwinner.service.scheduler.SaveTablesSchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SaveDataScheduler {
    private final SaveTablesSchedulerService saveTablesSchedulerService;
    private final SaveMatchesSchedulerService saveMatchesSchedulerService;

    private static final int DELAY_USING_FOOTBALL_DATA_API = 7;

    @Scheduled(cron = "0 0 1,23 * * *")
    public void saveData() throws InterruptedException {
        log.info("Start saving tables");
        long start = System.currentTimeMillis();
        saveTablesSchedulerService.saveTables(DELAY_USING_FOOTBALL_DATA_API);
        long end = System.currentTimeMillis();
        log.info("Finished saving tables time : {}", end - start);

        log.info("Start saving matches");
        start = System.currentTimeMillis();
        saveMatchesSchedulerService.saveMatches(DELAY_USING_FOOTBALL_DATA_API);
        end = System.currentTimeMillis();
        log.info("Finished saving matches time: {}", end - start);
    }
}
