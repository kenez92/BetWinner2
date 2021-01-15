package com.kenez92.betwinner.mapper.matches;

import com.kenez92.betwinner.domain.matches.MatchDto;
import com.kenez92.betwinner.mapper.coupons.CouponTypeMapper;
import com.kenez92.betwinner.persistence.entity.matches.Match;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper(uses = {MatchDayMapper.class, WeatherMapper.class, CouponTypeMapper.class})
public interface MatchMapper {
    Match mapToMatch(MatchDto matchDto);

    @Mapping(target = "weather", qualifiedByName = "weatherDtoForMatchDto")
    @Mapping(target = "matchDay", qualifiedByName = "matchDayDtoForMatchDto")
    @Mapping(target = "couponTypeList", qualifiedByName = "couponTypeDtosForMatchDto")
    MatchDto mapToMatchDto(final Match match);

    @Mapping(target = "weather", qualifiedByName = "weatherDtoForMatchDto")
    @Mapping(target = "couponTypeList", qualifiedByName = "couponTypeDtosForMatchDto")
    @Mapping(target = "matchDay", ignore = true)
    MatchDto mapToMatchDtoForMatchDayDto(final Match match);

    @Mapping(target = "weather", ignore = true)
    @Mapping(target = "matchDay", qualifiedByName = "matchDayDtoForMatchDto")
    @Mapping(target = "couponTypeList", qualifiedByName = "couponTypeDtosForMatchDto")
    MatchDto mapToMatchDtoForWeatherDto(final Match match);

    @Named("matchDtoForCouponTypeDto")
    @Mapping(target = "weather", qualifiedByName = "weatherDtoForMatchDto")
    @Mapping(target = "matchDay", qualifiedByName = "matchDayDtoForMatchDto")
    @Mapping(target = "couponTypeList", ignore = true)
    MatchDto mapToMatchDtoForCouponTypeDto(final Match match);

    default List<MatchDto> mapToMatchDtoList(final List<Match> matchList) {
        if (matchList == null) {
            return new ArrayList<>();
        } else {
            return matchList.stream()
                    .map(this::mapToMatchDto)
                    .collect(Collectors.toList());
        }
    }

    @Named(value = "matchDtosForMatchDayDto")
    default List<MatchDto> mapToMatchDtosForMatchDayDto(List<Match> matchList) {
        if (matchList == null) {
            return new ArrayList<>();
        } else {
            return matchList.stream()
                    .map(this::mapToMatchDtoForMatchDayDto)
                    .collect(Collectors.toList());
        }
    }

    @Named(value = "matchDtosForWeatherDto")
    default List<MatchDto> mapToMatchDtosForWeatherDto(List<Match> matchList) {
        if (matchList == null) {
            return new ArrayList<>();
        } else {
            return matchList.stream()
                    .map(this::mapToMatchDtoForWeatherDto)
                    .collect(Collectors.toList());
        }
    }
}
