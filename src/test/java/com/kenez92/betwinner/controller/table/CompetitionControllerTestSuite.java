package com.kenez92.betwinner.controller.table;

import com.kenez92.betwinner.domain.table.CompetitionDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.service.table.CompetitionService;
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
public class CompetitionControllerTestSuite {
    private final String url = "/v1/competitions";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompetitionService competitionService;

    @Test
    public void shouldFetchEmptyList() throws Exception {
        //Given
        List<CompetitionDto> competitionDtoList = new ArrayList<>();
        Mockito.when(competitionService.getCompetitions()).thenReturn(competitionDtoList);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void testGetCompetitions() throws Exception {
        //Given
        List<CompetitionDto> competitionDtoList = new ArrayList<>();
        competitionDtoList.add(createCompetitionDto());
        competitionDtoList.add(createCompetitionDto());
        competitionDtoList.add(createCompetitionDto());
        Mockito.when(competitionService.getCompetitions()).thenReturn(competitionDtoList);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id", Matchers.is(4563)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].footballId", Matchers.is(45252)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name", Matchers.is("Test competition")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].competitionSeasonList", Matchers.hasSize(0)));
    }

    @Test
    public void testGetCompetition() throws Exception {
        //Given
        Mockito.when(competitionService.getCompetition(ArgumentMatchers.anyLong())).thenReturn(createCompetitionDto());
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get(url.concat("/434534"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(4563)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.footballId", Matchers.is(45252)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Test competition")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.competitionSeasonList", Matchers.hasSize(0)));
    }

    @Test
    public void testGetCompetitionShouldThrowExceptionWhenNotFound() throws Exception {
        //Given
        Mockito.when(competitionService.getCompetition(ArgumentMatchers.anyLong()))
                .thenThrow(new BetWinnerException(BetWinnerException.ERR_COMPETITION_NOT_FOUND_EXCEPTION));
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get(url.concat("/434534"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> Assert.assertTrue(result.getResolvedException() instanceof BetWinnerException))
                .andExpect(result -> Assert.assertEquals(BetWinnerException.ERR_COMPETITION_NOT_FOUND_EXCEPTION,
                        result.getResolvedException().getMessage()));
    }

    private CompetitionDto createCompetitionDto() {
        return CompetitionDto.builder()
                .id(4563L)
                .footballId(45252L)
                .name("Test competition")
                .competitionSeasonList(new ArrayList<>())
                .build();
    }
}