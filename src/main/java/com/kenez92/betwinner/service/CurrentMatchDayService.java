package com.kenez92.betwinner.service;

import com.kenez92.betwinner.domain.CurrentMatchDay;
import com.kenez92.betwinner.domain.CurrentMatchDayDto;
import com.kenez92.betwinner.mapper.CurrentMatchDayMapper;
import com.kenez92.betwinner.repository.CurrentMatchDayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CurrentMatchDayService {
    private final CurrentMatchDayRepository currentMatchDayRepository;
    private final CurrentMatchDayMapper currentMatchDayMapper;

    public CurrentMatchDayDto saveCurrentMatchDay(CurrentMatchDayDto currentMatchDayDto) {
        CurrentMatchDay currentMatchDay = currentMatchDayRepository.save(currentMatchDayMapper.mapToCurrentMatchDay(currentMatchDayDto));
        return currentMatchDayMapper.mapToCurrentMatchDayDto(currentMatchDay);
    }
}
