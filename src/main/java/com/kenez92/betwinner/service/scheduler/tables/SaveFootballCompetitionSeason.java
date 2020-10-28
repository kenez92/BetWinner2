package com.kenez92.betwinner.service.scheduler.tables;

import com.kenez92.betwinner.domain.fotballdata.matches.FootballSeason;
import com.kenez92.betwinner.domain.table.CompetitionDto;
import com.kenez92.betwinner.domain.table.CompetitionSeasonDto;
import com.kenez92.betwinner.service.table.CompetitionSeasonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
@Service
public class SaveFootballCompetitionSeason {
    private final CompetitionSeasonService competitionSeasonService;


    public CompetitionSeasonDto saveCompetitionSeason(FootballSeason footballSeason, CompetitionDto competitionDto) {
        Long seasonId = footballSeason.getId();
        CompetitionSeasonDto competitionSeasonDto = null;
        if (competitionSeasonService.competitionSeasonExistByFootballId(seasonId)) {
            boolean update = false;
            competitionSeasonDto = competitionSeasonService.getCompetitionSeasonByFootballId(seasonId);
            if (!competitionSeasonDto.getStartDate().equals(footballSeason.getStartDate())) {
                System.out.println(!competitionSeasonDto.getStartDate().equals(footballSeason.getStartDate()));
                competitionSeasonDto.setStartDate(footballSeason.getStartDate());
                update = true;
            }
            if (!competitionSeasonDto.getEndDate().equals(footballSeason.getEndDate())) {
                competitionSeasonDto.setEndDate(footballSeason.getEndDate());
                update = true;
            }
            if (competitionSeasonDto.getWinner() == null && footballSeason.getWinner() != null) {
                competitionSeasonDto.setWinner(footballSeason.getWinner().getName());
                update = true;
            }
            if (update) {
                competitionSeasonService.updateCompetitionSeasonDto(competitionSeasonDto);
                log.info("Updating competition season : {}", competitionSeasonDto);
            }
        } else {
            CompetitionSeasonDto tmpCompetitionSeasonDto = CompetitionSeasonDto.builder()
                    .footballId(footballSeason.getId())
                    .startDate(footballSeason.getStartDate())
                    .endDate(footballSeason.getEndDate())
                    .winner(footballSeason.getWinner().getName())
                    .competition(competitionDto)
                    .currentMatchDayList(new ArrayList<>())
                    .build();
            competitionSeasonDto = competitionSeasonService.saveCompetitionSeason(tmpCompetitionSeasonDto);
            log.info("Saved new competition season: {}", competitionDto);
        }
        return competitionSeasonDto;
    }
}
