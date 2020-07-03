package com.kenez92.betwinner.sheduler.openweathermap;

import com.kenez92.betwinner.domain.matches.WeatherDto;
import com.kenez92.betwinner.domain.openweathermap.WeatherList;
import com.kenez92.betwinner.domain.openweathermap.WeatherMain;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.openweathermap.client.WeatherClient;
import com.kenez92.betwinner.service.matches.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class SaveWeather {
    private final WeatherService weatherService;
    private final WeatherClient weatherClient;

    public WeatherDto getWeather(DateTime dateTime, String country) {
        int hour = 0;
        System.out.println(dateTime);
        int matchHour = dateTime.getHourOfDay();
        System.out.println(matchHour);
        if (matchHour >= 0 && matchHour < 3) {
            hour = 3;
        } else if (matchHour >= 3 && matchHour < 6) {
            hour = 6;
        } else if (matchHour >= 6 && matchHour < 9) {
            hour = 9;
        } else if (matchHour >= 9 && matchHour < 12) {
            hour = 12;
        } else if (matchHour >= 12 && matchHour < 15) {
            hour = 15;
        } else if (matchHour >= 15 && matchHour < 18) {
            hour = 18;
        } else if (matchHour >= 18 && matchHour < 21) {
            hour = 21;
        } else if (matchHour >= 21 && matchHour < 24) {
            hour = 0;
            dateTime = dateTime.plusDays(1);
        } else {
            throw new BetWinnerException(BetWinnerException.ERR_DATE_TIME_IS_WRONG_EXCEPTION);
        }

        DateTime fixedDateTime = dateTime.hourOfDay().setCopy(hour);
        Date fixedDate = fixedDateTime.toDate();
        System.out.println(fixedDate + "przekazane date");
        WeatherDto weatherDto;
        if (weatherService.existsByDateAndCountry(fixedDate, country)) {
            weatherDto = weatherService.getByDateAndCountry(fixedDate, country);
        } else {
            weatherDto = saveWeather(fixedDate, country);
        }
        return weatherDto;
    }

    public WeatherDto saveWeather(Date date, String country) {
        WeatherMain weatherMain = weatherClient.getForecast(country);
        WeatherList weatherList = null;
        Date fixedDate = null;
        int size = weatherMain.getWeatherList().length;
        for (int i = 0; i < size; i++) {
            fixedDate = new Date((weatherMain.getWeatherList()[i].getDate() + 3600) * 1000);
            System.out.println(fixedDate + "fixed date in loop");
            System.out.println(date.equals(fixedDate));
            if (date.equals(fixedDate)) {
                weatherList = weatherMain.getWeatherList()[i];
                break;
            }
        }
        System.out.println(weatherList);
        System.out.println(fixedDate);
        System.out.println(date);
        if (weatherList == null) {
            throw new BetWinnerException(BetWinnerException.ERR_DATE_TIME_IS_WRONG_EXCEPTION);
        }
        WeatherDto weatherDto = WeatherDto.builder()
                .date(fixedDate)
                .country(country)
                .tempFelt(weatherList.getWeatherDetails().getTempFelt())
                .tempMin(weatherList.getWeatherDetails().getTempMin())
                .tempMax(weatherList.getWeatherDetails().getTempMax())
                .pressure(weatherList.getWeatherDetails().getPressure())
                .build();
        WeatherDto savedWeatherDto = weatherService.saveWeather(weatherDto);
        log.info("Return saved weather: {}", savedWeatherDto);
        return savedWeatherDto;
    }
}
