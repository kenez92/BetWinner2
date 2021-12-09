package com.kenez92.betwinner.controller.matches;

import com.kenez92.betwinner.weather.WeatherDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.weather.WeatherService;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherControllerTestSuite {
    private final String url = "/v1/weathers/";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    public void shouldFetchEmptyList() throws Exception {
        //Given
        List<WeatherDto> weatherDtoList = new ArrayList<>();
        Mockito.when(weatherService.getWeathers()).thenReturn(weatherDtoList);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void testGetWeathers() throws Exception {
        //Given
        List<WeatherDto> weatherDtoList = new ArrayList<>();
        weatherDtoList.add(createWeatherDto());
        weatherDtoList.add(createWeatherDto());
        weatherDtoList.add(createWeatherDto());
        Mockito.when(weatherService.getWeathers()).thenReturn(weatherDtoList);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id", Matchers.is(387)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].country", Matchers.is("England")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].date", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].tempFelt", Matchers.is(21.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].tempMin", Matchers.is(18.1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].tempMax", Matchers.is(23.3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].pressure", Matchers.is(1101)));
    }

    @Test
    public void testgetWeather() throws Exception {
        //Given
        WeatherDto weatherDto = createWeatherDto();
        Mockito.when(weatherService.getWeather(ArgumentMatchers.anyLong())).thenReturn(weatherDto);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get(url + "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(387)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.country", Matchers.is("England")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tempFelt", Matchers.is(21.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tempMin", Matchers.is(18.1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tempMax", Matchers.is(23.3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pressure", Matchers.is(1101)));
    }

    @Test
    public void testGetWeatherShouldThrowBetWinnerExceptionWhenNotFound() throws Exception {
        //Given
        Mockito.when(weatherService.getWeather(ArgumentMatchers.anyLong()))
                .thenThrow(new BetWinnerException(BetWinnerException.ERR_WEATHER_NOT_FOUND_EXCEPTION));
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get(url + "1454")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> Assert.assertTrue(result.getResolvedException() instanceof BetWinnerException))
                .andExpect(result -> Assert.assertEquals(BetWinnerException.ERR_WEATHER_NOT_FOUND_EXCEPTION,
                        result.getResolvedException().getMessage()));
    }

    private WeatherDto createWeatherDto() {
        return WeatherDto.builder()
                .id(387L)
                .country("England")
                .date(new Date())
                .tempFelt(21.0)
                .tempMin(18.1)
                .tempMax(23.3)
                .pressure(1101)
                .build();
    }
}
