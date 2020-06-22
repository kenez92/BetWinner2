package com.kenez92.betwinner.service;

import com.kenez92.betwinner.domain.CompetitionTable;
import com.kenez92.betwinner.domain.CompetitionTableDto;
import com.kenez92.betwinner.mapper.CompetitionTableMapper;
import com.kenez92.betwinner.repository.CompetitionTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompetitionTableService {
    private final CompetitionTableRepository competitionTableRepository;
    private final CompetitionTableMapper competitionTableMapper;

    public CompetitionTableDto saveCompetitionTable(CompetitionTableDto competitionTableDto) {
        CompetitionTable competitionTable = competitionTableMapper.mapToCompetitionTable(competitionTableDto);
        CompetitionTable savedCompetitionTable = competitionTableRepository.save(competitionTable);
        return competitionTableMapper.mapToCompetitionTableDto(savedCompetitionTable);
    }
}
