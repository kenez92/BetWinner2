package com.kenez92.betwinner.service.table;

import com.kenez92.betwinner.domain.table.CompetitionDto;
import com.kenez92.betwinner.domain.table.CompetitionSeasonDto;
import com.kenez92.betwinner.domain.table.CurrentMatchDayDto;
import com.kenez92.betwinner.entity.table.CompetitionSeason;
import com.kenez92.betwinner.entity.table.CompetitionTable;
import com.kenez92.betwinner.entity.table.CurrentMatchDay;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.repository.table.CurrentMatchDayRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrentMatchDayServiceTestSuite {
    @Autowired
    private CurrentMatchDayService currentMatchDayService;

    @MockBean
    private CurrentMatchDayRepository currentMatchDayRepository;

    @Test
    public void testGetCurrentMatchDays() {
        //Given
        List<CurrentMatchDay> currentMatchDayList = new ArrayList<>();
        currentMatchDayList.add(createCurrentMatchDay());
        currentMatchDayList.add(createCurrentMatchDay());
        currentMatchDayList.add(createCurrentMatchDay());
        Mockito.when(currentMatchDayRepository.findAll()).thenReturn(currentMatchDayList);
        //When
        List<CurrentMatchDayDto> currentMatchDayDtoList = currentMatchDayService.getCurrentMatchDays();
        //Then
        Assert.assertEquals(currentMatchDayList.size(), currentMatchDayDtoList.size());
    }

    @Test
    public void testGetCurrentMatchDay() {
        //Given
        CurrentMatchDay currentMatchDay = createCurrentMatchDay();
        Mockito.when(currentMatchDayRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(currentMatchDay));
        //When
        CurrentMatchDayDto currentMatchDayDto = currentMatchDayService.getCurrentMatchDay(234L);
        //Then
        Assert.assertEquals(currentMatchDay.getId(), currentMatchDayDto.getId());
        Assert.assertEquals(currentMatchDay.getMatchDay(), currentMatchDayDto.getMatchDay());
        Assert.assertEquals(currentMatchDay.getCompetitionSeason().getId(), currentMatchDayDto.getCompetitionSeason().getId());
        Assert.assertEquals(currentMatchDay.getCompetitionTableList().size(), currentMatchDayDto.getCompetitionTableList().size());
    }

    @Test
    public void testGetCurrentMatchDayShouldThrowBetWinnerExceptionWhenNotFound() {
        //Given
        Mockito.when(currentMatchDayRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        //When
        //Then
        Assertions.assertThrows(BetWinnerException.class, () -> currentMatchDayService.getCurrentMatchDay(1478L));
    }

    @Test
    public void testCurrentMatchDayExistBySeasonAndMatchDay() {
        //Given
        Mockito.when(currentMatchDayRepository.existsByCompetitionSeasonAndMatchDay(ArgumentMatchers.any(CompetitionSeason.class),
                ArgumentMatchers.anyInt())).thenReturn(true);
        //When
        boolean result = currentMatchDayService.currentMatchDayExistBySeasonAndMatchDay(createCompetitionSeasonDto(), 22);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testSaveCurrentMatchDay() {
        //Given
        CurrentMatchDay currentMatchDay = createCurrentMatchDay();
        Mockito.when(currentMatchDayRepository.save(ArgumentMatchers.any(CurrentMatchDay.class))).thenReturn(currentMatchDay);
        //When
        CurrentMatchDayDto currentMatchDayDto = currentMatchDayService.saveCurrentMatchDay(createCurrentMatchDayDto());
        //Then
        Assert.assertEquals(currentMatchDay.getId(), currentMatchDayDto.getId());
        Assert.assertEquals(currentMatchDay.getMatchDay(), currentMatchDayDto.getMatchDay());
        Assert.assertEquals(currentMatchDay.getCompetitionSeason().getId(), currentMatchDayDto.getCompetitionSeason().getId());
        Assert.assertEquals(currentMatchDay.getCompetitionTableList().size(), currentMatchDayDto.getCompetitionTableList().size());
    }

    @Test
    public void testGetCurrentMatchDayBySeasonAndMatchDay() {
        //Given
        CurrentMatchDay currentMatchDay = createCurrentMatchDay();
        Mockito.when(currentMatchDayRepository.findByCompetitionSeasonAndMatchDay(ArgumentMatchers.any(CompetitionSeason.class),
                ArgumentMatchers.anyInt())).thenReturn(Optional.ofNullable(currentMatchDay));
        //When
        CurrentMatchDayDto currentMatchDayDto = currentMatchDayService.getCurrentMatchDayBySeasonAndMatchDay(createCompetitionSeasonDto(), 22);
        //Then
        Assert.assertEquals(currentMatchDay.getId(), currentMatchDayDto.getId());
        Assert.assertEquals(currentMatchDay.getMatchDay(), currentMatchDayDto.getMatchDay());
        Assert.assertEquals(currentMatchDay.getCompetitionSeason().getId(), currentMatchDayDto.getCompetitionSeason().getId());
        Assert.assertEquals(currentMatchDay.getCompetitionTableList().size(), currentMatchDayDto.getCompetitionTableList().size());
    }

    @Test
    public void testGetCurrentMatchDayBySeasonAndMatchDayShouldThrowExceptionWhenNotFound() {
        //Given
        Mockito.when(currentMatchDayRepository.findByCompetitionSeasonAndMatchDay(ArgumentMatchers.any(CompetitionSeason.class),
                ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        //When
        //Then
        Assertions.assertThrows(BetWinnerException.class, () -> currentMatchDayService
                .getCurrentMatchDayBySeasonAndMatchDay(createCompetitionSeasonDto(), 26));
    }


    private CurrentMatchDay createCurrentMatchDay() {
        List<CompetitionTable> competitionTableList = new ArrayList<>();
        competitionTableList.add(createCompetitionTable());
        competitionTableList.add(createCompetitionTable());
        competitionTableList.add(createCompetitionTable());
        return CurrentMatchDay.builder()
                .id(23484L)
                .matchDay(21)
                .competitionSeason(createCompetitionSeason())
                .competitionTableList(competitionTableList)
                .build();
    }

    private CurrentMatchDayDto createCurrentMatchDayDto() {
        return CurrentMatchDayDto.builder()
                .id(234234L)
                .competitionSeason(new CompetitionSeasonDto())
                .build();
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

    private CompetitionSeason createCompetitionSeason() {
        return CompetitionSeason.builder()
                .id(24123L)
                .build();
    }

    private CompetitionSeasonDto createCompetitionSeasonDto() {
        return CompetitionSeasonDto.builder()
                .id(2543L)
                .competition(new CompetitionDto())
                .build();
    }


}