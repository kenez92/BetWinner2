package com.kenez92.betwinner.service.table;

import com.kenez92.betwinner.domain.table.*;
import com.kenez92.betwinner.persistence.entity.table.CompetitionTable;
import com.kenez92.betwinner.persistence.entity.table.CompetitionTableElement;
import com.kenez92.betwinner.persistence.entity.table.CurrentMatchDay;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.persistence.repository.table.CompetitionTableElementRepository;
import com.kenez92.betwinner.persistence.repository.table.CompetitionTableRepository;
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
public class CompetitionTableElementServiceTestSuite {
    @Autowired
    private CompetitionTableElementService competitionTableElementService;
    @MockBean
    private CompetitionTableElementRepository competitionTableElementRepository;
    @MockBean
    private CompetitionTableRepository competitionTableRepository;

    @Test
    public void testExistByNameAndCompetitionTable() {
        //Given
        Mockito.when(competitionTableElementRepository.existsByNameAndCompetitionTable(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(CompetitionTable.class))).thenReturn(true);
        //When
        boolean result = competitionTableElementService.existByNameAndCompetitionTable("Test",
                createCompetitionTableDto());
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testSaveCompetitionTableElement() {
        //Given
        CompetitionTableElement competitionTableElement = createCompetitionTableElement();
        Mockito.when(competitionTableElementRepository.save(ArgumentMatchers.any(CompetitionTableElement.class)))
                .thenReturn(competitionTableElement);
        //When
        CompetitionTableElementDto competitionTableElementDto = competitionTableElementService
                .saveCompetitionTableElement(createCompetitionTableElementDto());
        //Then
        Assert.assertEquals(competitionTableElement.getId(), competitionTableElementDto.getId());
        Assert.assertEquals(competitionTableElement.getCompetitionTable().getId(),
                competitionTableElementDto.getCompetitionTable().getId());
        Assert.assertEquals(competitionTableElement.getName(), competitionTableElementDto.getName());
        Assert.assertEquals(competitionTableElement.getPosition(), competitionTableElementDto.getPosition());
        Assert.assertEquals(competitionTableElement.getPlayedGames(), competitionTableElementDto.getPlayedGames());
        Assert.assertEquals(competitionTableElement.getWon(), competitionTableElementDto.getWon());
        Assert.assertEquals(competitionTableElement.getDraw(), competitionTableElementDto.getDraw());
        Assert.assertEquals(competitionTableElement.getLost(), competitionTableElementDto.getLost());
        Assert.assertEquals(competitionTableElement.getPoints(), competitionTableElementDto.getPoints());
        Assert.assertEquals(competitionTableElement.getGoalsFor(), competitionTableElementDto.getGoalsFor());
        Assert.assertEquals(competitionTableElement.getGoalsAgainst(), competitionTableElementDto.getGoalsAgainst());
        Assert.assertEquals(competitionTableElement.getGoalDifference(), competitionTableElementDto.getGoalDifference());
    }

    @Test
    public void testGetByNameAndCompetitionTable() {
        //Given
        CompetitionTableElement competitionTableElement = createCompetitionTableElement();
        Mockito.when(competitionTableRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(createCompetitionTable()));
        Mockito.when(competitionTableElementRepository.findByNameAndCompetitionTable(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(CompetitionTable.class))).thenReturn(Optional.ofNullable(competitionTableElement));
        //When
        CompetitionTableElementDto competitionTableElementDto = competitionTableElementService
                .getByNameAndCompetitionTable("Test", createCompetitionTableDto());
        //Then
        Assert.assertEquals(competitionTableElement.getId(), competitionTableElementDto.getId());
        Assert.assertEquals(competitionTableElement.getCompetitionTable().getId(),
                competitionTableElementDto.getCompetitionTable().getId());
        Assert.assertEquals(competitionTableElement.getName(), competitionTableElementDto.getName());
        Assert.assertEquals(competitionTableElement.getPosition(), competitionTableElementDto.getPosition());
        Assert.assertEquals(competitionTableElement.getPlayedGames(), competitionTableElementDto.getPlayedGames());
        Assert.assertEquals(competitionTableElement.getWon(), competitionTableElementDto.getWon());
        Assert.assertEquals(competitionTableElement.getDraw(), competitionTableElementDto.getDraw());
        Assert.assertEquals(competitionTableElement.getLost(), competitionTableElementDto.getLost());
        Assert.assertEquals(competitionTableElement.getPoints(), competitionTableElementDto.getPoints());
        Assert.assertEquals(competitionTableElement.getGoalsFor(), competitionTableElementDto.getGoalsFor());
        Assert.assertEquals(competitionTableElement.getGoalsAgainst(), competitionTableElementDto.getGoalsAgainst());
        Assert.assertEquals(competitionTableElement.getGoalDifference(), competitionTableElementDto.getGoalDifference());
    }

    @Test
    public void testGetByNameAndCompetitionTableShouldThrowBetWinnerExceptionWhenNotFound() {
        //Given
        Mockito.when(competitionTableRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());
        Mockito.when(competitionTableElementRepository.findByNameAndCompetitionTable(ArgumentMatchers.anyString(),
                ArgumentMatchers.any(CompetitionTable.class))).thenReturn(Optional.empty());
        //When
        //Then
        Assertions.assertThrows(BetWinnerException.class, () -> competitionTableElementService
                .getByNameAndCompetitionTable("Test", createCompetitionTableDto()));
    }

    @Test
    public void testGetByName() {
        //Given
        CompetitionTableElement competitionTableElement = createCompetitionTableElement();
        List<CompetitionTableElement> competitionTableElements = new ArrayList<>();
        competitionTableElements.add(competitionTableElement);
        Mockito.when(competitionTableElementRepository.findByName(ArgumentMatchers.anyString()))
                .thenReturn(competitionTableElements);
        //When
        List<CompetitionTableElementDto> competitionTableElementDtoList = competitionTableElementService.getByName("Test");
        CompetitionTableElementDto competitionTableElementDto = competitionTableElementDtoList.get(0);
        //Then
        Assert.assertEquals(1, competitionTableElementDtoList.size());
        Assert.assertEquals(competitionTableElement.getId(), competitionTableElementDto.getId());
        Assert.assertEquals(competitionTableElement.getCompetitionTable().getId(),
                competitionTableElementDto.getCompetitionTable().getId());
        Assert.assertEquals(competitionTableElement.getName(), competitionTableElementDto.getName());
        Assert.assertEquals(competitionTableElement.getPosition(), competitionTableElementDto.getPosition());
        Assert.assertEquals(competitionTableElement.getPlayedGames(), competitionTableElementDto.getPlayedGames());
        Assert.assertEquals(competitionTableElement.getWon(), competitionTableElementDto.getWon());
        Assert.assertEquals(competitionTableElement.getDraw(), competitionTableElementDto.getDraw());
        Assert.assertEquals(competitionTableElement.getLost(), competitionTableElementDto.getLost());
        Assert.assertEquals(competitionTableElement.getPoints(), competitionTableElementDto.getPoints());
        Assert.assertEquals(competitionTableElement.getGoalsFor(), competitionTableElementDto.getGoalsFor());
        Assert.assertEquals(competitionTableElement.getGoalsAgainst(), competitionTableElementDto.getGoalsAgainst());
        Assert.assertEquals(competitionTableElement.getGoalDifference(), competitionTableElementDto.getGoalDifference());
    }

    @Test
    public void testGetCompetitionTableElements() {
        //Given
        List<CompetitionTableElement> competitionTableElements = new ArrayList<>();
        competitionTableElements.add(createCompetitionTableElement());
        competitionTableElements.add(createCompetitionTableElement());
        competitionTableElements.add(createCompetitionTableElement());
        Mockito.when(competitionTableElementRepository.findAll()).thenReturn(competitionTableElements);
        //When
        List<CompetitionTableElementDto> competitionTableElementDtoList = competitionTableElementService
                .getCompetitionTableElements();
        //Then
        Assert.assertEquals(competitionTableElements.size(), competitionTableElementDtoList.size());
    }

    @Test
    public void testGetCompetitionTableElement() {
        //Given
        CompetitionTableElement competitionTableElement = createCompetitionTableElement();
        Mockito.when(competitionTableElementRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(competitionTableElement));
        //When
        CompetitionTableElementDto competitionTableElementDto = competitionTableElementService
                .getCompetitionTableElement(234L);
        //Then
        Assert.assertEquals(competitionTableElement.getId(), competitionTableElementDto.getId());
        Assert.assertEquals(competitionTableElement.getCompetitionTable().getId(),
                competitionTableElementDto.getCompetitionTable().getId());
        Assert.assertEquals(competitionTableElement.getName(), competitionTableElementDto.getName());
        Assert.assertEquals(competitionTableElement.getPosition(), competitionTableElementDto.getPosition());
        Assert.assertEquals(competitionTableElement.getPlayedGames(), competitionTableElementDto.getPlayedGames());
        Assert.assertEquals(competitionTableElement.getWon(), competitionTableElementDto.getWon());
        Assert.assertEquals(competitionTableElement.getDraw(), competitionTableElementDto.getDraw());
        Assert.assertEquals(competitionTableElement.getLost(), competitionTableElementDto.getLost());
        Assert.assertEquals(competitionTableElement.getPoints(), competitionTableElementDto.getPoints());
        Assert.assertEquals(competitionTableElement.getGoalsFor(), competitionTableElementDto.getGoalsFor());
        Assert.assertEquals(competitionTableElement.getGoalsAgainst(), competitionTableElementDto.getGoalsAgainst());
        Assert.assertEquals(competitionTableElement.getGoalDifference(), competitionTableElementDto.getGoalDifference());
    }

    @Test
    public void testGetCompetitionTableElementShouldThrowBetWinnerExceptionWhenNotFound() {
        //Given
        Mockito.when(competitionTableElementRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());
        //When
        //Then
        Assertions.assertThrows(BetWinnerException.class, () -> competitionTableElementService
                .getCompetitionTableElement(9494L));
    }

    private CompetitionTableElement createCompetitionTableElement() {
        return CompetitionTableElement.builder()
                .id(23554L)
                .competitionTable(createCompetitionTable())
                .name("Test Name")
                .position(234)
                .playedGames(22)
                .won(15)
                .draw(234)
                .lost(8)
                .points(55)
                .goalsFor(81)
                .goalsAgainst(49)
                .goalDifference(22)
                .build();
    }

    private CompetitionTable createCompetitionTable() {
        return CompetitionTable.builder()
                .id(9974L)
                .stage("Test Stage")
                .type("Test Type")
                .currentMatchDay(new CurrentMatchDay())
                .competitionTableElements(new ArrayList<>())
                .build();
    }

    private CompetitionTableElementDto createCompetitionTableElementDto() {
        return CompetitionTableElementDto.builder()
                .id(23554L)
                .competitionTable(createCompetitionTableDto())
                .name("Test Name")
                .position(234)
                .playedGames(22)
                .won(15)
                .draw(234)
                .lost(8)
                .points(55)
                .goalsFor(81)
                .goalsAgainst(49)
                .goalDifference(22)
                .build();
    }

    private CompetitionTableDto createCompetitionTableDto() {
        return CompetitionTableDto.builder()
                .id(9974L)
                .stage("Test Stage")
                .type("Test Type")
                .currentMatchDay(createCurrentMatchDayDto())
                .competitionTableElements(new ArrayList<>())
                .build();
    }

    private CurrentMatchDayDto createCurrentMatchDayDto() {
        return CurrentMatchDayDto.builder()
                .id(234234L)
                .competitionSeason(createCompetitionSeasonDto())
                .build();
    }

    private CompetitionSeasonDto createCompetitionSeasonDto() {
        return CompetitionSeasonDto.builder()
                .id(2543L)
                .competition(createCompetitionDto())
                .build();
    }

    private CompetitionDto createCompetitionDto() {
        return CompetitionDto.builder()
                .id(234L)
                .footballId(253L)
                .name("Test Competition")
                .build();
    }
}