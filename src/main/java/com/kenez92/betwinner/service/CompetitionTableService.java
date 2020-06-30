package com.kenez92.betwinner.service;

import com.kenez92.betwinner.domain.*;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.CompetitionTableMapper;
import com.kenez92.betwinner.mapper.CurrentMatchDayMapper;
import com.kenez92.betwinner.repository.CompetitionTableElementRepository;
import com.kenez92.betwinner.repository.CompetitionTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompetitionTableService {
    private final CompetitionTableRepository competitionTableRepository;
    private final CompetitionTableElementRepository competitionTableElementRepository;
    private final CompetitionTableMapper competitionTableMapper;
    private final CurrentMatchDayMapper currentMatchDayMapper;

    public Boolean existsByFields(String stage, String type, CurrentMatchDayDto currentMatchDayDto) {
        CurrentMatchDay currentMatchDay = currentMatchDayMapper.mapToCurrentMatchDay(currentMatchDayDto);
        Boolean result = competitionTableRepository.existsByStageAndTypeAndCurrentMatchDay(stage, type, currentMatchDay);
        return result;
    }

    public CompetitionTableDto saveCompetitionTable(CompetitionTableDto competitionTableDto) {
        CompetitionTable competitionTable = competitionTableMapper.mapToCompetitionTable(competitionTableDto);
        CompetitionTable savedCompetitionTable = competitionTableRepository.save(competitionTable);
        return competitionTableMapper.mapToCompetitionTableDto(savedCompetitionTable);
    }

    public CompetitionTableDto getByFields(String stage, String type, CurrentMatchDayDto currentMatchDayDto) {
        CurrentMatchDay currentMatchDay = currentMatchDayMapper.mapToCurrentMatchDay(currentMatchDayDto);
        CompetitionTable competitionTable = competitionTableRepository
                .findByStageAndTypeAndCurrentMatchDay(stage, type, currentMatchDay).orElseThrow(() ->
                        new BetWinnerException(BetWinnerException.ERR_COMPETITION_TABLE_NOT_FOUND_EXCEPTION));
        fetchCompetitionTableElements(competitionTable);
        CompetitionTableDto competitionTableDto = competitionTableMapper.mapToCompetitionTableDto(competitionTable);
        return competitionTableDto;
    }

    private void fetchCompetitionTableElements(final CompetitionTable competitionTable) {
        List<CompetitionTableElement> competitionTableElements = competitionTableElementRepository
                .findByCompetitionTable(competitionTable);
        competitionTable.setCompetitionTableElements(competitionTableElements);
    }
}
