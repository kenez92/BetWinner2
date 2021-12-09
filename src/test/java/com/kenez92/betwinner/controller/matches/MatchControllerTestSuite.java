package com.kenez92.betwinner.controller.matches;

import com.kenez92.betwinner.weather.WeatherDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.match.MatchDto;
import com.kenez92.betwinner.matchDay.MatchDayDto;
import com.kenez92.betwinner.matchScore.MatchScoreDto;
import com.kenez92.betwinner.matchStats.MatchStatsDto;
import com.kenez92.betwinner.match.MatchService;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class MatchControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatchService matchService;

    @Test
    public void shouldFetchEmptyList() throws Exception {
        //Given
        List<MatchDto> matchDtoList = new ArrayList<>();
        Mockito.when(matchService.getMatches()).thenReturn(matchDtoList);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/matches")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void testGetMatches() throws Exception {
        //Given
        List<MatchDto> matchDtoList = new ArrayList<>();
        matchDtoList.add(createMatchDto());
        matchDtoList.add(createMatchDto());
        matchDtoList.add(createMatchDto());
        Mockito.when(matchService.getMatches()).thenReturn(matchDtoList);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/matches")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id", Matchers.is(1234)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].footballId", Matchers.is(123456789)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].homeTeam", Matchers.is(matchDtoList.get(0).getHomeTeam())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].awayTeam", Matchers.is(matchDtoList.get(0).getAwayTeam())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].competitionId", Matchers.is(30000)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].seasonId", Matchers.is(123)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].matchStats.id", Matchers.is(123234)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].round", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].weather.id", Matchers.is(883383)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].matchScore.id", Matchers.is(222)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].matchDay.id", Matchers.is(223)));
    }

    @Test
    public void testGetMatch() throws Exception {
        //Given
        MatchDto matchDto = createMatchDto();
        Mockito.when(matchService.getMatch(ArgumentMatchers.anyLong())).thenReturn(matchDto);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/matches/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1234)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.footballId", Matchers.is(123456789)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.homeTeam", Matchers.is(matchDto.getHomeTeam())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.awayTeam", Matchers.is(matchDto.getAwayTeam())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.competitionId", Matchers.is(30000)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seasonId", Matchers.is(123)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchStats.id", Matchers.is(123234)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.round", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.weather.id", Matchers.is(883383)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchScore.id", Matchers.is(222)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchDay.id", Matchers.is(223)));
    }

    @Test
    public void testGetMatchShouldThrowBetWinnerExceptionWhenNotFound() throws Exception {
        //Given
        Mockito.when(matchService.getMatch(ArgumentMatchers.anyLong()))
                .thenThrow(new BetWinnerException(BetWinnerException.ERR_MATCH_NOT_FOUND_EXCEPTION));
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/matches/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(result -> Assert.assertTrue(result.getResolvedException() instanceof BetWinnerException))
                .andExpect(result -> Assert.assertEquals(BetWinnerException.ERR_MATCH_NOT_FOUND_EXCEPTION,
                        result.getResolvedException().getMessage()));
    }

    @Test
    public void testGetMatchesAtDay() throws Exception {
        //Given
        List<MatchDto> matchDtoList = new ArrayList<>();
        matchDtoList.add(createMatchDto());
        matchDtoList.add(createMatchDto());
        Mockito.when(matchService.getMatchesAtDay(ArgumentMatchers.anyString())).thenReturn(matchDtoList);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/matches/date=2020-01-01")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id", Matchers.is(1234)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].footballId", Matchers.is(123456789)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].homeTeam", Matchers.is(matchDtoList.get(0).getHomeTeam())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].awayTeam", Matchers.is(matchDtoList.get(0).getAwayTeam())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].competitionId", Matchers.is(30000)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].seasonId", Matchers.is(123)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].matchStats.id", Matchers.is(123234)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].round", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].weather.id", Matchers.is(883383)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].matchScore.id", Matchers.is(222)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].matchDay.id", Matchers.is(223)));
    }

    private MatchDto createMatchDto() {
        return MatchDto.builder()
                .id(1234L)
                .footballId(123456789L)
                .homeTeam("HOME_TEAM")
                .awayTeam("AWAY_TEAM")
                .competitionId(30000L)
                .seasonId(123L)
                .matchStats(MatchStatsDto.builder()
                        .id(123234L)
                        .build())
                .round(3)
                .weather(WeatherDto.builder()
                        .id(883383L)
                        .build())
                .matchScore(MatchScoreDto.builder()
                        .id(222L)
                        .build())
                .matchDay(MatchDayDto.builder()
                        .id(223L)
                        .build())
                .build();
    }
}
