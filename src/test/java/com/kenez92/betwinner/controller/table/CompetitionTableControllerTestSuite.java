package com.kenez92.betwinner.controller.table;

import com.kenez92.betwinner.domain.table.CompetitionTableDto;
import com.kenez92.betwinner.domain.table.CurrentMatchDayDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.service.table.CompetitionTableService;
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
public class CompetitionTableControllerTestSuite {
    private static final String URL = "/v1/competitions/seasons/rounds/tables";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompetitionTableService competitionTableService;

    @Test
    public void shouldFetchEmptyList() throws Exception {
        //Given
        Mockito.when(competitionTableService.getCompetitionTables()).thenReturn(new ArrayList<>());
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get(URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void testGetCompetitionTables() throws Exception {
        //Given
        List<CompetitionTableDto> competitionTableDtoList = new ArrayList<>();
        competitionTableDtoList.add(createCompetitionTableDto());
        competitionTableDtoList.add(createCompetitionTableDto());
        competitionTableDtoList.add(createCompetitionTableDto());
        Mockito.when(competitionTableService.getCompetitionTables()).thenReturn(competitionTableDtoList);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get(URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id", Matchers.is(45635)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].stage", Matchers.is("Test stage")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].type", Matchers.is("Test type")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].currentMatchDay.id", Matchers.is(23455)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].competitionTableElements", Matchers.hasSize(0)));
    }

    @Test
    public void testGetCompetitionTable() throws Exception {
        //Given
        Mockito.when(competitionTableService.getCompetitionTable(ArgumentMatchers.anyLong()))
                .thenReturn(createCompetitionTableDto());
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get(URL.concat("/45653"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(45635)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stage", Matchers.is("Test stage")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", Matchers.is("Test type")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentMatchDay.id", Matchers.is(23455)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.competitionTableElements", Matchers.hasSize(0)));


    }

    @Test
    public void testGetCompetitionTableShouldThrowBetWinnerExceptionWhenNotFound() throws Exception {
        //Given
        Mockito.when(competitionTableService.getCompetitionTable(ArgumentMatchers.anyLong()))
                .thenThrow(new BetWinnerException(BetWinnerException.ERR_COMPETITION_TABLE_NOT_FOUND_EXCEPTION));
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get(URL.concat("/4582111"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> Assert.assertTrue(result.getResolvedException() instanceof BetWinnerException))
                .andExpect(result -> Assert.assertEquals(BetWinnerException.ERR_COMPETITION_TABLE_NOT_FOUND_EXCEPTION,
                        result.getResolvedException().getMessage()));


    }

    private CompetitionTableDto createCompetitionTableDto() {
        return CompetitionTableDto.builder()
                .id(45635L)
                .stage("Test stage")
                .type("Test type")
                .currentMatchDay(CurrentMatchDayDto.builder()
                        .id(23455L)
                        .build())
                .competitionTableElements(new ArrayList<>())
                .build();
    }
}