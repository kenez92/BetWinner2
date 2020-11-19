package com.kenez92.betwinner.api.client;

import com.kenez92.betwinner.domain.openweathermap.WeatherMain;
import com.kenez92.betwinner.exception.BetWinnerException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@Component
public class WeatherClient {
    private final RestTemplate restTemplate;
    @Value("${api.openweathermap.org}")
    private String apiKey;
    private final static String ENDPOINT = "https://api.openweathermap.org/data/2.5/";
    private final static String UNITS_METRIC = "metric";

    public WeatherMain getForecast(final String country) {
        URI url = UriComponentsBuilder.fromHttpUrl(ENDPOINT + "forecast?")
                .queryParam("q", country)
                .queryParam("units", UNITS_METRIC)
                .queryParam("APPID", apiKey)
                .build().encode().toUri();
        WeatherMain weatherMain = restTemplate.getForObject(url, WeatherMain.class);
        if (weatherMain != null) {
            return weatherMain;
        } else {
            throw new BetWinnerException(BetWinnerException.ERR_WEATHER_NULL_EXCEPTION);
        }
    }
}
