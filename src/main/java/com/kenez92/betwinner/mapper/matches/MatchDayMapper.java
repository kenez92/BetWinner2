package com.kenez92.betwinner.mapper.matches;

import com.kenez92.betwinner.domain.matches.MatchDayDto;
import com.kenez92.betwinner.domain.matches.MatchDto;
import com.kenez92.betwinner.persistence.entity.matches.Match;
import com.kenez92.betwinner.persistence.entity.matches.MatchDay;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MatchDayMapper {

    public MatchDay mapToMatchDay(final MatchDayDto matchDayDto) {
        return MatchDay.builder()
                .id(matchDayDto.getId())
                .localDate(matchDayDto.getLocalDate())
                .matchesList(mapToMatchList(matchDayDto.getMatchesList()))
                .build();
    }

    public MatchDayDto mapToMatchDayDto(final MatchDay matchDay) {
        return MatchDayDto.builder()
                .id(matchDay.getId())
                .localDate(matchDay.getLocalDate())
                .matchesList(mapToMatchDtoList(matchDay.getMatchesList()))
                .build();
    }

    public List<MatchDayDto> mapToMatchDayDtoList(List<MatchDay> matchDayList) {
        return new ArrayList<>(matchDayList).stream()
                .map(this::mapToMatchDayDto)
                .collect(Collectors.toList());
    }


    private List<Match> mapToMatchList(final List<MatchDto> matchDtoList) {
        List<Match> matches = new ArrayList<>();
        if (matchDtoList != null) {
            for (MatchDto tmpMatchDto : matchDtoList) {
                matches.add(Match.builder()
                        .id(tmpMatchDto.getId())
                        .footballId(tmpMatchDto.getFootballId())
                        .homeTeam(tmpMatchDto.getHomeTeam())
                        .awayTeam(tmpMatchDto.getAwayTeam())
                        .competitionId(tmpMatchDto.getCompetitionId())
                        .seasonId(tmpMatchDto.getSeasonId())
                        .date(tmpMatchDto.getDate())
                        .homeTeamPositionInTable(tmpMatchDto.getHomeTeamPositionInTable())
                        .awayTeamPositionInTable(tmpMatchDto.getAwayTeamPositionInTable())
                        .homeTeamChance(tmpMatchDto.getHomeTeamChance())
                        .awayTeamChance(tmpMatchDto.getAwayTeamChance())
                        .round(tmpMatchDto.getRound())
                        .matchDay(MatchDay.builder()
                                .id(tmpMatchDto.getMatchDay().getId())
                                .localDate(tmpMatchDto.getMatchDay().getLocalDate())
                                .matchesList(new ArrayList<>())
                                .build())
                        .build());
            }
        }
        return matches;
    }

    private List<MatchDto> mapToMatchDtoList(final List<Match> matchList) {
        List<MatchDto> matchDtoList = new ArrayList<>();
        if (matchList != null) {
            for (Match match : matchList) {
                matchDtoList.add(MatchDto.builder()
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
                        .matchDay(MatchDayDto.builder()
                                .id(match.getMatchDay().getId())
                                .localDate(match.getMatchDay().getLocalDate())
                                .matchesList(new ArrayList<>())
                                .build())
                        .build());
            }
        }
        return matchDtoList;
    }
}
