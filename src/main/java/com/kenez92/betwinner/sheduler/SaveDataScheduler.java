package com.kenez92.betwinner.sheduler;

import com.kenez92.betwinner.service.scheduler.SaveDataSchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SaveDataScheduler {
    private final SaveDataSchedulerService saveDataSchedulerService;

    @Scheduled(cron = "0 0 1,23 * * *")
    @Scheduled(fixedDelay = 1000)
    public void saveData() throws InterruptedException {
        saveDataSchedulerService.saveCompetitionData();
    }
}
