package com.kenez92.betwinner.mapper.table;

import com.kenez92.betwinner.domain.table.CompetitionDto;
import com.kenez92.betwinner.domain.table.CompetitionSeasonDto;
import com.kenez92.betwinner.entity.table.Competition;
import com.kenez92.betwinner.entity.table.CompetitionSeason;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompetitionMapper {

    public Competition mapToCompetition(final CompetitionDto competitionDto) {
        return Competition.builder()
                .id(competitionDto.getId())
                .footballId(competitionDto.getFootballId())
                .name(competitionDto.getName())
                .lastSavedRound(competitionDto.getLastSavedRound())
                .competitionSeasonList(competitionSeasonList(competitionDto))
                .build();
    }

    public CompetitionDto mapToCompetitionDto(final Competition competition) {
        return CompetitionDto.builder()
                .id(competition.getId())
                .footballId(competition.getFootballId())
                .name(competition.getName())
                .lastSavedRound(competition.getLastSavedRound())
                .competitionSeasonList(competitionSeasonDtoList(competition))
                .build();
    }

    public List<CompetitionDto> mapToCompetitionDtoList(final List<Competition> competitionList) {
        return new ArrayList<>(competitionList).stream()
                .map(this::mapToCompetitionDto)
                .collect(Collectors.toList());
    }

    private List<CompetitionSeason> competitionSeasonList(final CompetitionDto competitionDto) {
        List<CompetitionSeason> competitionSeasons = new ArrayList<>();
        if (competitionDto.getCompetitionSeasonList() != null) {
            for (CompetitionSeasonDto competitionSeasonDto : competitionDto.getCompetitionSeasonList()) {
                competitionSeasons.add(CompetitionSeason.builder()
                        .id(competitionSeasonDto.getId())
                        .footballId(competitionSeasonDto.getFootballId())
                        .startDate(competitionSeasonDto.getStartDate())
                        .endDate(competitionSeasonDto.getEndDate())
                        .winner(competitionSeasonDto.getWinner())
                        .currentMatchDayList(new ArrayList<>())
                        .build());
            }
        }
        return competitionSeasons;
    }

    private List<CompetitionSeasonDto> competitionSeasonDtoList(final Competition competition) {
        List<CompetitionSeasonDto> competitionSeasons = new ArrayList<>();
        for (CompetitionSeason competitionSeason : competition.getCompetitionSeasonList()) {
            competitionSeasons.add(CompetitionSeasonDto.builder()
                    .id(competitionSeason.getId())
                    .footballId(competitionSeason.getFootballId())
                    .startDate(competitionSeason.getStartDate())
                    .endDate(competitionSeason.getEndDate())
                    .winner(competitionSeason.getWinner())
                    .currentMatchDayList(new ArrayList<>())
                    .build());

        }
        return competitionSeasons;
    }
}
