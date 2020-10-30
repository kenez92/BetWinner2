package com.kenez92.betwinner.sheduler;

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
    private static final int DELAY = 6; // Delay in seconds !
    private final SaveTablesSchedulerService saveDataSchedulerService;
    private final SaveMatchesSchedulerService saveMatchesSchedulerService;

    @Scheduled(cron = "0 0 1,23 * * *")
    @Scheduled(fixedDelay = 1000)
    public void saveTables() throws InterruptedException {
        saveDataSchedulerService.saveTables(DELAY);
        saveMatchesSchedulerService.saveMatches(DELAY);
    }
}
