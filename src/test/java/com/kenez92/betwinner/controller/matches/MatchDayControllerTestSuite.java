package com.kenez92.betwinner.controller.matches;

import com.kenez92.betwinner.domain.matches.MatchDayDto;
import com.kenez92.betwinner.domain.matches.MatchDto;
import com.kenez92.betwinner.domain.matches.MatchScoreDto;
import com.kenez92.betwinner.domain.matches.WeatherDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.service.matches.MatchDayService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class MatchDayControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatchDayService matchDayService;

    @Test
    public void shouldFetchEmptyList() throws Exception {
        //Given
        List<MatchDayDto> matchDayDtoList = new ArrayList<>();
        Mockito.when(matchDayService.getMatchDays()).thenReturn(matchDayDtoList);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/matches/rounds")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void testGetMatchDays() throws Exception {
        //Given
        List<MatchDayDto> matchDayDtoList = new ArrayList<>();
        matchDayDtoList.add(createMatchDayDto());
        matchDayDtoList.add(createMatchDayDto());
        Mockito.when(matchDayService.getMatchDays()).thenReturn(matchDayDtoList);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/matches/rounds")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    public void testGetMatchDay() throws Exception {
        //Given
        MatchDayDto matchDayDto = createMatchDayDto();
        Mockito.when(matchDayService.getMatchDay(ArgumentMatchers.anyLong())).thenReturn(matchDayDto);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/matches/rounds/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(252)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.localDate", Matchers.is(LocalDate.now().minusYears(1).toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchesList", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchesList[0].id", Matchers.is(234)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchesList[0].homeTeam", Matchers.is("Home team")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchesList[0].awayTeam", Matchers.is("Away team")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchesList[0].competitionId", Matchers.is(-202)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchesList[0].seasonId", Matchers.is(-203)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchesList[0].homeTeamPositionInTable", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchesList[0].awayTeamPositionInTable", Matchers.is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchesList[0].homeTeamChance", Matchers.is(60.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchesList[0].awayTeamChance", Matchers.is(40.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchesList[0].round", Matchers.is(23)));
    }

    @Test
    public void testGetMatchDayShouldThrowBetWinnerExceptionWhenNotFound() throws Exception {
        //Given
        Mockito.when(matchDayService.getMatchDay(ArgumentMatchers.anyLong()))
                .thenThrow(new BetWinnerException(BetWinnerException.ERR_MATCH_DAY_NOT_FOUND_EXCEPTION));
        //Whent & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/matches/rounds/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(result -> Assert.assertTrue(result.getResolvedException() instanceof BetWinnerException))
                .andExpect(result -> Assert.assertEquals(BetWinnerException.ERR_MATCH_DAY_NOT_FOUND_EXCEPTION,
                        result.getResolvedException().getMessage()));
    }

    private MatchDayDto createMatchDayDto() {
        List<MatchDto> matchDtoList = new ArrayList<>();
        matchDtoList.add(createMatchDto(234L));
        matchDtoList.add(createMatchDto(7389L));
        return MatchDayDto.builder()
                .id(252L)
                .localDate(LocalDate.now().minusYears(1))
                .matchesList(matchDtoList)
                .build();
    }

    private MatchDto createMatchDto(Long id) {
        return MatchDto.builder()
                .id(id)
                .footballId(123L)
                .homeTeam("Home team")
                .awayTeam("Away team")
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
                .couponTypeList(new ArrayList<>())
                .build();
    }

}
