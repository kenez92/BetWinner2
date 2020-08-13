package com.kenez92.betwinner.controller.table;

import com.kenez92.betwinner.domain.table.CompetitionSeasonDto;
import com.kenez92.betwinner.domain.table.CurrentMatchDayDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.service.table.CurrentMatchDayService;
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
public class CurrentMatchDayControllerTestSuite {
    private final String url = "/v1/competitions/seasons/rounds";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrentMatchDayService currentMatchDayService;

    @Test
    public void shouldFetchEmptyList() throws Exception {
        //Given
        Mockito.when(currentMatchDayService.getCurrentMatchDays()).thenReturn(new ArrayList<>());
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void testGetCurrentMatchDays() throws Exception {
        //Given
        List<CurrentMatchDayDto> currentMatchDayDtoList = new ArrayList<>();
        currentMatchDayDtoList.add(createCurrentMatchDayDto());
        currentMatchDayDtoList.add(createCurrentMatchDayDto());
        currentMatchDayDtoList.add(createCurrentMatchDayDto());
        Mockito.when(currentMatchDayService.getCurrentMatchDays()).thenReturn(currentMatchDayDtoList);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id", Matchers.is(4789878)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].matchDay", Matchers.is(23)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].competitionSeason.id", Matchers.is(7895)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].competitionTableList", Matchers.hasSize(0)));
    }

    @Test
    public void testGetCurrentMatchDay() throws Exception {
        //Given
        Mockito.when(currentMatchDayService.getCurrentMatchDay(ArgumentMatchers.anyLong()))
                .thenReturn(createCurrentMatchDayDto());
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get(url.concat("/48633"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(4789878)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchDay", Matchers.is(23)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.competitionSeason.id", Matchers.is(7895)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.competitionTableList", Matchers.hasSize(0)));
    }

    @Test
    public void testGetCurrentMatchDayShouldThrowBetWinnerExceptionWhenNotFound() throws Exception {
        //Given
        Mockito.when(currentMatchDayService.getCurrentMatchDay(ArgumentMatchers.anyLong()))
                .thenThrow(new BetWinnerException(BetWinnerException.ERR_CURRENT_MATCH_DAY_NOT_FOUND_EXCEPTION));
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get(url.concat("/3216522"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> Assert.assertTrue(result.getResolvedException() instanceof BetWinnerException))
                .andExpect(result -> Assert.assertEquals(BetWinnerException.ERR_CURRENT_MATCH_DAY_NOT_FOUND_EXCEPTION,
                        result.getResolvedException().getMessage()));

    }

    private CurrentMatchDayDto createCurrentMatchDayDto() {
        return CurrentMatchDayDto.builder()
                .id(4789878L)
                .matchDay(23)
                .competitionSeason(CompetitionSeasonDto.builder()
                        .id(7895L)
                        .build())
                .competitionTableList(new ArrayList<>())
                .build();
    }


}