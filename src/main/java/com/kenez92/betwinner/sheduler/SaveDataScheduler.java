package com.kenez92.betwinner.sheduler;

import com.kenez92.betwinner.domain.fotballdata.AvailableCompetitions;
import com.kenez92.betwinner.domain.fotballdata.match.FootballMatchById;
import com.kenez92.betwinner.domain.fotballdata.matches.FootballMatch;
import com.kenez92.betwinner.domain.fotballdata.matches.FootballMatchList;
import com.kenez92.betwinner.domain.matches.MatchDayDto;
import com.kenez92.betwinner.domain.matches.WeatherDto;
import com.kenez92.betwinner.football.client.FootballClient;
import com.kenez92.betwinner.service.coupons.CouponTypeService;
import com.kenez92.betwinner.sheduler.football.data.matches.SaveMatch;
import com.kenez92.betwinner.sheduler.football.data.matches.SaveMatchDay;
import com.kenez92.betwinner.sheduler.football.data.table.SaveAvailableCompetitions;
import com.kenez92.betwinner.sheduler.openweathermap.SaveWeather;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class SaveDataScheduler {
    private static final int DELAY = 6; // Delay in seconds !
    private final AvailableCompetitions availableCompetitions;
    private final FootballClient footballClient;
    private final SaveAvailableCompetitions saveAvailableCompetitions;
    private final SaveMatchDay saveMatchDay;
    private final SaveMatch saveMatch;
    private final SaveWeather saveWeather;
    private final CouponTypeService couponTypeService;

    //@Scheduled(cron = "0 0 1,23 * * *")
    //@Scheduled(fixedDelay = 1000)
    private void saveData() throws InterruptedException {
        saveTables();
        saveMatches();
        couponTypeService.checkCouponTypes();
    }

    private void saveTables() throws InterruptedException {
        log.info("Starting saving data");
        for (Long competitionId : availableCompetitions.getAvailableCompetitionList()) {
            log.info("Saving table id: {}", competitionId);
            saveAvailableCompetitions.saveCompetitions(footballClient.getTable(competitionId));
            log.info("Saving table complete");
            TimeUnit.SECONDS.sleep(DELAY);
        }
    }

    private void saveMatches() throws InterruptedException {
        log.info("Starting saving matches");
        FootballMatchList footballMatchList = footballClient.getMatches(LocalDate.now());
        TimeUnit.SECONDS.sleep(DELAY);
        if (footballMatchList.getCount() > 0) {
            MatchDayDto matchDayDto = saveMatchDay.saveMatchDay(footballMatchList);
            int size = footballMatchList.getCount();
            for (int i = 0; i < size; i++) {
                FootballMatch footballMatch = footballMatchList.getMatches()[i];
                if (availableCompetitions.getAvailableCompetitionList().contains(footballMatch.getCompetition().getId())) {
                    TimeUnit.SECONDS.sleep(DELAY);
                    FootballMatchById footballMatchById = footballClient.getMatch(footballMatch.getId());
                    DateTime dateTime = new DateTime(footballMatchById.getMatch().getUtcDate()).minuteOfHour().setCopy(0);
                    WeatherDto weatherDto = saveWeather.getWeather(dateTime,
                            footballMatchById.getMatch().getCompetition().getArea().getName());
                    saveMatch.saveMatch(footballMatchById, matchDayDto, weatherDto);
                }
            }
        }
        log.info("Saving matches complete");
    }
}
