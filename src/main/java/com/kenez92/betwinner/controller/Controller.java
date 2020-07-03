package com.kenez92.betwinner.controller;

import com.kenez92.betwinner.domain.fotballdata.matches.FootballMatchList;
import com.kenez92.betwinner.domain.fotballdata.standings.FootballTable;
import com.kenez92.betwinner.domain.openweathermap.WeatherMain;
import com.kenez92.betwinner.football.client.FootballClient;
import com.kenez92.betwinner.openweathermap.client.WeatherClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class Controller {
    private final FootballClient footballClient;
    private final WeatherClient weatherClient;

    @GetMapping("/getMatches")
    public FootballMatchList getMatches() {
        return footballClient.getMatches(LocalDate.now());
    }

    @GetMapping("/getTables")
    public FootballTable getTables() {
        return footballClient.getTable(2002L);
    }

    @GetMapping("/getWeather")
    public WeatherMain getWeather() {
        return weatherClient.getForecast("England");
    }
}
