package com.kenez92.betwinner.controller.table;

import com.kenez92.betwinner.competitionTable.CompetitionTableDto;
import com.kenez92.betwinner.competitionTableElement.CompetitionTableElementDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.competitionTableElement.CompetitionTableElementService;
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
public class CompetitionTableElementControllerTestSuite {
    private final String url = "/v1/competitions/seasons/rounds/tables/elements";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompetitionTableElementService competitionTableElementService;

    @Test
    public void shouldFetchEmptyList() throws Exception {
        //Given
        Mockito.when(competitionTableElementService.getCompetitionTableElements()).thenReturn(new ArrayList<>());
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void testGetCompetitionTableElements() throws Exception {
        //Given
        List<CompetitionTableElementDto> competitionTableElementDtoList = new ArrayList<>();
        competitionTableElementDtoList.add(createCompetitionTableElementDto());
        competitionTableElementDtoList.add(createCompetitionTableElementDto());
        competitionTableElementDtoList.add(createCompetitionTableElementDto());
        Mockito.when(competitionTableElementService.getCompetitionTableElements())
                .thenReturn(competitionTableElementDtoList);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id", Matchers.is(455366)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].competitionTable.id", Matchers.is(4535)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name", Matchers.is("Test name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].position", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].playedGames", Matchers.is(23)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].won", Matchers.is(15)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].draw", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].lost", Matchers.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].points", Matchers.is(48)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].goalsFor", Matchers.is(51)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].goalsAgainst", Matchers.is(21)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].goalDifference", Matchers.is(30)));
    }

    @Test
    public void testGetCompetitionTableElement() throws Exception {
        //Given
        Mockito.when(competitionTableElementService.getCompetitionTableElement(ArgumentMatchers.anyLong()))
                .thenReturn(createCompetitionTableElementDto());
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get(url.concat("/45635"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(455366)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.competitionTable.id", Matchers.is(4535)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Test name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.position", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.playedGames", Matchers.is(23)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.won", Matchers.is(15)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.draw", Matchers.is(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lost", Matchers.is(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.points", Matchers.is(48)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.goalsFor", Matchers.is(51)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.goalsAgainst", Matchers.is(21)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.goalDifference", Matchers.is(30)));
    }

    @Test
    public void testGetCompetitionTableElementShouldThrowBetWinnerExceptionWhenNotFound() throws Exception {
        //Given
        Mockito.when(competitionTableElementService.getCompetitionTableElement(ArgumentMatchers.anyLong()))
                .thenThrow(new BetWinnerException(BetWinnerException.ERR_COMPETITION_TABLE_ELEMENT_NOT_FOUND_EXCEPTION));
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get(url.concat("/46579954"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> Assert.assertTrue(result.getResolvedException() instanceof BetWinnerException))
                .andExpect(result -> Assert.assertEquals(BetWinnerException.ERR_COMPETITION_TABLE_ELEMENT_NOT_FOUND_EXCEPTION,
                        result.getResolvedException().getMessage()));
    }


    private CompetitionTableElementDto createCompetitionTableElementDto() {
        return CompetitionTableElementDto.builder()
                .id(455366L)
                .competitionTable(CompetitionTableDto.builder()
                        .id(4535L)
                        .build())
                .name("Test name")
                .position(2)
                .playedGames(23)
                .won(15)
                .draw(3)
                .lost(5)
                .points(48)
                .goalsFor(51)
                .goalsAgainst(21)
                .goalDifference(30)
                .build();
    }
}
