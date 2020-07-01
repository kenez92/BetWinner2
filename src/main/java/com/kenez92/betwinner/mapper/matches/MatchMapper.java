package com.kenez92.betwinner.mapper.matches;

import com.kenez92.betwinner.domain.matches.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MatchMapper {
    public Match mapToMatch(MatchDto matchDto) {
        return Match.builder()
                .id(matchDto.getId())
                .footballId(matchDto.getFootballId())
                .homeTeam(matchDto.getHomeTeam())
                .awayTeam(matchDto.getAwayTeam())
                .competitionId(matchDto.getCompetitionId())
                .seasonId(matchDto.getSeasonId())
                .date(matchDto.getDate())
                .homeTeamPositionInTable(matchDto.getHomeTeamPositionInTable())
                .awayTeamPositionInTable(matchDto.getAwayTeamPositionInTable())
                .homeTeamChance(matchDto.getHomeTeamChance())
                .awayTeamChance(matchDto.getAwayTeamChance())
                .round(matchDto.getRound())
                .matchDay(mapToMatchDay(matchDto.getMatchDay()))
                .matchScore(mapToMatchScore(matchDto.getMatchScore()))
                .build();
    }

    public MatchDto mapToMatchDto(Match match) {
        return MatchDto.builder()
                .id(match.getId())
                .footballId(match.getFootballId())
                .homeTeam(match.getHomeTeam())
                .awayTeam(match.getAwayTeam())
                .competitionId(match.getCompetitionId())
                .seasonId(match.getSeasonId())
                .date(match.getDate())
                .homeTeamPositionInTable(match.getHomeTeamPositionInTable())
                .awayTeamPositionInTable(match.getAwayTeamPositionInTable())
                .homeTeamChance(match.getHomeTeamChance())
                .awayTeamChance(match.getAwayTeamChance())
                .round(match.getRound())
                .matchDay(mapToMatchDayDto(match.getMatchDay()))
                .matchScore(mapToMatchScoreDto(match.getMatchScore()))
                .build();
    }

    private MatchDay mapToMatchDay(MatchDayDto matchDayDto) {
        return MatchDay.builder()
                .id(matchDayDto.getId())
                .localDate(matchDayDto.getLocalDate())
                .matchesList(new ArrayList<>())
                .build();
    }

    private MatchDayDto mapToMatchDayDto(MatchDay matchDay) {
        return MatchDayDto.builder()
                .id(matchDay.getId())
                .localDate(matchDay.getLocalDate())
                .matchesList(new ArrayList<>())
                .build();
    }

    private MatchScoreDto mapToMatchScoreDto(MatchScore matchScore) {
        System.out.println(matchScore);
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

    private MatchScore mapToMatchScore(MatchScoreDto matchScoreDto) {
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
}
