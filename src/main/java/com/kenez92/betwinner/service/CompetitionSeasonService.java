package com.kenez92.betwinner.service;

import com.kenez92.betwinner.domain.CompetitionSeason;
import com.kenez92.betwinner.domain.CompetitionSeasonDto;
import com.kenez92.betwinner.mapper.CompetitionSeasonMapper;
import com.kenez92.betwinner.repository.CompetitionSeasonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompetitionSeasonService {
    private final CompetitionSeasonRepository competitionSeasonRepository;
    private final CompetitionSeasonMapper competitionSeasonMapper;

    public CompetitionSeasonDto saveCompetitionSeason(CompetitionSeasonDto competitionSeasonDto) {
        CompetitionSeason competitionSeason = competitionSeasonMapper.mapToCompetitionSeason(competitionSeasonDto);
        CompetitionSeason savedCompetitionDto = competitionSeasonRepository.save(competitionSeason);
        return competitionSeasonMapper.mapToCompetitionSeasonDto(savedCompetitionDto);
    }
}
