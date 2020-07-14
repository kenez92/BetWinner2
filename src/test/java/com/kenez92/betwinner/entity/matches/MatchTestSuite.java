package com.kenez92.betwinner.entity.matches;

import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.repository.matches.MatchRepository;
import com.kenez92.betwinner.repository.matches.MatchScoreRepository;
import com.kenez92.betwinner.repository.matches.WeatherRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    public void testFindById() {
        //Given
        Match match = createMatch();
        MatchScore matchScore = matchScoreRepository.save(createMatchScore(match.getFootballId()));
        Weather weather = weatherRepository.save(createWeather());
        Long weatherId = weather.getId();
        Long matchScoreId = matchScore.getId();
        match.setMatchScore(matchScore);
        match.setWeather(weather);
        Match createdMatch = matchRepository.save(match);
        //When
        Match dbMatch = matchRepository.findById(createdMatch.getId()).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_MATCH_NOT_FOUND_EXCEPTION));
        //Then
        try {
            Assert.assertNotNull(dbMatch);
            Assert.assertEquals(createdMatch.getId(), dbMatch.getId());
        } finally {
            matchRepository.deleteById(createdMatch.getId());
            matchScoreRepository.deleteById(matchScoreId);
            weatherRepository.deleteById(weatherId);
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
        Match match = createMatch();
        MatchScore matchScore = matchScoreRepository.save(createMatchScore(match.getFootballId()));
        Weather weather = weatherRepository.save(createWeather());
        Long weatherId = weather.getId();
        Long matchScoreId = matchScore.getId();
        long dateInLong = match.getDate().getTime();
        match.setMatchScore(matchScore);
        match.setWeather(weather);

        //When
        Match createdMatch = matchRepository.save(match);
        //Then
        try {
            Assert.assertEquals(123456789L, createdMatch.getFootballId(), 0.001);
            Assert.assertEquals(HOME_TEAM, createdMatch.getHomeTeam());
            Assert.assertEquals(AWAY_TEAM, createdMatch.getAwayTeam());
            Assert.assertEquals(30000L, createdMatch.getCompetitionId(), 0.001);
            Assert.assertEquals(123L, createdMatch.getSeasonId(), 0.001);
            Assert.assertEquals(dateInLong, createdMatch.getDate().getTime(), 0.001);
            Assert.assertEquals(3, createdMatch.getHomeTeamPositionInTable(), 0.001);
            Assert.assertEquals(12, createdMatch.getAwayTeamPositionInTable(), 0.001);
            Assert.assertEquals(40.0, createdMatch.getHomeTeamChance(), 0.001);
            Assert.assertEquals(60.0, createdMatch.getAwayTeamChance(), 0.001);
            Assert.assertEquals(3, createdMatch.getRound(), 0.001);
            Assert.assertEquals(matchScoreId, createdMatch.getMatchScore().getId());
            Assert.assertEquals(HOME_TEAM, createdMatch.getMatchScore().getWinner());
            Assert.assertEquals(weatherId, createdMatch.getWeather().getId());
            Assert.assertEquals(20.1, createdMatch.getWeather().getTempFelt(), 0.001);

        } finally {
            matchRepository.deleteById(createdMatch.getId());
            matchScoreRepository.deleteById(matchScoreId);
            weatherRepository.deleteById(weatherId);
        }
    }

    @Test
    public void testUpdateMatch() {
        //Given
        Match match = createMatch();
        MatchScore matchScore = matchScoreRepository.save(createMatchScore(match.getFootballId()));
        Weather weather = weatherRepository.save(createWeather());
        Long weatherId = weather.getId();
        Long matchScoreId = matchScore.getId();
        long dateInLong = match.getDate().getTime();
        match.setMatchScore(matchScore);
        match.setWeather(weather);
        Match createdMatch = matchRepository.save(match);
        //When
        Match dbMatch = matchRepository.save(Match.builder()
                .id(createdMatch.getId())
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
                .build());
        //Then
        try {
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
            Assert.assertEquals(matchScoreId, dbMatch.getMatchScore().getId());
            Assert.assertEquals(HOME_TEAM, dbMatch.getMatchScore().getWinner());
            Assert.assertEquals(weatherId, dbMatch.getWeather().getId());
            Assert.assertEquals(20.1, dbMatch.getWeather().getTempFelt(), 0.001);

        } finally {
            matchRepository.deleteById(dbMatch.getId());
            matchScoreRepository.deleteById(matchScoreId);
            weatherRepository.deleteById(weatherId);
        }

    }


    private Weather createWeather() {
        return Weather.builder()
                .tempFelt(20.1)
                .build();
    }

    private MatchScore createMatchScore(Long matchId) {
        return MatchScore.builder()
                .matchId(matchId)
                .winner(HOME_TEAM)
                .status(FINISHED)
                .duration(DURATION)
                .fullTimeHomeTeam(3)
                .fullTimeAwayTeam(1)
                .build();
    }

    private Match createMatch() {
        return Match.builder()
                .footballId(123456789L)
                .homeTeam(HOME_TEAM)
                .awayTeam(AWAY_TEAM)
                .competitionId(30000L)
                .seasonId(123L)
                .date(new Date(System.currentTimeMillis()))
                .homeTeamPositionInTable(3)
                .awayTeamPositionInTable(12)
                .homeTeamChance(40.0)
                .awayTeamChance(60.0)
                .round(3)
                .build();
    }
}