package com.kenez92.betwinner.service.matches;

import com.kenez92.betwinner.domain.matches.MatchDayDto;
import com.kenez92.betwinner.domain.matches.MatchDto;
import com.kenez92.betwinner.domain.matches.MatchScoreDto;
import com.kenez92.betwinner.domain.weather.WeatherDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.persistence.entity.matches.Match;
import com.kenez92.betwinner.persistence.entity.matches.MatchDay;
import com.kenez92.betwinner.persistence.entity.matches.MatchScore;
import com.kenez92.betwinner.persistence.entity.matches.MatchStats;
import com.kenez92.betwinner.persistence.entity.weather.Weather;
import com.kenez92.betwinner.persistence.repository.matches.MatchRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class MatchServiceTestSuite {
    private final static String HOME_TEAM = "home team";
    private final static String AWAY_TEAM = "away team";

    @Autowired
    private MatchService matchService;

    @MockBean
    private MatchRepository matchRepository;

    @Test
    public void getMatches() {
        //Given
        List<Match> matchList = new ArrayList<>();
        matchList.add(createMatch());
        matchList.add(createMatch());
        Mockito.when(matchRepository.findAll()).thenReturn(matchList);
        //When
        List<MatchDto> matchDtoList = matchService.getMatches();
        //Then
        Assert.assertEquals(matchList.size(), matchDtoList.size());
    }

    @Test
    public void getMatch() {
        //Given
        Match match = createMatch();
        Mockito.when(matchRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.ofNullable(match));
        //When
        MatchDto matchDto = matchService.getMatch(21234L);
        //Then
        Assert.assertEquals(match.getId(), matchDto.getId());
        Assert.assertEquals(match.getFootballId(), matchDto.getFootballId());
        Assert.assertEquals(match.getHomeTeam(), matchDto.getHomeTeam());
        Assert.assertEquals(match.getAwayTeam(), matchDto.getAwayTeam());
        Assert.assertEquals(match.getCompetitionId(), matchDto.getCompetitionId());
        Assert.assertEquals(match.getSeasonId(), matchDto.getSeasonId());
        Assert.assertEquals(match.getDate(), matchDto.getDate());
        Assert.assertEquals(match.getCouponTypeList().size(), matchDto.getCouponTypeList().size());
    }

    @Test
    public void testExistsByFields() {
        //Given
        Mockito.when(matchRepository.existsByHomeTeamAndAwayTeamAndRound(ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(), ArgumentMatchers.anyInt())).thenReturn(true);
        //When
        boolean result = matchRepository.existsByHomeTeamAndAwayTeamAndRound("Home team",
                "Away team", 33);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testFindByFields() {
        //Given
        Match match = createMatch();
        Mockito.when(matchRepository.findByHomeTeamAndAwayTeamAndRound(ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(), ArgumentMatchers.anyInt())).thenReturn(Optional.ofNullable(match));
        //When
        MatchDto matchDto = matchService.findByFields("Home team",
                "Away team", 33);
        //Then
        Assert.assertEquals(match.getId(), matchDto.getId());
        Assert.assertEquals(match.getFootballId(), matchDto.getFootballId());
        Assert.assertEquals(match.getHomeTeam(), matchDto.getHomeTeam());
        Assert.assertEquals(match.getAwayTeam(), matchDto.getAwayTeam());
        Assert.assertEquals(match.getCompetitionId(), matchDto.getCompetitionId());
        Assert.assertEquals(match.getSeasonId(), matchDto.getSeasonId());
        Assert.assertEquals(match.getDate(), matchDto.getDate());
        Assert.assertEquals(match.getCouponTypeList().size(), matchDto.getCouponTypeList().size());
    }

    @Test
    public void testSaveMatch() {
        //Given
        Match match = createMatch();
        MatchDto tmpMatchDto = MatchDto.builder()
                .matchDay(new MatchDayDto())
                .matchScore(new MatchScoreDto())
                .weather(new WeatherDto())
                .build();
        Mockito.when(matchRepository.save(ArgumentMatchers.any(Match.class))).thenReturn(match);
        //When
        MatchDto matchDto = matchService.saveMatch(tmpMatchDto);
        //Then
        Assert.assertEquals(match.getId(), matchDto.getId());
        Assert.assertEquals(match.getFootballId(), matchDto.getFootballId());
        Assert.assertEquals(match.getHomeTeam(), matchDto.getHomeTeam());
        Assert.assertEquals(match.getAwayTeam(), matchDto.getAwayTeam());
        Assert.assertEquals(match.getCompetitionId(), matchDto.getCompetitionId());
        Assert.assertEquals(match.getSeasonId(), matchDto.getSeasonId());
        Assert.assertEquals(match.getDate(), matchDto.getDate());
        Assert.assertEquals(match.getCouponTypeList().size(), matchDto.getCouponTypeList().size());
    }

    @Test
    public void testGetMatchesAtDay() {
        //Given
        String date = "2020-01-01";
        List<Match> matchList = new ArrayList<>();
        matchList.add(createMatch());
        matchList.add(createMatch());
        Mockito.when(matchRepository.findMatchesAtDate(java.sql.Date.valueOf(date))).thenReturn(matchList);
        //When
        List<MatchDto> matchDtoList = matchService.getMatchesAtDay(date);
        //Then
        Assert.assertEquals(2, matchDtoList.size());
    }

    @Test
    public void testGetMatchesAtDayShouldThrowException() {
        //Given
        String date = "2020-01-01an";
        //When
        Exception exception = Assertions.assertThrows(BetWinnerException.class, () -> {
            matchService.getMatchesAtDay(date);
        });
        String expectedMessage = BetWinnerException.ERR_ILLEGAL_ARGUMENT_EXCEPTION;
        String actualMessage = exception.getMessage();
        //Then
        Assert.assertTrue(actualMessage.contains(expectedMessage));
    }


    private Match createMatch() {
        return Match.builder()
                .id(23423L)
                .footballId(11234L)
                .homeTeam(HOME_TEAM)
                .awayTeam(AWAY_TEAM)
                .competitionId(202L)
                .seasonId(203L)
                .date(new Date())
                .round(23)
                .matchStats(new MatchStats())
                .matchDay(new MatchDay())
                .matchScore(new MatchScore())
                .weather(new Weather())
                .couponTypeList(new ArrayList<>())
                .build();
    }
}