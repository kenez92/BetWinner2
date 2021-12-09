package com.kenez92.betwinner.service.matches;

import com.kenez92.betwinner.matchDay.MatchDayDto;
import com.kenez92.betwinner.matchDay.MatchDayService;
import com.kenez92.betwinner.match.Match;
import com.kenez92.betwinner.matchDay.MatchDay;
import com.kenez92.betwinner.matchScore.MatchScore;
import com.kenez92.betwinner.matchStats.MatchStats;
import com.kenez92.betwinner.weather.Weather;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.matchDay.MatchDayRepository;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class MatchDayServiceTestSuite {
    private final static String HOME_TEAM = "home team";
    private final static String AWAY_TEAM = "away team";

    @Autowired
    private MatchDayService matchDayService;

    @MockBean
    private MatchDayRepository matchDayRepository;

    @Test
    public void testGetByLocalDate() {
        //Given
        MatchDay matchDay = createMatchDay();
        Mockito.when(matchDayRepository.findByLocalDate(ArgumentMatchers.any(LocalDate.class)))
                .thenReturn(Optional.ofNullable(matchDay));
        //When
        MatchDayDto matchDayDto = matchDayService.getByLocalDate(LocalDate.now());
        //Then
        Assert.assertEquals(matchDay.getId(), matchDayDto.getId());
        Assert.assertEquals(matchDay.getLocalDate(), matchDayDto.getLocalDate());
        Assert.assertEquals(matchDay.getMatchesList().size(), matchDayDto.getMatchesList().size());
    }

    @Test
    public void testGetByLocalDateShouldThrowExceptionWhenMatchDayNotFound() {
        //Given
        //When
        Mockito.when(matchDayRepository.findByLocalDate(ArgumentMatchers.any(LocalDate.class)))
                .thenReturn(Optional.empty());
        //Then
        Assertions.assertThrows(BetWinnerException.class, () -> matchDayService.getByLocalDate(LocalDate.now()));
    }

    @Test
    public void testCreateMatchDay() {
        //Given
        MatchDay matchDay = createMatchDay();
        Mockito.when(matchDayRepository.save(ArgumentMatchers.any(MatchDay.class))).thenReturn(matchDay);
        //When
        MatchDayDto matchDayDto = matchDayService.saveMatchDay(new MatchDayDto());
        //Then
        Assert.assertEquals(matchDay.getId(), matchDayDto.getId());
        Assert.assertEquals(matchDay.getLocalDate(), matchDayDto.getLocalDate());
        Assert.assertEquals(matchDay.getMatchesList().size(), matchDayDto.getMatchesList().size());
    }

    @Test
    public void testGetMatchDay() {
        //Given
        MatchDay matchDay = createMatchDay();
        Mockito.when(matchDayRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.ofNullable(matchDay));
        //When
        MatchDayDto matchDayDto = matchDayService.getMatchDay(234234L);
        //Then
        Assert.assertEquals(matchDay.getId(), matchDayDto.getId());
        Assert.assertEquals(matchDay.getLocalDate(), matchDayDto.getLocalDate());
        Assert.assertEquals(matchDay.getMatchesList().size(), matchDayDto.getMatchesList().size());
    }

    @Test
    public void testGetMatchDayShouldThrowBetWinnerExceptionWhenMatchDayNotFound() {
        //Given
        Mockito.when(matchDayRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        //Then
        Assertions.assertThrows(BetWinnerException.class, () -> matchDayService.getMatchDay(2346L));
    }

    @Test
    public void testGetMatchDays() {
        //Given
        List<MatchDay> matchDayList = new ArrayList<>();
        matchDayList.add(createMatchDay());
        matchDayList.add(createMatchDay());
        Mockito.when(matchDayRepository.findAll()).thenReturn(matchDayList);
        //When
        List<MatchDayDto> matchDayDtoList = matchDayService.getMatchDays();
        //Then
        Assert.assertEquals(matchDayList.size(), matchDayDtoList.size());
    }

    private MatchDay createMatchDay() {
        List<Match> matchList = new ArrayList<>();
        matchList.add(createMatch());
        matchList.add(createMatch());
        matchList.add(createMatch());
        return MatchDay.builder()
                .id(23423L)
                .localDate(LocalDate.now())
                .matchesList(matchList)
                .build();
    }

    private Match createMatch() {
        return Match.builder()
                .footballId(-11234L)
                .homeTeam(HOME_TEAM)
                .awayTeam(AWAY_TEAM)
                .competitionId(-202L)
                .seasonId(-203L)
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
