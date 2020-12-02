package com.kenez92.betwinner.service.table;

import com.kenez92.betwinner.domain.table.CompetitionTableDto;
import com.kenez92.betwinner.domain.table.CurrentMatchDayDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.table.CompetitionTableMapper;
import com.kenez92.betwinner.mapper.table.CurrentMatchDayMapper;
import com.kenez92.betwinner.persistence.entity.table.CompetitionTable;
import com.kenez92.betwinner.persistence.entity.table.CompetitionTableElement;
import com.kenez92.betwinner.persistence.entity.table.CurrentMatchDay;
import com.kenez92.betwinner.persistence.repository.table.CompetitionTableElementRepository;
import com.kenez92.betwinner.persistence.repository.table.CompetitionTableRepository;
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

    public List<CompetitionTableDto> getCompetitionTables() {
        log.debug("Getting all competition tables");
        List<CompetitionTable> competitionTableList = competitionTableRepository.findAll();
        List<CompetitionTableDto> competitionTableDtoList = competitionTableMapper
                .mapToCompetitionTableDtoList(competitionTableList);
        log.debug("Return all competition tables");
        return competitionTableDtoList;
    }

    public CompetitionTableDto getCompetitionTable(final Long competitionTableId) {
        log.debug("Getting competition table by id: {}", competitionTableId);
        CompetitionTable competitionTable = competitionTableRepository.findById(competitionTableId).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_COMPETITION_TABLE_NOT_FOUND_EXCEPTION));
        CompetitionTableDto competitionTableDto = competitionTableMapper.mapToCompetitionTableDto(competitionTable);
        log.debug("Return competition table: {}", competitionTableDto);
        return competitionTableDto;
    }

    public boolean existsByFields(final String stage, final String type, final CurrentMatchDayDto currentMatchDayDto) {
        CurrentMatchDay currentMatchDay = currentMatchDayMapper.mapToCurrentMatchDay(currentMatchDayDto);
        boolean result = competitionTableRepository.existsByStageAndTypeAndCurrentMatchDay(stage, type, currentMatchDay);
        log.debug("Competition table exists in repository: {}", result);
        return result;
    }

    public CompetitionTableDto saveCompetitionTable(final CompetitionTableDto competitionTableDto) {
        log.debug("Saving competition table: {}", competitionTableDto);
        CompetitionTable competitionTable = competitionTableMapper.mapToCompetitionTable(competitionTableDto);
        CompetitionTable savedCompetitionTable = competitionTableRepository.save(competitionTable);
        CompetitionTableDto savedCompetitionTableDto = competitionTableMapper.mapToCompetitionTableDto(savedCompetitionTable);
        log.debug("Return saved competition table: {}", savedCompetitionTableDto);
        return savedCompetitionTableDto;
    }

    public CompetitionTableDto getByFields(final String stage, final String type, final CurrentMatchDayDto currentMatchDayDto) {
        log.debug("Getting competition table by fields: {}{}{}", stage, type, currentMatchDayDto);
        CurrentMatchDay currentMatchDay = currentMatchDayMapper.mapToCurrentMatchDay(currentMatchDayDto);
        CompetitionTable competitionTable = competitionTableRepository
                .findByStageAndTypeAndCurrentMatchDay(stage, type, currentMatchDay).orElseThrow(() ->
                        new BetWinnerException(BetWinnerException.ERR_COMPETITION_TABLE_NOT_FOUND_EXCEPTION));
        fetchCompetitionTableElements(competitionTable);
        CompetitionTableDto competitionTableDto = competitionTableMapper.mapToCompetitionTableDto(competitionTable);
        log.debug("Return competition table get by fields: {}", competitionTableDto);
        return competitionTableDto;
    }

    private void fetchCompetitionTableElements(final CompetitionTable competitionTable) {
        List<CompetitionTableElement> competitionTableElements = competitionTableElementRepository
                .findByCompetitionTable(competitionTable);
        competitionTable.setCompetitionTableElements(competitionTableElements);
    }
}
