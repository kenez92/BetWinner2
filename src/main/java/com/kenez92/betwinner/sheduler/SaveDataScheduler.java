package com.kenez92.betwinner.sheduler;

import com.kenez92.betwinner.service.scheduler.SaveMatchesSchedulerService;
import com.kenez92.betwinner.service.scheduler.SaveTablesSchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@RequiredArgsConstructor
@Component
public class SaveDataScheduler {
    private static final int DELAY = 6; // Delay in seconds !
    private final SaveTablesSchedulerService saveDataSchedulerService;
    private final SaveMatchesSchedulerService saveMatchesSchedulerService;

    @PostConstruct
    @Scheduled(cron = "0 0 1,23 * * *")
    public void saveTables() throws InterruptedException {
        saveDataSchedulerService.saveTables(DELAY);
        saveMatchesSchedulerService.saveMatches(DELAY);
    }
}
