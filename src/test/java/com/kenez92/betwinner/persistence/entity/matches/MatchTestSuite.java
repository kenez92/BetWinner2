package com.kenez92.betwinner.persistence.entity.matches;

import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.persistence.repository.matches.MatchDayRepository;
import com.kenez92.betwinner.persistence.repository.matches.MatchRepository;
import com.kenez92.betwinner.persistence.repository.matches.MatchScoreRepository;
import com.kenez92.betwinner.persistence.repository.matches.WeatherRepository;
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

    @Test
    public void testFindById() {
        //Given
        MatchScore matchScore = matchScoreRepository.save(createMatchScore());
        Weather weather = weatherRepository.save(createWeather());
        MatchDay matchDay = matchDayRepository.save(createMatchDay());
        Match match = matchRepository.save(createMatch(weather, matchScore, matchDay));
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
            Assert.assertEquals(3, dbMatch.getHomeTeamPositionInTable(), 0.001);
            Assert.assertEquals(12, dbMatch.getAwayTeamPositionInTable(), 0.001);
            Assert.assertEquals(40.0, dbMatch.getHomeTeamChance(), 0.001);
            Assert.assertEquals(60.0, dbMatch.getAwayTeamChance(), 0.001);
            Assert.assertEquals(3, dbMatch.getRound(), 0.001);
            Assert.assertEquals(matchScore.getId(), dbMatch.getMatchScore().getId());
            Assert.assertEquals(HOME_TEAM, dbMatch.getMatchScore().getWinner());
            Assert.assertEquals(weather.getId(), dbMatch.getWeather().getId());
            Assert.assertEquals(20.1, dbMatch.getWeather().getTempFelt(), 0.001);
            Assert.assertEquals(matchDay.getId(), dbMatch.getMatchDay().getId());
        } finally {
            matchRepository.deleteById(match.getId());
            matchScoreRepository.deleteById(matchScore.getId());
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
        //When
        Match dbMatch = matchRepository.save(createMatch(weather, matchScore, matchDay));
        //Then
        try {
            Assert.assertNotNull(dbMatch.getId());
            Assert.assertEquals(123456789L, dbMatch.getFootballId(), 0.001);
            Assert.assertEquals(HOME_TEAM, dbMatch.getHomeTeam());
            Assert.assertEquals(AWAY_TEAM, dbMatch.getAwayTeam());
            Assert.assertEquals(30000L, dbMatch.getCompetitionId(), 0.001);
            Assert.assertEquals(123L, dbMatch.getSeasonId(), 0.001);
            Assert.assertNotNull(dbMatch.getDate());
            Assert.assertEquals(3, dbMatch.getHomeTeamPositionInTable(), 0.001);
            Assert.assertEquals(12, dbMatch.getAwayTeamPositionInTable(), 0.001);
            Assert.assertEquals(40.0, dbMatch.getHomeTeamChance(), 0.001);
            Assert.assertEquals(60.0, dbMatch.getAwayTeamChance(), 0.001);
            Assert.assertEquals(3, dbMatch.getRound(), 0.001);
            Assert.assertEquals(matchScore.getId(), dbMatch.getMatchScore().getId());
            Assert.assertEquals(HOME_TEAM, dbMatch.getMatchScore().getWinner());
            Assert.assertEquals(weather.getId(), dbMatch.getWeather().getId());
            Assert.assertEquals(20.1, dbMatch.getWeather().getTempFelt(), 0.001);
            Assert.assertEquals(matchDay.getId(), dbMatch.getMatchDay().getId());

        } finally {
            matchRepository.deleteById(dbMatch.getId());
            matchScoreRepository.deleteById(matchScore.getId());
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
        Match match = matchRepository.save(createMatch(weather, matchScore, matchDay));
        //When
        Match dbMatch = matchRepository.save(Match.builder()
                .id(match.getId())
                .footballId(123456789L)
                .homeTeam(HOME_TEAM)
                .awayTeam(AWAY_TEAM)
                .competitionId(40000L)
                .seasonId(1234L)
                .homeTeamPositionInTable(2)
                .awayTeamPositionInTable(11)
                .homeTeamChance(30.0)
                .awayTeamChance(70.0)
                .round(4)
                .matchScore(matchScore)
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
            Assert.assertNull(dbMatch.getDate());
            Assert.assertEquals(2, dbMatch.getHomeTeamPositionInTable(), 0.001);
            Assert.assertEquals(11, dbMatch.getAwayTeamPositionInTable(), 0.001);
            Assert.assertEquals(30.0, dbMatch.getHomeTeamChance(), 0.001);
            Assert.assertEquals(70.0, dbMatch.getAwayTeamChance(), 0.001);
            Assert.assertEquals(4, dbMatch.getRound(), 0.001);
            Assert.assertEquals(matchScore.getId(), dbMatch.getMatchScore().getId());
            Assert.assertEquals(HOME_TEAM, dbMatch.getMatchScore().getWinner());
            Assert.assertEquals(weather.getId(), dbMatch.getWeather().getId());
            Assert.assertEquals(20.1, dbMatch.getWeather().getTempFelt(), 0.001);
            Assert.assertEquals(matchDay.getId(), dbMatch.getMatchDay().getId());

        } finally {
            matchRepository.deleteById(dbMatch.getId());
            matchScoreRepository.deleteById(matchScore.getId());
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
                .matchId(-1223L)
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

    private Match createMatch(Weather weather, MatchScore matchScore, MatchDay matchDay) {
        return Match.builder()
                .footballId(123456789L)
                .homeTeam(HOME_TEAM)
                .awayTeam(AWAY_TEAM)
                .competitionId(30000L)
                .seasonId(123L)
                .date(new Date())
                .homeTeamPositionInTable(3)
                .awayTeamPositionInTable(12)
                .homeTeamChance(40.0)
                .awayTeamChance(60.0)
                .round(3)
                .weather(weather)
                .matchScore(matchScore)
                .matchDay(matchDay)
                .build();
    }
}