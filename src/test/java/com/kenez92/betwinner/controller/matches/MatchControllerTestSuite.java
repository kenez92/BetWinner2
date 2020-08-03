package com.kenez92.betwinner.controller.matches;

import com.kenez92.betwinner.domain.matches.MatchDayDto;
import com.kenez92.betwinner.domain.matches.MatchDto;
import com.kenez92.betwinner.domain.matches.MatchScoreDto;
import com.kenez92.betwinner.domain.matches.WeatherDto;
import com.kenez92.betwinner.entity.matches.Match;
import com.kenez92.betwinner.entity.matches.MatchDay;
import com.kenez92.betwinner.entity.matches.MatchScore;
import com.kenez92.betwinner.entity.matches.Weather;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.service.UserService;
import com.kenez92.betwinner.service.matches.MatchService;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
public class MatchControllerTestSuite {
    private static final String HOME_TEAM = "home team";
    private static final String AWAY_TEAM = "away team";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MatchController matchController;
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
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void testGetMatches() throws Exception {
        //Given
        List<MatchDto> matchDtoList = new ArrayList<>();
        matchDtoList.add(createMatchDto(2012L));
        matchDtoList.add(createMatchDto(232L));
        matchDtoList.add(createMatchDto(23232L));
        Mockito.when(matchService.getMatches()).thenReturn(matchDtoList);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/matches")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)));
    }

    @Test
    public void testGetMatch() throws Exception {
        //Given
        MatchDto matchDto = createMatchDto(123456L);
        Mockito.when(matchService.getMatch(ArgumentMatchers.anyLong())).thenReturn(matchDto);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/matches/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().isOk()))
                .andExpect((MockMvcResultMatchers.jsonPath("$.id", Matchers.is(123456))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.footballId", Matchers.is(123)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.homeTeam", Matchers.is(HOME_TEAM)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.awayTeam", Matchers.is(AWAY_TEAM)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.competitionId", Matchers.is(-202)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seasonId", Matchers.is(-203)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.homeTeamPositionInTable", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.awayTeamPositionInTable", Matchers.is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.homeTeamChance", Matchers.is(60.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.awayTeamChance", Matchers.is(40.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.round", Matchers.is(23)));
    }

    @Test
    public void testGetMatchShouldThrowBetWinnerException() throws Exception {
        //Given
        Mockito.when(matchService.getMatch(ArgumentMatchers.any()))
                .thenThrow(new BetWinnerException(BetWinnerException.ERR_MATCH_NOT_FOUND_EXCEPTION));
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/matches/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(result -> Assert.assertTrue(result.getResolvedException() instanceof BetWinnerException))
                .andExpect(result -> Assert.assertEquals(BetWinnerException.ERR_MATCH_NOT_FOUND_EXCEPTION,
                        result.getResolvedException().getMessage()));
    }

    private MatchDto createMatchDto(Long id) {
        return MatchDto.builder()
                .id(id)
                .footballId(123L)
                .homeTeam(HOME_TEAM)
                .awayTeam(AWAY_TEAM)
                .competitionId(-202L)
                .seasonId(-203L)
                .date(new Date())
                .homeTeamPositionInTable(2)
                .awayTeamPositionInTable(4)
                .homeTeamChance(60.0)
                .awayTeamChance(40.0)
                .round(23)
                .matchDay(new MatchDayDto())
                .matchScore(new MatchScoreDto())
                .weather(new WeatherDto())
                .couponList(new ArrayList<>())
                .build();
    }

}