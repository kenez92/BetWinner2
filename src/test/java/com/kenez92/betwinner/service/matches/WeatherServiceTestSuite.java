package com.kenez92.betwinner.service.matches;

import com.kenez92.betwinner.domain.matches.WeatherDto;
import com.kenez92.betwinner.entity.matches.Weather;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.repository.matches.WeatherRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class WeatherServiceTestSuite {
    @Autowired
    private WeatherService weatherService;

    @MockBean
    private WeatherRepository weatherRepository;

    @Test
    public void getWeathers() {
        //Given
        List<Weather> weatherList = new ArrayList<>();
        weatherList.add(createWeather());
        weatherList.add(createWeather());
        Mockito.when(weatherRepository.findAll()).thenReturn(weatherList);
        //When
        List<WeatherDto> weatherDtoList = weatherService.getWeathers();
        //Then
        Assert.assertEquals(weatherList.size(), weatherDtoList.size());
    }

    @Test
    public void getWeather() {
        //Given
        Weather weather = createWeather();
        Mockito.when(weatherRepository.findById(ArgumentMatchers.anyLong())).thenReturn(java.util.Optional.ofNullable(weather));
        //When
        WeatherDto weatherDto = weatherService.getWeather(2323L);
        //Then
        Assert.assertEquals(weather.getId(), weatherDto.getId());
        Assert.assertEquals(weather.getCountry(), weatherDto.getCountry());
        Assert.assertEquals(weather.getDate(), weatherDto.getDate());
        Assert.assertEquals(weather.getTempFelt(), weatherDto.getTempFelt());
        Assert.assertEquals(weather.getTempMax(), weatherDto.getTempMax());
        Assert.assertEquals(weather.getTempMin(), weatherDto.getTempMin());
        Assert.assertEquals(weather.getPressure(), weatherDto.getPressure());
        Assert.assertEquals(weather.getMatchList().size(), weatherDto.getMatchList().size());
    }

    @Test
    public void testGetWeatherShouldThrowBetWinnerExceptionWhenWeatherNotFound() {
        //Given
        Mockito.when(weatherRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        //When
        //Then
        Assertions.assertThrows(BetWinnerException.class, () -> weatherService.getWeather(234234L));
    }

    @Test
    public void testCreateWeather() {
        //Given
        Weather weather = createWeather();
        Mockito.when(weatherRepository.save(ArgumentMatchers.any(Weather.class))).thenReturn(weather);
        //When
        WeatherDto weatherDto = weatherService.saveWeather(new WeatherDto());
        //Then
        Assert.assertEquals(weather.getId(), weatherDto.getId());
        Assert.assertEquals(weather.getCountry(), weatherDto.getCountry());
        Assert.assertEquals(weather.getDate(), weatherDto.getDate());
        Assert.assertEquals(weather.getTempFelt(), weatherDto.getTempFelt());
        Assert.assertEquals(weather.getTempMax(), weatherDto.getTempMax());
        Assert.assertEquals(weather.getTempMin(), weatherDto.getTempMin());
        Assert.assertEquals(weather.getPressure(), weatherDto.getPressure());
        Assert.assertEquals(weather.getMatchList().size(), weatherDto.getMatchList().size());
    }

    @Test
    public void testExistsByDateAndCountry() {
        //Given
        Mockito.when(weatherRepository.existsByDateAndCountry(ArgumentMatchers.any(Date.class), ArgumentMatchers.anyString()))
                .thenReturn(true);
        //When
        boolean result = weatherService.existsByDateAndCountry(new Date(), "Test string");
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testGetByDateAndCountry() {
        //Given
        Weather weather = createWeather();
        Mockito.when(weatherRepository.findByDateAndCountry(ArgumentMatchers.any(Date.class), ArgumentMatchers.anyString()))
                .thenReturn(Optional.ofNullable(weather));
        //When
        WeatherDto weatherDto = weatherService.getByDateAndCountry(new Date(), "Test string");
        //Then
        Assert.assertEquals(weather.getId(), weatherDto.getId());
        Assert.assertEquals(weather.getCountry(), weatherDto.getCountry());
        Assert.assertEquals(weather.getDate(), weatherDto.getDate());
        Assert.assertEquals(weather.getTempFelt(), weatherDto.getTempFelt());
        Assert.assertEquals(weather.getTempMax(), weatherDto.getTempMax());
        Assert.assertEquals(weather.getTempMin(), weatherDto.getTempMin());
        Assert.assertEquals(weather.getPressure(), weatherDto.getPressure());
        Assert.assertEquals(weather.getMatchList().size(), weatherDto.getMatchList().size());
    }

    @Test
    public void testGetByDateAndCountryShouldThrowBetWinnerExceptionWhenWeatherNotFound() {
        //Given
        Mockito.when(weatherRepository.findByDateAndCountry(ArgumentMatchers.any(Date.class), ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());
        //When
        Assertions.assertThrows(BetWinnerException.class, () -> weatherService.getByDateAndCountry(new Date(),
                new String("new string")));
    }

    private Weather createWeather() {
        return Weather.builder()
                .id(3232L)
                .country("Test country")
                .date(new Date())
                .tempFelt(22.0)
                .tempMin(21.1)
                .tempMax(22.3)
                .pressure(1133)
                .matchList(new ArrayList<>())
                .build();
    }
}