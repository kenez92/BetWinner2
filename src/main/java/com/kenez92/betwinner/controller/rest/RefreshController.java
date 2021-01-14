package com.kenez92.betwinner.controller.rest;

import com.kenez92.betwinner.controller.scheduler.SaveDataScheduler;
import com.kenez92.betwinner.exception.BetWinnerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/refresh")
public class RefreshController {
    private final SaveDataScheduler saveDataScheduler;

    @GetMapping(value = "/saveDataScheduler")
    public void refreshSaveDataScheduler() {
        try {
            saveDataScheduler.saveData();
        } catch (Exception ex) {
            log.error("Exception occured : {}", ex.getMessage());
            throw new BetWinnerException(BetWinnerException.ERR_SOMETHING_WENT_WRONG_EXCEPTION);
        }
    }
}
