package com.kenez92.betwinner.controller.table;

import com.kenez92.betwinner.domain.table.CompetitionDto;
import com.kenez92.betwinner.domain.table.CompetitionSeasonDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.service.table.CompetitionSeasonService;
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
import java.util.List;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class CompetitionSeasonControllerTestSuite {
    private final String url = "/v1/competitions/seasons";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompetitionSeasonService competitionSeasonService;

    @Test
    public void shouldFetchEmptyList() throws Exception {
        //Given
        Mockito.when(competitionSeasonService.getCompetitionSeasons()).thenReturn(new ArrayList<>());
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void testGetCompetitionSeasons() throws Exception {
        //Given
        List<CompetitionSeasonDto> competitionSeasonDtoList = new ArrayList<>();
        competitionSeasonDtoList.add(createCompetitionSeasonDto());
        competitionSeasonDtoList.add(createCompetitionSeasonDto());
        competitionSeasonDtoList.add(createCompetitionSeasonDto());
        Mockito.when(competitionSeasonService.getCompetitionSeasons()).thenReturn(competitionSeasonDtoList);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id", Matchers.is(45685)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].footballId", Matchers.is(42863)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].startDate",
                        Matchers.is(LocalDate.now().minusYears(1).toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].endDate", Matchers.is(LocalDate.now().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].winner", Matchers.is("Test winner")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].competition.id", Matchers.is(1234)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].currentMatchDayList", Matchers.hasSize(0)));
    }

    @Test
    public void testGetCompetition() throws Exception {
        //Given
        Mockito.when(competitionSeasonService.getCompetitionSeason(ArgumentMatchers.anyLong()))
                .thenReturn(createCompetitionSeasonDto());
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get(url.concat("/34345"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(45685)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.footballId", Matchers.is(42863)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate",
                        Matchers.is(LocalDate.now().minusYears(1).toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate", Matchers.is(LocalDate.now().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.winner", Matchers.is("Test winner")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.competition.id", Matchers.is(1234)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentMatchDayList", Matchers.hasSize(0)));


    }

    @Test
    public void testGetCompetitionShouldThrowBetWinnerExceptionWhenNotFound() throws Exception {
        //Given
        Mockito.when(competitionSeasonService.getCompetitionSeason(ArgumentMatchers.anyLong()))
                .thenThrow(new BetWinnerException(BetWinnerException.ERR_COMPETITION_SEASON_NOT_FOUND_EXCEPTION));
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get(url.concat("/348963"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> Assert.assertTrue(result.getResolvedException() instanceof BetWinnerException))
                .andExpect(result -> Assert.assertEquals(BetWinnerException.ERR_COMPETITION_SEASON_NOT_FOUND_EXCEPTION,
                        result.getResolvedException().getMessage()));
    }

    private CompetitionSeasonDto createCompetitionSeasonDto() {
        return CompetitionSeasonDto.builder()
                .id(45685L)
                .footballId(42863L)
                .startDate(LocalDate.now().minusYears(1))
                .endDate(LocalDate.now())
                .winner("Test winner")
                .competition(CompetitionDto.builder()
                        .id(1234L)
                        .build())
                .currentMatchDayList(new ArrayList<>())
                .build();
    }
}