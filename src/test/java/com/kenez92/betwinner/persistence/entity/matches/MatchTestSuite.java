package com.kenez92.betwinner.persistence.entity.matches;

import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.persistence.entity.weather.Weather;
import com.kenez92.betwinner.persistence.repository.matches.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MatchTestSuite {
    private static final String HOME_TEAM = "HomeTeam";
    private static final String AWAY_TEAM = "AwayTeam";
    private static final String FINISHED = "FINISHED";
    private static final String DURATION = "REGULAR";
    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MatchScoreRepository matchScoreRepository;

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private MatchDayRepository matchDayRepository;

    @Autowired
    private MatchStatsRepository matchStatsRepository;

    @Test
    public void testFindById() {
        //Given
        MatchScore matchScore = matchScoreRepository.save(createMatchScore());
        Weather weather = weatherRepository.save(createWeather());
        MatchDay matchDay = matchDayRepository.save(createMatchDay());
        MatchStats matchStats = matchStatsRepository.save(createMatchStats());
        Match match = matchRepository.save(createMatch(weather, matchScore, matchDay, matchStats));
        //When
        Match dbMatch = matchRepository.findById(match.getId()).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_MATCH_NOT_FOUND_EXCEPTION));
        //Then
        try {
            Assert.assertEquals(match.getId(), dbMatch.getId());
            Assert.assertEquals(123456789L, dbMatch.getFootballId(), 0.001);
            Assert.assertEquals(HOME_TEAM, dbMatch.getHomeTeam());
            Assert.assertEquals(AWAY_TEAM, dbMatch.getAwayTeam());
            Assert.assertEquals(30000L, dbMatch.getCompetitionId(), 0.001);
            Assert.assertEquals(123L, dbMatch.getSeasonId(), 0.001);
            Assert.assertTrue(match.getDate().getTime() - dbMatch.getDate().getTime() < 1000);
            Assert.assertEquals(3, dbMatch.getRound(), 0.001);
            Assert.assertEquals(matchScore.getId(), dbMatch.getMatchScore().getId());
            Assert.assertEquals(HOME_TEAM, dbMatch.getMatchScore().getWinner());
            Assert.assertEquals(weather.getId(), dbMatch.getWeather().getId());
            Assert.assertEquals(20.1, dbMatch.getWeather().getTempFelt(), 0.001);
            Assert.assertEquals(matchDay.getId(), dbMatch.getMatchDay().getId());
            Assert.assertEquals(matchStats.getId(), dbMatch.getMatchStats().getId());
            Assert.assertEquals(matchStats.getHomeTeamWins(), dbMatch.getMatchStats().getHomeTeamWins());
        } finally {
            matchRepository.deleteById(match.getId());
            matchScoreRepository.deleteById(matchScore.getId());
            matchStatsRepository.deleteById(matchStats.getId());
            weatherRepository.deleteById(weather.getId());
            matchDayRepository.deleteById(matchDay.getId());
        }
    }

    @Test
    public void testFindByIdShouldBeEmpty() {
        //Given
        //When
        Optional<Match> dbMatch = matchRepository.findById(-1L);
        //Then
        Assert.assertFalse(dbMatch.isPresent());
    }

    @Test
    public void testSaveMatch() {
        //Given
        MatchScore matchScore = matchScoreRepository.save(createMatchScore());
        Weather weather = weatherRepository.save(createWeather());
        MatchDay matchDay = matchDayRepository.save(createMatchDay());
        MatchStats matchStats = matchStatsRepository.save(createMatchStats());
        //When
        Match dbMatch = matchRepository.save(createMatch(weather, matchScore, matchDay, matchStats));
        //Then
        try {
            Assert.assertNotNull(dbMatch.getId());
            Assert.assertEquals(123456789L, dbMatch.getFootballId(), 0.001);
            Assert.assertEquals(HOME_TEAM, dbMatch.getHomeTeam());
            Assert.assertEquals(AWAY_TEAM, dbMatch.getAwayTeam());
            Assert.assertEquals(30000L, dbMatch.getCompetitionId(), 0.001);
            Assert.assertEquals(123L, dbMatch.getSeasonId(), 0.001);
            Assert.assertNotNull(dbMatch.getDate());
            Assert.assertEquals(3, dbMatch.getRound(), 0.001);
            Assert.assertEquals(matchScore.getId(), dbMatch.getMatchScore().getId());
            Assert.assertEquals(HOME_TEAM, dbMatch.getMatchScore().getWinner());
            Assert.assertEquals(weather.getId(), dbMatch.getWeather().getId());
            Assert.assertEquals(20.1, dbMatch.getWeather().getTempFelt(), 0.001);
            Assert.assertEquals(matchDay.getId(), dbMatch.getMatchDay().getId());
            Assert.assertEquals(matchStats.getId(), dbMatch.getMatchStats().getId());
            Assert.assertEquals(matchStats.getHomeTeamWins(), dbMatch.getMatchStats().getHomeTeamWins());

        } finally {
            matchRepository.deleteById(dbMatch.getId());
            matchScoreRepository.deleteById(matchScore.getId());
            matchStatsRepository.deleteById(matchStats.getId());
            weatherRepository.deleteById(weather.getId());
            matchDayRepository.deleteById(matchDay.getId());
        }
    }

    @Test
    public void testUpdateMatch() {
        //Given
        MatchScore matchScore = matchScoreRepository.save(createMatchScore());
        Weather weather = weatherRepository.save(createWeather());
        MatchDay matchDay = matchDayRepository.save(createMatchDay());
        MatchStats matchStats = matchStatsRepository.save(createMatchStats());
        Match match = matchRepository.save(createMatch(weather, matchScore, matchDay, matchStats));
        //When
        Match dbMatch = matchRepository.save(Match.builder()
                .id(match.getId())
                .footballId(123456789L)
                .homeTeam(HOME_TEAM)
                .awayTeam(AWAY_TEAM)
                .competitionId(40000L)
                .seasonId(1234L)
                .round(4)
                .date(new Date())
                .matchScore(matchScore)
                .matchStats(matchStats)
                .weather(weather)
                .matchDay(matchDay)
                .build());
        //Then
        try {
            Assert.assertNotNull(dbMatch.getId());
            Assert.assertEquals(123456789L, dbMatch.getFootballId(), 0.001);
            Assert.assertEquals(HOME_TEAM, dbMatch.getHomeTeam());
            Assert.assertEquals(AWAY_TEAM, dbMatch.getAwayTeam());
            Assert.assertEquals(40000L, dbMatch.getCompetitionId(), 0.001);
            Assert.assertEquals(1234L, dbMatch.getSeasonId(), 0.001);
            Assert.assertTrue(dbMatch.getDate().getTime() > match.getDate().getTime());
            Assert.assertEquals(4, dbMatch.getRound(), 0.001);
            Assert.assertEquals(matchScore.getId(), dbMatch.getMatchScore().getId());
            Assert.assertEquals(HOME_TEAM, dbMatch.getMatchScore().getWinner());
            Assert.assertEquals(weather.getId(), dbMatch.getWeather().getId());
            Assert.assertEquals(20.1, dbMatch.getWeather().getTempFelt(), 0.001);
            Assert.assertEquals(matchDay.getId(), dbMatch.getMatchDay().getId());
            Assert.assertEquals(matchStats.getId(), dbMatch.getMatchStats().getId());
            Assert.assertEquals(matchStats.getHomeTeamWins(), dbMatch.getMatchStats().getHomeTeamWins());
        } finally {
            matchRepository.deleteById(dbMatch.getId());
            matchScoreRepository.deleteById(matchScore.getId());
            matchStatsRepository.deleteById(matchStats.getId());
            weatherRepository.deleteById(weather.getId());
            matchDayRepository.deleteById(matchDay.getId());
        }

    }


    private Weather createWeather() {
        return Weather.builder()
                .tempFelt(20.1)
                .build();
    }

    private MatchScore createMatchScore() {
        return MatchScore.builder()
                .footballMatchId(-1223L)
                .winner(HOME_TEAM)
                .status(FINISHED)
                .duration(DURATION)
                .fullTimeHomeTeam(3)
                .fullTimeAwayTeam(1)
                .build();
    }

    private MatchDay createMatchDay() {
        return MatchDay.builder()
                .localDate(LocalDate.of(1550, 3, 20))
                .build();
    }

    private MatchStats createMatchStats() {
        return MatchStats.builder()
                .footballMatchId(23452345L)
                .homeTeamWins(10)
                .draws(5)
                .awayTeamWins(3)
                .gamesPlayed(18)
                .homeTeamPositionInTable(1)
                .awayTeamPositionInTable(5)
                .homeTeamChance(60D)
                .awayTeamChance(40D)
                .homeTeamCourse(1.4)
                .drawCourse(1.6)
                .awayTeamCourse(1.6)
                .build();
    }

    private Match createMatch(Weather weather, MatchScore matchScore, MatchDay matchDay, MatchStats matchStats) {
        return Match.builder()
                .footballId(123456789L)
                .homeTeam(HOME_TEAM)
                .awayTeam(AWAY_TEAM)
                .competitionId(30000L)
                .seasonId(123L)
                .date(new Date())
                .round(3)
                .matchStats(matchStats)
                .weather(weather)
                .matchScore(matchScore)
                .matchDay(matchDay)
                .build();
    }
}