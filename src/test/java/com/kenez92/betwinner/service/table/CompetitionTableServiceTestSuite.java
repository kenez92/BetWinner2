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
                .existsByFields("Test stage", "Test type", new CurrentMatchDayDto());
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
                "Test Type", new CurrentMatchDayDto());
        //Then
        Assert.assertEquals(competitionTable.getId(), competitionTableDto.getId());
        Assert.assertEquals(competitionTable.getStage(), competitionTableDto.getStage());
        Assert.assertEquals(competitionTable.getType(), competitionTableDto.getType());
    }

    @Test
    public void testGetByFieldsShouldThrowBetWinnerExceptionWhenNotFound() {
        //Given
        Mockito.when(competitionTableRepository.findByStageAndTypeAndCurrentMatchDay(ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(), ArgumentMatchers.any(CurrentMatchDay.class))).thenReturn(Optional.empty());
        //When
        //Then
        Assertions.assertThrows(BetWinnerException.class, () -> competitionTableService.getByFields("Test",
                "Test", new CurrentMatchDayDto()));
    }


    private CompetitionTable createCompetitionTable() {
        return CompetitionTable.builder()
                .id(234L)
                .stage("Test stage")
                .type("Test type")
                .currentMatchDay(new CurrentMatchDay())
                .competitionTableElements(new ArrayList<>())
                .build();
    }
    private CompetitionTableDto createCompetitionTableDto() {
        return CompetitionTableDto.builder()
                .id(234L)
                .stage("Test stage")
                .type("Test type")
                .currentMatchDay(new CurrentMatchDayDto())
                .competitionTableElements(new ArrayList<>())
                .build();
    }
}