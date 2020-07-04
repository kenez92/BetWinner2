package com.kenez92.betwinner.service.competition;

import com.kenez92.betwinner.domain.table.*;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.table.CompetitionTableMapper;
import com.kenez92.betwinner.mapper.table.CurrentMatchDayMapper;
import com.kenez92.betwinner.repository.table.CompetitionTableElementRepository;
import com.kenez92.betwinner.repository.table.CompetitionTableRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompetitionTableService {
    private final CompetitionTableRepository competitionTableRepository;
    private final CompetitionTableElementRepository competitionTableElementRepository;
    private final CompetitionTableMapper competitionTableMapper;
    private final CurrentMatchDayMapper currentMatchDayMapper;

    public boolean existsByFields(final String stage, final String type, final CurrentMatchDayDto currentMatchDayDto) {
        CurrentMatchDay currentMatchDay = currentMatchDayMapper.mapToCurrentMatchDay(currentMatchDayDto);
        boolean result = competitionTableRepository.existsByStageAndTypeAndCurrentMatchDay(stage, type, currentMatchDay);
        log.info("Competition table exists in repository: {}", result);
        return result;
    }

    public CompetitionTableDto saveCompetitionTable(final CompetitionTableDto competitionTableDto) {
        log.info("Saving competition table: {}", competitionTableDto);
        CompetitionTable competitionTable = competitionTableMapper.mapToCompetitionTable(competitionTableDto);
        CompetitionTable savedCompetitionTable = competitionTableRepository.save(competitionTable);
        CompetitionTableDto savedCompetitionTableDto = competitionTableMapper.mapToCompetitionTableDto(savedCompetitionTable);
        log.info("Return saved competition table: {}", savedCompetitionTableDto);
        return savedCompetitionTableDto;
    }

    public CompetitionTableDto getByFields(final String stage, final String type, final CurrentMatchDayDto currentMatchDayDto) {
        log.info("Getting competition table by fields: {}{}{}", stage, type, currentMatchDayDto);
        CurrentMatchDay currentMatchDay = currentMatchDayMapper.mapToCurrentMatchDay(currentMatchDayDto);
        CompetitionTable competitionTable = competitionTableRepository
                .findByStageAndTypeAndCurrentMatchDay(stage, type, currentMatchDay).orElseThrow(() ->
                        new BetWinnerException(BetWinnerException.ERR_COMPETITION_TABLE_NOT_FOUND_EXCEPTION));
        fetchCompetitionTableElements(competitionTable);
        CompetitionTableDto competitionTableDto = competitionTableMapper.mapToCompetitionTableDto(competitionTable);
        log.info("Return competition table get by fields: {}", competitionTableDto);
        return competitionTableDto;
    }

    private void fetchCompetitionTableElements(final CompetitionTable competitionTable) {
        List<CompetitionTableElement> competitionTableElements = competitionTableElementRepository
                .findByCompetitionTable(competitionTable);
        competitionTable.setCompetitionTableElements(competitionTableElements);
    }
}
