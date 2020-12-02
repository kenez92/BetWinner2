package com.kenez92.betwinner.mapper.matches;

import com.kenez92.betwinner.domain.matches.MatchDayDto;
import com.kenez92.betwinner.persistence.entity.matches.MatchDay;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
@Component
public interface MatchDayMapper {
    MatchDay mapToMatchDay(MatchDayDto matchDayDto);

    @Mapping(target = "matchesList", ignore = true)
    MatchDayDto mapToMatchDayDto(MatchDay matchDay);

    default List<MatchDayDto> mapToMatchDayDtoList(List<MatchDay> matchDays) {
        if (matchDays == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(matchDays).stream()
                .map(this::mapToMatchDayDto)
                .collect(Collectors.toList());
    }
}
