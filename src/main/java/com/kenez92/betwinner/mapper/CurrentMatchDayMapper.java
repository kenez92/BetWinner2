package com.kenez92.betwinner.mapper;

import com.kenez92.betwinner.domain.CurrentMatchDay;
import com.kenez92.betwinner.domain.CurrentMatchDayDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface CurrentMatchDayMapper {

    CurrentMatchDay mapToCurrentMatchDay(CurrentMatchDayDto currentMatchDayDto);

    CurrentMatchDayDto mapToCurrentMatchDayDto(CurrentMatchDay currentMatchDay);
}
