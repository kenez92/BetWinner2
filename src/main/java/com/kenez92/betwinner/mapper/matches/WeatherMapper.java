package com.kenez92.betwinner.mapper.matches;

import com.kenez92.betwinner.domain.weather.WeatherDto;
import com.kenez92.betwinner.persistence.entity.weather.Weather;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
@Component
public interface WeatherMapper {
    Weather mapToWeather(WeatherDto weatherDto);

    @Mapping(target = "matchList", ignore = true)
    WeatherDto mapToWeatherDto(final Weather weather);

    default List<WeatherDto> mapToWeatherDtoList(List<Weather> weatherList) {
        return new ArrayList<>(weatherList).stream()
                .map(this::mapToWeatherDto)
                .collect(Collectors.toList());
    }
}
