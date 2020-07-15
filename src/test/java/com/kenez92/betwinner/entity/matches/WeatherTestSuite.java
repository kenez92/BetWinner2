package com.kenez92.betwinner.entity.matches;

import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.repository.matches.WeatherRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherTestSuite {
    private final static String COUNTRY = "Poland";
    @Autowired
    private WeatherRepository weatherRepository;

    @Test
    public void testFindWeatherById() {
        //Given
        Weather weather = weatherRepository.save(createWeather());
        //When
        Weather dbWeather = weatherRepository.findById(weather.getId()).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_WEATHER_NOT_FOUND_EXCEPTION));
        //Then
        try {
            Assert.assertEquals(weather.getId(), dbWeather.getId());
            Assert.assertEquals(COUNTRY, dbWeather.getCountry());
            Assert.assertTrue(weather.getDate().getTime() - dbWeather.getDate().getTime() < 1000);
            Assert.assertEquals(21.0, dbWeather.getTempFelt(), 0.001);
            Assert.assertEquals(18.1, dbWeather.getTempMin(), 0.001);
            Assert.assertEquals(23.3, dbWeather.getTempMax(), 0.001);
            Assert.assertEquals(1101, dbWeather.getPressure(), 0.001);
        } finally {
            weatherRepository.deleteById(weather.getId());
        }
    }

    @Test
    public void testFindByIdShouldBeEmpty() {
        //Given
        //When
        Optional<Weather> weather = weatherRepository.findById(-1L);
        //Then
        Assert.assertFalse(weather.isPresent());
    }

    @Test
    public void testFindAll() {
        //Given
        Weather weather = weatherRepository.save(createWeather());
        //When
        List<Weather> weatherList = weatherRepository.findAll();
        //Then
        try {
            Assert.assertTrue(weatherList.size() >= 1);
        } finally {
            weatherRepository.deleteById(weather.getId());
        }
    }

    @Test
    public void testSaveWeather() {
        //Given
        Weather weather = createWeather();
        //When
        Weather dbWeather = weatherRepository.save(weather);
        //Then
        try {
            Assert.assertEquals(weather.getId(), dbWeather.getId());
            Assert.assertEquals(COUNTRY, dbWeather.getCountry());
            Assert.assertTrue(weather.getDate().getTime() - dbWeather.getDate().getTime() < 1000);
            Assert.assertEquals(21.0, dbWeather.getTempFelt(), 0.001);
            Assert.assertEquals(18.1, dbWeather.getTempMin(), 0.001);
            Assert.assertEquals(23.3, dbWeather.getTempMax(), 0.001);
            Assert.assertEquals(1101, dbWeather.getPressure(), 0.001);
        } finally {
            weatherRepository.deleteById(dbWeather.getId());
        }
    }

    @Test
    public void testUpdateWeather() {
        //Given
        Weather weather = weatherRepository.save(createWeather());
        Long weatherId = weather.getId();
        Weather updatedWeather = Weather.builder()
                .id(weatherId)
                .country(COUNTRY)
                .date(new Date())
                .tempFelt(22.0)
                .tempMin(13.1)
                .tempMax(23.7)
                .pressure(1120)
                .build();
        //When
        Weather dbWeather = weatherRepository.save(updatedWeather);
        //Then
        try {
            Assert.assertEquals(weatherId, dbWeather.getId());
            Assert.assertEquals(COUNTRY, dbWeather.getCountry());
            Assert.assertTrue(weather.getDate().getTime() - dbWeather.getDate().getTime() < 1000);
            Assert.assertEquals(22.0, dbWeather.getTempFelt(), 0.001);
            Assert.assertEquals(13.1, dbWeather.getTempMin(), 0.001);
            Assert.assertEquals(23.7, dbWeather.getTempMax(), 0.001);
            Assert.assertEquals(1120, dbWeather.getPressure(), 0.001);
        } finally {
            weatherRepository.deleteById(dbWeather.getId());
        }
    }

    private Weather createWeather() {
        return Weather.builder()
                .country(COUNTRY)
                .date(new Date())
                .tempFelt(21.0)
                .tempMin(18.1)
                .tempMax(23.3)
                .pressure(1101)
                .build();
    }
}