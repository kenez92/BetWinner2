package com.kenez92.betwinner.mapper;

import com.kenez92.betwinner.domain.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
//@Mapper
public class CompetitionMapper {

    //Competition mapToCompetition(CompetitionDto competitionDto);

    //CompetitionDto mapToCompetitionDto(Competition competition);

    public Competition mapToCompetition(CompetitionDto competitionDto) {
        List<CompetitionSeason> competitionSeasonDtoList = new ArrayList<>();
        return new Competition(competitionDto.getId(),
                competitionDto.getName(),
                competitionSeasonDtoList);
    }
    public CompetitionDto mapToCompetitionDto(Competition competition) {
        return CompetitionDto.builder()
                .id(competition.getId())
                .name(competition.getName())
                .competitionSeasonList(new ArrayList<>())
                .build();
    }
}
