package com.kenez92.betwinner.service.table;

import com.kenez92.betwinner.domain.table.CompetitionDto;
import com.kenez92.betwinner.domain.table.CompetitionSeasonDto;
import com.kenez92.betwinner.domain.table.CompetitionTableDto;
import com.kenez92.betwinner.domain.table.CurrentMatchDayDto;
import com.kenez92.betwinner.persistence.entity.table.Competition;
import com.kenez92.betwinner.persistence.entity.table.CompetitionSeason;
import com.kenez92.betwinner.persistence.entity.table.CompetitionTable;
import com.kenez92.betwinner.persistence.entity.table.CurrentMatchDay;
import com.kenez92.betwinner.exception.BetWinnerException;
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
public class CompetitionTableServiceTestSuite {
    @Autowired
    private CompetitionTableService competitionTableService;

    @MockBean
    private CompetitionTableRepository competitionTableRepository;

    @Test
    public void testGetCompetitionTables() {
        //Given
        List<CompetitionTable> competitionTables = new ArrayList<>();
        competitionTables.add(createCompetitionTable());
        competitionTables.add(createCompetitionTable());
        competitionTables.add(createCompetitionTable());
        Mockito.when(competitionTableRepository.findAll()).thenReturn(competitionTables);
        //When
        List<CompetitionTableDto> competitionTableDtoList = competitionTableService.getCompetitionTables();
        //Then
        Assert.assertEquals(competitionTables.size(), competitionTableDtoList.size());
    }

    @Test
    public void testGetCompetitionTable() {
        //Given
        CompetitionTable competitionTable = createCompetitionTable();
        Mockito.when(competitionTableRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(competitionTable));
        //When
        CompetitionTableDto competitionTableDto = competitionTableService.getCompetitionTable(2184L);
        //Then
        Assert.assertEquals(competitionTable.getId(), competitionTableDto.getId());
        Assert.assertEquals(competitionTable.getStage(), competitionTableDto.getStage());
        Assert.assertEquals(competitionTable.getType(), competitionTableDto.getType());
        Assert.assertEquals(competitionTable.getCurrentMatchDay().getId(), competitionTableDto.getCurrentMatchDay().getId());
        Assert.assertEquals(competitionTable.getCompetitionTableElements().size(),
                competitionTableDto.getCompetitionTableElements().size());
    }

    @Test
    public void testGetCompetitionTableShouldThrowBetWinnerExceptionWhenNotFound() {
        //Given
        Mockito.when(competitionTableRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());
        //When
        //Then
        Assertions.assertThrows(BetWinnerException.class, () -> competitionTableService.getCompetitionTable(456L));
    }

    @Test
    public void testExistsByFields() {
        //Given
        Mockito.when(competitionTableRepository.existsByStageAndTypeAndCurrentMatchDay(ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(), ArgumentMatchers.any(CurrentMatchDay.class))).thenReturn(true);
        //When
        boolean result = competitionTableService
                .existsByFields("Test stage", "Test type", createCurrentMatchDayDto());
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testSaveCompetitionTable() {
        //Given
        CompetitionTable competitionTable = createCompetitionTable();
        Mockito.when(competitionTableRepository.save(ArgumentMatchers.any(CompetitionTable.class)))
                .thenReturn(competitionTable);
        //When
        CompetitionTableDto competitionTableDto = competitionTableService.saveCompetitionTable(createCompetitionTableDto());
        //Then
        Assert.assertEquals(competitionTable.getId(), competitionTableDto.getId());
        Assert.assertEquals(competitionTable.getStage(), competitionTableDto.getStage());
        Assert.assertEquals(competitionTable.getType(), competitionTableDto.getType());
        Assert.assertEquals(competitionTable.getCurrentMatchDay().getId(), competitionTableDto.getCurrentMatchDay().getId());
        Assert.assertEquals(competitionTable.getCompetitionTableElements().size(),
                competitionTableDto.getCompetitionTableElements().size());
    }

    @Test
    public void testGetByFields() {
        //Given
        CompetitionTable competitionTable = createCompetitionTable();
        Mockito.when(competitionTableRepository.findByStageAndTypeAndCurrentMatchDay(ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(), ArgumentMatchers.any(CurrentMatchDay.class)))
                .thenReturn(Optional.ofNullable(competitionTable));
        //When
        CompetitionTableDto competitionTableDto = competitionTableService.getByFields("Test Stage",
                "Test Type", createCurrentMatchDayDto());
        //Then
        Assert.assertEquals(competitionTable.getId(), competitionTableDto.getId());
        Assert.assertEquals(competitionTable.getStage(), competitionTableDto.getStage());
        Assert.assertEquals(competitionTable.getType(), competitionTableDto.getType());
        Assert.assertEquals(competitionTable.getCurrentMatchDay().getId(), competitionTableDto.getCurrentMatchDay().getId());
        Assert.assertEquals(competitionTable.getCompetitionTableElements().size(),
                competitionTableDto.getCompetitionTableElements().size());
    }

    @Test
    public void testGetByFieldsShouldThrowBetWinnerExceptionWhenNotFound() {
        //Given
        Mockito.when(competitionTableRepository.findByStageAndTypeAndCurrentMatchDay(ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(), ArgumentMatchers.any(CurrentMatchDay.class))).thenReturn(Optional.empty());
        //When
        //Then
        Assertions.assertThrows(BetWinnerException.class, () -> competitionTableService.getByFields("Test",
                "Test", createCurrentMatchDayDto()));
    }


    private CompetitionTable createCompetitionTable() {
        return CompetitionTable.builder()
                .id(234L)
                .stage("Test stage")
                .type("Test type")
                .currentMatchDay(createCurrentMatchDay())
                .competitionTableElements(new ArrayList<>())
                .build();
    }

    private CurrentMatchDay createCurrentMatchDay() {
        return CurrentMatchDay.builder()
                .id(23444L)
                .matchDay(2)
                .competitionSeason(createCompetitionSeason())
                .competitionTableList(new ArrayList<>())
                .build();
    }

    private CompetitionTableDto createCompetitionTableDto() {
        return CompetitionTableDto.builder()
                .id(234L)
                .stage("Test stage")
                .type("Test type")
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

    private CompetitionSeason createCompetitionSeason() {
        return CompetitionSeason.builder()
                .id(24123L)
                .competition(createCompetition())
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

    private Competition createCompetition() {
        return Competition.builder()
                .id(234L)
                .footballId(253L)
                .name("Test Competition")
                .build();
    }
}