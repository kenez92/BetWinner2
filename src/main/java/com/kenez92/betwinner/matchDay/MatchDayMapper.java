package com.kenez92.betwinner.matchDay;

import com.kenez92.betwinner.match.MatchMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {MatchMapper.class})
@Component
public interface MatchDayMapper {
    MatchDay mapToMatchDay(MatchDayDto matchDayDto);

    @Mapping(target = "matchesList", qualifiedByName = "matchDtosForMatchDayDto")
    MatchDayDto mapToMatchDayDto(final MatchDay matchDay);

    @Named("matchDayDtoForMatchDto")
    @Mapping(target = "matchesList", ignore = true)
    MatchDayDto mapToMatchDayDtoForMatchDto(final MatchDay matchDay);

    default List<MatchDayDto> mapToMatchDayDtoList(List<MatchDay> matchDays) {
        if (matchDays == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(matchDays).stream()
                .map(this::mapToMatchDayDto)
                .collect(Collectors.toList());
    }
}
