package com.kenez92.betwinner.service.table;

import com.kenez92.betwinner.domain.table.CompetitionDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.persistence.entity.table.Competition;
import com.kenez92.betwinner.persistence.repository.table.CompetitionRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CompetitionServiceTestSuite {
    @Autowired
    private CompetitionService competitionService;

    @MockBean
    private CompetitionRepository competitionRepository;

    @Test
    public void testGetCompetitions() {
        //Given
        List<Competition> competitionList = new ArrayList<>();
        competitionList.add(createCompetition());
        competitionList.add(createCompetition());
        competitionList.add(createCompetition());
        Mockito.when(competitionRepository.findAll()).thenReturn(competitionList);
        //When
        List<CompetitionDto> competitionDtoList = competitionService.getCompetitions();
        //Then
        Assert.assertEquals(competitionList.size(), competitionDtoList.size());
    }

    @Test
    public void testGetCompetition() {
        //Given
        Competition competition = createCompetition();
        Mockito.when(competitionRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.ofNullable(competition));
        //When
        CompetitionDto competitionDto = competitionService.getCompetition(234L);
        //Then
        Assert.assertEquals(competition.getId(), competitionDto.getId());
        Assert.assertEquals(competition.getFootballId(), competitionDto.getFootballId());
        Assert.assertEquals(competition.getName(), competitionDto.getName());
    }

    @Test
    public void testGetCompetitionShouldThrowExceptionWhenCompetitionNotFound() {
        //Given
        Mockito.when(competitionRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        //When
        //Then
        Assertions.assertThrows(BetWinnerException.class, () -> competitionService.getCompetition(234L));
    }

    @Test
    public void testCompetitionExistsByFootballId() {
        //Given
        Mockito.when(competitionRepository.existsByFootballId(ArgumentMatchers.anyLong())).thenReturn(true);
        //When
        boolean result = competitionService.competitionExistByFootballId(234L);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testSaveCompetition() {
        //Given
        Competition competition = createCompetition();
        CompetitionDto tmpCompetitionDto = new CompetitionDto();
        Mockito.when(competitionRepository.save(ArgumentMatchers.any(Competition.class))).thenReturn(competition);
        //When
        CompetitionDto competitionDto = competitionService.saveCompetition(tmpCompetitionDto);
        //Then
        Assert.assertEquals(competition.getId(), competitionDto.getId());
        Assert.assertEquals(competition.getFootballId(), competitionDto.getFootballId());
        Assert.assertEquals(competition.getName(), competitionDto.getName());
    }

    @Test
    public void testGetCompetitionByFootballId() {
        //Given
        Competition competition = createCompetition();
        Mockito.when(competitionRepository.findByFootballId(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(competition));
        //When
        CompetitionDto competitionDto = competitionService.getCompetitionByFootballId(234L);
        //Then
        Assert.assertEquals(competition.getId(), competitionDto.getId());
        Assert.assertEquals(competition.getFootballId(), competitionDto.getFootballId());
        Assert.assertEquals(competition.getName(), competitionDto.getName());
    }

    @Test
    public void testGetCompetitionByFootballIdShouldThrowBetWinnerExceptionWhenCompetitionNotFound() {
        //Given
        Mockito.when(competitionRepository.findByFootballId(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        //When
        //Then
        Assertions.assertThrows(BetWinnerException.class, () -> competitionService.getCompetitionByFootballId(445L));
    }

    @Test
    public void testGetCompetitionByName() {
        //Given
        Competition competition = createCompetition();
        Mockito.when(competitionRepository.findByName(ArgumentMatchers.anyString())).thenReturn(
                Optional.ofNullable(competition));
        //When
        CompetitionDto competitionDto = competitionService.getByName("Bundesliga");
        //Then
        Assert.assertEquals(competition.getId(), competitionDto.getId());
        Assert.assertEquals(competition.getFootballId(), competitionDto.getFootballId());
        Assert.assertEquals(competition.getName(), competitionDto.getName());
    }

    private Competition createCompetition() {
        return Competition.builder()
                .id(2409689L)
                .footballId(95937L)
                .name("Test Competition")
                .competitionSeasonList(new ArrayList<>())
                .build();
    }
}