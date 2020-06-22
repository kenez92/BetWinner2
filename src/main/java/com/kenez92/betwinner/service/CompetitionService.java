package com.kenez92.betwinner.service;

import com.kenez92.betwinner.domain.Competition;
import com.kenez92.betwinner.domain.CompetitionDto;
import com.kenez92.betwinner.mapper.CompetitionMapper;
import com.kenez92.betwinner.repository.CompetitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CompetitionService {
    private final CompetitionRepository competitionRepository;
    private final CompetitionMapper competitionMapper;

    public CompetitionDto saveCompetition(CompetitionDto competitionDto) {
        Competition competition = competitionMapper.mapToCompetition(competitionDto);
        Competition savedCompetition = competitionRepository.save(competition);
        return competitionMapper.mapToCompetitionDto(savedCompetition);

    }
}
