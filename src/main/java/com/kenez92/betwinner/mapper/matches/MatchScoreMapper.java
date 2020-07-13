package com.kenez92.betwinner.mapper.matches;

import com.kenez92.betwinner.domain.matches.MatchScoreDto;
import com.kenez92.betwinner.entity.matches.MatchScore;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MatchScoreMapper {
    public MatchScore mapToMatchScore(MatchScoreDto matchScoreDto) {
        return MatchScore.builder()
                .id(matchScoreDto.getId())
                .matchId(matchScoreDto.getMatchId())
                .winner(matchScoreDto.getWinner())
                .status(matchScoreDto.getStatus())
                .duration(matchScoreDto.getDuration())
                .fullTimeHomeTeam(matchScoreDto.getFullTimeHomeTeam())
                .fullTimeAwayTeam(matchScoreDto.getFullTimeAwayTeam())
                .halfTimeHomeTeam(matchScoreDto.getHalfTimeHomeTeam())
                .halfTimeAwayTeam(matchScoreDto.getHalfTimeAwayTeam())
                .extraTimeHomeTeam(matchScoreDto.getExtraTimeHomeTeam())
                .extraTimeAwayTeam(matchScoreDto.getExtraTimeAwayTeam())
                .penaltiesHomeTeam(matchScoreDto.getPenaltiesHomeTeam())
                .penaltiesAwayTeam(matchScoreDto.getPenaltiesAwayTeam())
                .build();
    }

    public MatchScoreDto mapToMatchScoreDto(MatchScore matchScore) {
        return MatchScoreDto.builder()
                .id(matchScore.getId())
                .matchId(matchScore.getMatchId())
                .winner(matchScore.getWinner())
                .status(matchScore.getStatus())
                .duration(matchScore.getDuration())
                .fullTimeHomeTeam(matchScore.getFullTimeHomeTeam())
                .fullTimeAwayTeam(matchScore.getFullTimeAwayTeam())
                .halfTimeHomeTeam(matchScore.getHalfTimeHomeTeam())
                .halfTimeAwayTeam(matchScore.getHalfTimeAwayTeam())
                .extraTimeHomeTeam(matchScore.getExtraTimeHomeTeam())
                .extraTimeAwayTeam(matchScore.getExtraTimeAwayTeam())
                .penaltiesHomeTeam(matchScore.getPenaltiesHomeTeam())
                .penaltiesAwayTeam(matchScore.getPenaltiesAwayTeam())
                .build();
    }

    public List<MatchScoreDto> mapToMatchScoreDtoList(List<MatchScore> matchScoreList) {
        return new ArrayList<>(matchScoreList).stream()
                .map(this::mapToMatchScoreDto)
                .collect(Collectors.toList());
    }
}
