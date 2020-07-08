package com.kenez92.betwinner.controller.matches;

import com.kenez92.betwinner.domain.matches.WeatherDto;
import com.kenez92.betwinner.service.matches.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/matches/weather")
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<WeatherDto> getWeathers() {
        log.info("Getting all weathers");
        List<WeatherDto> weatherDtoList = weatherService.getWeathers();
        log.info("Return all weaters: {}", weatherDtoList);
        return weatherDtoList;
    }

    @GetMapping(value = "/{weatherId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WeatherDto getWeather(@PathVariable Long weatherId) {
        log.info("Getting weather by id: {}", weatherId);
        WeatherDto weatherDto = weatherService.getWeather(weatherId);
        log.info("Return weather: {}", weatherDto);
        return weatherDto;
    }
}
