package com.kenez92.betwinner.mapper.matches;

import com.kenez92.betwinner.domain.matches.Match;
import com.kenez92.betwinner.domain.matches.MatchDay;
import com.kenez92.betwinner.domain.matches.MatchDayDto;
import com.kenez92.betwinner.domain.matches.MatchDto;
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
                .homeTeamPositionInTable(matchDto.getHomeTeamPositionInTable())
                .awayTeamPositionInTable(matchDto.getAwayTeamPositionInTable())
                .homeTeamChance(matchDto.getHomeTeamChance())
                .awayTeamChance(matchDto.getAwayTeamChance())
                .round(matchDto.getRound())
                .matchDay(mapToMatchDay(matchDto.getMatchDay()))
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
                .homeTeamPositionInTable(match.getHomeTeamPositionInTable())
                .awayTeamPositionInTable(match.getAwayTeamPositionInTable())
                .homeTeamChance(match.getHomeTeamChance())
                .awayTeamChance(match.getAwayTeamChance())
                .round(match.getRound())
                .matchDay(mapToMatchDayDto(match.getMatchDay()))
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

}
