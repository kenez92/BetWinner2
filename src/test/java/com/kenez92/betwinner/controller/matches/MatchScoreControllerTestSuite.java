package com.kenez92.betwinner.controller.matches;

import com.kenez92.betwinner.domain.matches.MatchScoreDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.service.matches.MatchScoreService;
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
public class MatchScoreControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatchScoreService matchScoreService;

    @Test
    public void shouldFetchEmptyList() throws Exception {
        //Given
        List<MatchScoreDto> matchScoreDtoList = new ArrayList<>();
        Mockito.when(matchScoreService.getMatchScores()).thenReturn(matchScoreDtoList);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/matches/scores")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void testGetMatchScores() throws Exception {
        //Given
        List<MatchScoreDto> matchScoreDtoList = new ArrayList<>();
        matchScoreDtoList.add(createMatchScoreDto());
        matchScoreDtoList.add(createMatchScoreDto());
        matchScoreDtoList.add(createMatchScoreDto());
        Mockito.when(matchScoreService.getMatchScores()).thenReturn(matchScoreDtoList);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/matches/scores")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id", Matchers.is(222)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].footballMatchId", Matchers.is(123)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].winner", Matchers.is("Home team")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].status", Matchers.is("Finished")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].duration", Matchers.is("Regular")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].fullTimeHomeTeam", Matchers.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].fullTimeAwayTeam", Matchers.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].halfTimeHomeTeam", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].halfTimeAwayTeam", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].extraTimeHomeTeam", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].extraTimeAwayTeam", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].penaltiesHomeTeam", Matchers.is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].penaltiesAwayTeam", Matchers.is(3)));
    }

    @Test
    public void testGetMatchScore() throws Exception {
        //Given
        MatchScoreDto matchScoreDto = createMatchScoreDto();
        Mockito.when(matchScoreService.getMatchScore(ArgumentMatchers.anyLong())).thenReturn(matchScoreDto);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/matches/scores/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(222)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.footballMatchId", Matchers.is(123)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.winner", Matchers.is("Home team")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("Finished")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.duration", Matchers.is("Regular")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullTimeHomeTeam", Matchers.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullTimeAwayTeam", Matchers.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.halfTimeHomeTeam", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.halfTimeAwayTeam", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.extraTimeHomeTeam", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.extraTimeAwayTeam", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.penaltiesHomeTeam", Matchers.is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.penaltiesAwayTeam", Matchers.is(3)));
    }

    @Test
    public void testGetMatchShouldThrowBetWinnerExceptionWhenNotFound() throws Exception {
        //Given
        Mockito.when(matchScoreService.getMatchScore(ArgumentMatchers.anyLong()))
                .thenThrow(new BetWinnerException(BetWinnerException.ERR_MATCH_SCORE_NOT_FOUND_EXCEPTION));

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/matches/scores/323")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(result -> Assert.assertTrue(result.getResolvedException() instanceof BetWinnerException))
                .andExpect(result -> Assert.assertEquals(BetWinnerException.ERR_MATCH_SCORE_NOT_FOUND_EXCEPTION,
                        result.getResolvedException().getMessage()));
    }

    private MatchScoreDto createMatchScoreDto() {
        return MatchScoreDto.builder()
                .id(222L)
                .footballMatchId(123L)
                .winner("Home team")
                .status("Finished")
                .duration("Regular")
                .fullTimeHomeTeam(5)
                .fullTimeAwayTeam(5)
                .halfTimeHomeTeam(2)
                .halfTimeAwayTeam(1)
                .extraTimeHomeTeam(1)
                .extraTimeAwayTeam(1)
                .penaltiesHomeTeam(4)
                .penaltiesAwayTeam(3)
                .build();
    }
}