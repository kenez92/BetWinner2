package com.kenez92.betwinner.service;

import com.kenez92.betwinner.domain.CompetitionTable;
import com.kenez92.betwinner.domain.CompetitionTableDto;
import com.kenez92.betwinner.domain.CompetitionTableElement;
import com.kenez92.betwinner.domain.CompetitionTableElementDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.CompetitionTableElementMapper;
import com.kenez92.betwinner.mapper.CompetitionTableMapper;
import com.kenez92.betwinner.repository.CompetitionTableElementRepository;
import com.kenez92.betwinner.repository.CompetitionTableRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CompetitionTableElementService {
    private final CompetitionTableElementRepository competitionTableElementRepository;
    private final CompetitionTableRepository competitionTableRepository;
    private final CompetitionTableElementMapper competitionTableElementMapper;
    private final CompetitionTableMapper competitionTableMapper;

    public Boolean existByNameAndCompetitionTable(final String name, final CompetitionTableDto competitionTableDto) {
        CompetitionTable competitionTable = competitionTableMapper.mapToCompetitionTable(competitionTableDto);
        Boolean result = competitionTableElementRepository.existsByNameAndCompetitionTable(name, competitionTable);
        return result;
    }

    public CompetitionTableElementDto saveCompetitionTableElement(final CompetitionTableElementDto competitionTableElementDto) {
        log.info("Saving competition table element: {}", competitionTableElementDto);
        CompetitionTableElement competitionTableElement
                = competitionTableElementMapper.mapToCompetitionTableElement(competitionTableElementDto);
        CompetitionTableElement savedCompetitionTableElement =
                competitionTableElementRepository.save(competitionTableElement);
        CompetitionTableElementDto savedCompetitionTableElementDto = competitionTableElementMapper
                .mapToCompetitionTableElementDto(savedCompetitionTableElement);
        log.info("Return saved competition table element: {}", savedCompetitionTableElementDto);
        return savedCompetitionTableElementDto;
    }

    public CompetitionTableElementDto getByNameAndCompetitionTable(final String name,
                                                                   final CompetitionTableDto competitionTableDto) {
        CompetitionTable competitionTable = mapToCompetitionTable(competitionTableDto);
        log.info("Get competition table element by name and competition table: {}{}", name, competitionTable);
        CompetitionTableElement competitionTableElement = competitionTableElementRepository
                .findByNameAndCompetitionTable(name, competitionTable).orElseThrow(()
                        -> new BetWinnerException(BetWinnerException.ERR_COMPETITION_TABLE_ELEMENT_NOT_FOUND_EXCEPTION));
        CompetitionTableElementDto competitionTableElementDto = competitionTableElementMapper
                .mapToCompetitionTableElementDto(competitionTableElement);
        fetchCompetitionTable(competitionTableElement);
        log.info("Return competition table element found by name: {}", competitionTableElementDto);
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

}
