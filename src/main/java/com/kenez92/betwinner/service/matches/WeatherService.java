package com.kenez92.betwinner.service.matches;

import com.kenez92.betwinner.domain.matches.WeatherDto;
import com.kenez92.betwinner.persistence.entity.matches.Match;
import com.kenez92.betwinner.persistence.entity.matches.Weather;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.matches.WeatherMapper;
import com.kenez92.betwinner.persistence.repository.matches.MatchRepository;
import com.kenez92.betwinner.persistence.repository.matches.WeatherRepository;
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

    public List<WeatherDto> getWeathers() {
        log.info("Getting all weathers");
        List<Weather> weatherList = weatherRepository.findAll();
        List<WeatherDto> weatherDtoList = weatherMapper.mapToWeatherDtoList(weatherList);
        log.info("Return all weathers: {}", weatherDtoList);
        return weatherDtoList;
    }

    public WeatherDto getWeather(final Long weatherId) {
        log.info("Getting weather by id: {}", weatherId);
        Weather weather = weatherRepository.findById(weatherId).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_WEATHER_NOT_FOUND_EXCEPTION));
        WeatherDto weatherDto = weatherMapper.mapToWeatherDto(weather);
        log.info("Return weather: {}", weatherDto);
        return weatherDto;
    }

    public WeatherDto saveWeather(final WeatherDto weatherDto) {
        log.debug("Saving weather: {}", weatherDto);
        Weather weather = weatherRepository.save(weatherMapper.mapToWeather(weatherDto));
        WeatherDto savedWeather = weatherMapper.mapToWeatherDto(weather);
        log.debug("Return saved weather: {}", savedWeather);
        return savedWeather;
    }

    public boolean existsByDateAndCountry(final Date date, final String country) {
        boolean result = weatherRepository.existsByDateAndCountry(date, country);
        log.debug("Weather exits in repository: {}", result);
        return result;
    }

    public WeatherDto getByDateAndCountry(final Date date, final String country) {
        log.debug("Get weather by date and country: {}{}", date, country);
        Weather weather = weatherRepository.findByDateAndCountry(date, country).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_WEATHER_NOT_FOUND_EXCEPTION));
        fetchMatchList(weather);
        WeatherDto weatherDto = weatherMapper.mapToWeatherDto(weather);
        log.debug("Return weather found by date and country: {}", weatherDto);
        return weatherDto;
    }

    private void fetchMatchList(final Weather weather) {
        List<Match> matchList = matchRepository.findByWeather(weather);
        weather.setMatchList(matchList);
    }
}
