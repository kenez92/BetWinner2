package com.kenez92.betwinner.service.scheduler.weather;

import com.kenez92.betwinner.api.client.WeatherClient;
import com.kenez92.betwinner.domain.openweathermap.WeatherList;
import com.kenez92.betwinner.domain.openweathermap.WeatherMain;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.persistence.entity.weather.Weather;
import com.kenez92.betwinner.persistence.repository.matches.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class SaveWeatherSchedulerService {
    private final WeatherClient weatherClient;
    private final WeatherRepository weatherRepository;

    private final static long FIVE_DAYS_IN_MILLISECONDS = 432000000;


    public Weather process(Date date, String country) {
        Date preparedDate = prepareDate(date);
        return saveWeather(preparedDate, country);
    }

    private Date prepareDate(Date date) {
        int hour = 0;
        DateTime dateTime = new DateTime(date).minuteOfHour().setCopy(0);
        int matchHour = dateTime.getHourOfDay();
        if (matchHour >= 0 && matchHour < 3) {
            hour = 2;
        } else if (matchHour >= 3 && matchHour < 6) {
            hour = 5;
        } else if (matchHour >= 6 && matchHour < 9) {
            hour = 8;
        } else if (matchHour >= 9 && matchHour < 12) {
            hour = 11;
        } else if (matchHour >= 12 && matchHour < 15) {
            hour = 14;
        } else if (matchHour >= 15 && matchHour < 18) {
            hour = 17;
        } else if (matchHour >= 18 && matchHour < 21) {
            hour = 20;
        } else if (matchHour >= 21 && matchHour < 24) {
            hour = 23;
        } else {
            throw new BetWinnerException(BetWinnerException.ERR_DATE_TIME_IS_WRONG_EXCEPTION);
        }
        DateTime fixedDateTime = dateTime.hourOfDay().setCopy(hour);
        return fixedDateTime.toDate();
    }

    private Weather saveWeather(Date date, String country) {
        Weather weatherFromDb = getWeatherIfExists(date, country);
        long differenceOfDays = date.getTime() - new Date().getTime();
        if (differenceOfDays > FIVE_DAYS_IN_MILLISECONDS || differenceOfDays < 0) {
            return weatherFromDb;
        }
        WeatherMain weatherMain = weatherClient.getForecast(country);
        WeatherList weatherList = null;
        Date fixedDate = null;
        int size = weatherMain.getWeatherList().length;
        for (int i = 0; i < size; i++) {
            fixedDate = new Date((weatherMain.getWeatherList()[i].getDate() + 3600) * 1000);
            if (date.equals(fixedDate)) {
                weatherList = weatherMain.getWeatherList()[i];
                break;
            }
        }
        Weather weather = null;
        if (weatherList != null) {
            weather = Weather.builder()
                    .date(fixedDate)
                    .country(country)
                    .tempFelt(weatherList.getWeatherDetails().getTempFelt())
                    .tempMin(weatherList.getWeatherDetails().getTempMin())
                    .tempMax(weatherList.getWeatherDetails().getTempMax())
                    .pressure(weatherList.getWeatherDetails().getPressure())
                    .build();
        }
        if (weatherFromDb != null && weather != null) {
            if (weatherFromDb.equals(weather)) {
                return weatherFromDb;
            } else {
                weather.setId(weatherFromDb.getId());
                return weatherRepository.save(weather);
            }
        }
        return weatherRepository.save(weather);
    }

    private Weather getWeatherIfExists(Date date, String country) {
        if (weatherRepository.existsByDateAndCountry(date, country)) {
            return weatherRepository.findByDateAndCountry(date, country).orElseThrow(() ->
                    new BetWinnerException(BetWinnerException.ERR_WEATHER_NOT_FOUND_EXCEPTION));
        }
        return null;
    }
}
