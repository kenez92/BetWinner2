package com.kenez92.betwinner.mapper.table;

import com.kenez92.betwinner.domain.table.CurrentMatchDayDto;
import com.kenez92.betwinner.persistence.entity.table.CurrentMatchDay;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
@Component
public interface CurrentMatchDayMapper {
    CurrentMatchDay mapToCurrentMatchDay(final CurrentMatchDayDto currentMatchDayDto);

    @Mapping(target = "competitionSeason", ignore = true)
    @Mapping(target = "competitionTableList", ignore = true)
    CurrentMatchDayDto mapToCurrentMatchDayDto(final CurrentMatchDay currentMatchDay);

    default List<CurrentMatchDayDto> mapToCurrentMatchDayDtoList(final List<CurrentMatchDay> currentMatchDayList) {
        return new ArrayList<>(currentMatchDayList).stream()
                .map(this::mapToCurrentMatchDayDto)
                .collect(Collectors.toList());
    }
}
