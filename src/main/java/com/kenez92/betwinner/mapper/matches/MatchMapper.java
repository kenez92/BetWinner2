package com.kenez92.betwinner.mapper.matches;

import com.kenez92.betwinner.domain.CouponDto;
import com.kenez92.betwinner.domain.matches.MatchDayDto;
import com.kenez92.betwinner.domain.matches.MatchDto;
import com.kenez92.betwinner.domain.matches.MatchScoreDto;
import com.kenez92.betwinner.domain.matches.WeatherDto;
import com.kenez92.betwinner.entity.Coupon;
import com.kenez92.betwinner.entity.matches.Match;
import com.kenez92.betwinner.entity.matches.MatchDay;
import com.kenez92.betwinner.entity.matches.MatchScore;
import com.kenez92.betwinner.entity.matches.Weather;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MatchMapper {
    public Match mapToMatch(final MatchDto matchDto) {
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
                .weather(mapToWeather(matchDto.getWeather()))
                .couponList(new ArrayList<>())
                .build();
    }

    public MatchDto mapToMatchDto(final Match match) {
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
                .weather(mapToWeatherDto(match.getWeather()))
                .couponDtoList(new ArrayList<>())
                .build();
    }

    public List<MatchDto> mapToMatchDtoList(final List<Match> matchList) {
        return new ArrayList<>(matchList).stream()
                .map(this::mapToMatchDto)
                .collect(Collectors.toList());
    }

    private MatchDay mapToMatchDay(final MatchDayDto matchDayDto) {
        return MatchDay.builder()
                .id(matchDayDto.getId())
                .localDate(matchDayDto.getLocalDate())
                .matchesList(new ArrayList<>())
                .build();
    }

    private MatchDayDto mapToMatchDayDto(final MatchDay matchDay) {
        return MatchDayDto.builder()
                .id(matchDay.getId())
                .localDate(matchDay.getLocalDate())
                .matchesList(new ArrayList<>())
                .build();
    }

    private MatchScoreDto mapToMatchScoreDto(final MatchScore matchScore) {
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

    private MatchScore mapToMatchScore(final MatchScoreDto matchScoreDto) {
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

    private Weather mapToWeather(final WeatherDto weatherDto) {
        return Weather.builder()
                .id(weatherDto.getId())
                .country(weatherDto.getCountry())
                .date(weatherDto.getDate())
                .tempFelt(weatherDto.getTempFelt())
                .tempMin(weatherDto.getTempMin())
                .tempMax(weatherDto.getTempMax())
                .pressure(weatherDto.getPressure())
                .matchList(new ArrayList<>())
                .build();
    }

    private WeatherDto mapToWeatherDto(final Weather weather) {
        return WeatherDto.builder()
                .id(weather.getId())
                .country(weather.getCountry())
                .date(weather.getDate())
                .tempFelt(weather.getTempFelt())
                .tempMin(weather.getTempMin())
                .tempMax(weather.getTempMax())
                .pressure(weather.getPressure())
                .matchList(new ArrayList<>())
                .build();
    }
}
