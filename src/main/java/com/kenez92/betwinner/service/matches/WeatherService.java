package com.kenez92.betwinner.service.matches;

import com.kenez92.betwinner.domain.matches.Match;
import com.kenez92.betwinner.domain.matches.Weather;
import com.kenez92.betwinner.domain.matches.WeatherDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.matches.WeatherMapper;
import com.kenez92.betwinner.repository.matches.MatchRepository;
import com.kenez92.betwinner.repository.matches.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherService {
    private final WeatherMapper weatherMapper;
    private final WeatherRepository weatherRepository;
    private final MatchRepository matchRepository;

    public WeatherDto saveWeather(final WeatherDto weatherDto) {
        log.info("Saving weather: {}", weatherDto);
        Weather weather = weatherRepository.save(weatherMapper.mapToWeather(weatherDto));
        WeatherDto savedWeather = weatherMapper.mapToWeatherDto(weather);
        log.info("Return saved weather: {}", savedWeather);
        return savedWeather;
    }

    public boolean existsByDateAndCountry(final Date date, final String country) {
        boolean result = weatherRepository.existsByDateAndCountry(date, country);
        log.info("Weather exits in repository: {}", result);
        return result;
    }

    public WeatherDto getByDateAndCountry(final Date date, final String country) {
        log.info("Get weather by date and country: {}{}", date, country);
        Weather weather = weatherRepository.findByDateAndCountry(date, country).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_WEATHER_NOT_FOUND_EXCEPTION));
        fetchMatchList(weather);
        WeatherDto weatherDto = weatherMapper.mapToWeatherDto(weather);
        log.info("Return weather found by date and country: {}", weatherDto);
        return weatherDto;
    }

    private void fetchMatchList(final Weather weather) {
        List<Match> matchList = matchRepository.findByWeather(weather);
        weather.setMatchList(matchList);
    }
}
