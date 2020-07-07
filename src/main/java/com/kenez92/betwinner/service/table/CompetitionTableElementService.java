package com.kenez92.betwinner.service.competition;

import com.kenez92.betwinner.domain.table.CompetitionTable;
import com.kenez92.betwinner.domain.table.CompetitionTableDto;
import com.kenez92.betwinner.domain.table.CompetitionTableElement;
import com.kenez92.betwinner.domain.table.CompetitionTableElementDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.table.CompetitionTableElementMapper;
import com.kenez92.betwinner.mapper.table.CompetitionTableMapper;
import com.kenez92.betwinner.repository.table.CompetitionTableElementRepository;
import com.kenez92.betwinner.repository.table.CompetitionTableRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CompetitionTableElementService {
    private final CompetitionTableElementRepository competitionTableElementRepository;
    private final CompetitionTableRepository competitionTableRepository;
    private final CompetitionTableElementMapper competitionTableElementMapper;
    private final CompetitionTableMapper competitionTableMapper;

    public boolean existByNameAndCompetitionTable(final String name, final CompetitionTableDto competitionTableDto) {
        CompetitionTable competitionTable = competitionTableMapper.mapToCompetitionTable(competitionTableDto);
        boolean result = competitionTableElementRepository.existsByNameAndCompetitionTable(name, competitionTable);
        log.debug("Competition table exits in repository: {}", result);
        return result;
    }

    public CompetitionTableElementDto saveCompetitionTableElement(final CompetitionTableElementDto competitionTableElementDto) {
        log.debug("Saving competition table element: {}", competitionTableElementDto);
        CompetitionTableElement competitionTableElement
                = competitionTableElementMapper.mapToCompetitionTableElement(competitionTableElementDto);
        CompetitionTableElement savedCompetitionTableElement =
                competitionTableElementRepository.save(competitionTableElement);
        CompetitionTableElementDto savedCompetitionTableElementDto = competitionTableElementMapper
                .mapToCompetitionTableElementDto(savedCompetitionTableElement);
        log.debug("Return saved competition table element: {}", savedCompetitionTableElementDto);
        return savedCompetitionTableElementDto;
    }

    public CompetitionTableElementDto getByNameAndCompetitionTable(final String name,
                                                                   final CompetitionTableDto competitionTableDto) {
        CompetitionTable competitionTable = mapToCompetitionTable(competitionTableDto);
        log.debug("Get competition table element by name and competition table: {}{}", name, competitionTable);
        CompetitionTableElement competitionTableElement = competitionTableElementRepository
                .findByNameAndCompetitionTable(name, competitionTable).orElseThrow(()
                        -> new BetWinnerException(BetWinnerException.ERR_COMPETITION_TABLE_ELEMENT_NOT_FOUND_EXCEPTION));
        CompetitionTableElementDto competitionTableElementDto = competitionTableElementMapper
                .mapToCompetitionTableElementDto(competitionTableElement);
        fetchCompetitionTable(competitionTableElement);
        log.debug("Return competition table element found by name: {}", competitionTableElementDto);
        return competitionTableElementDto;
    }

    private void fetchCompetitionTable(final CompetitionTableElement competitionTableElement) {
        CompetitionTable competitionTable = competitionTableRepository.findById(competitionTableElement.getCompetitionTable().getId())
                .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_COMPETITION_TABLE_NOT_FOUND_EXCEPTION));
        competitionTableElement.setCompetitionTable(competitionTable);
    }

    private CompetitionTable mapToCompetitionTable(final CompetitionTableDto competitionTableDto) {
        return competitionTableMapper.mapToCompetitionTable(competitionTableDto);
    }

    public List<CompetitionTableElementDto> findByName(final String name) {
        List<CompetitionTableElement> competitionTableElementList = competitionTableElementRepository.findByName(name);
        List<CompetitionTableElementDto> competitionTableElementDtoList
                = competitionTableElementMapper.mapToCompetitionTableElementDtoList(competitionTableElementList);
        return competitionTableElementDtoList;
    }

    public List<CompetitionTableElementDto> getCompetitionTableElements() {
        log.debug("Getting all competition table elements");
        List<CompetitionTableElement> competitionTableElements = competitionTableElementRepository.findAll();
        List<CompetitionTableElementDto> competitionTableElementDtoList = competitionTableElementMapper
                .mapToCompetitionTableElementDtoList(competitionTableElements);
        log.debug("Return all competition table elements: {}", competitionTableElementDtoList);
        return competitionTableElementDtoList;
    }

    public CompetitionTableElementDto getCompetitionTableElement(final Long competitionTableElementId) {
        log.debug("Getting competition table element by id: {}", competitionTableElementId);
        CompetitionTableElement competitionTableElement = competitionTableElementRepository.findById(competitionTableElementId)
                .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_COMPETITION_TABLE_ELEMENT_NOT_FOUND_EXCEPTION));
        CompetitionTableElementDto competitionTableElementDto = competitionTableElementMapper
                .mapToCompetitionTableElementDto(competitionTableElement);
        log.debug("Return competition table element: {}", competitionTableElement);
        return competitionTableElementDto;
    }
}
