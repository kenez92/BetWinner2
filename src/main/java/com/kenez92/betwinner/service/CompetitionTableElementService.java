package com.kenez92.betwinner.service;

import com.kenez92.betwinner.domain.CompetitionTableElement;
import com.kenez92.betwinner.domain.CompetitionTableElementDto;
import com.kenez92.betwinner.mapper.CompetitionTableElementMapper;
import com.kenez92.betwinner.repository.CompetitionTableElementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CompetitionTableElementService {
    private final CompetitionTableElementRepository competitionTableElementRepository;
    private final CompetitionTableElementMapper competitionTableElementMapper;

    public CompetitionTableElementDto saveCompetitionTableElement(CompetitionTableElementDto competitionTableElementDto) {
        CompetitionTableElement competitionTableElement
                = competitionTableElementMapper.mapToCompetitionTableElement(competitionTableElementDto);
        CompetitionTableElement savedCompetitionTableElement =
                competitionTableElementRepository.save(competitionTableElement);
        return competitionTableElementMapper.mapToCompetitionTableElementDto(savedCompetitionTableElement);
    }
}
