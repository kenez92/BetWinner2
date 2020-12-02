package com.kenez92.betwinner.service.table;

import com.kenez92.betwinner.domain.table.CompetitionSeasonDto;
import com.kenez92.betwinner.domain.table.CurrentMatchDayDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.persistence.entity.table.CompetitionSeason;
import com.kenez92.betwinner.persistence.entity.table.CompetitionTable;
import com.kenez92.betwinner.persistence.entity.table.CurrentMatchDay;
import com.kenez92.betwinner.persistence.repository.table.CurrentMatchDayRepository;
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
        boolean result = currentMatchDayService.currentMatchDayExistBySeasonAndMatchDay(new CompetitionSeasonDto(), 22);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testSaveCurrentMatchDay() {
        //Given
        CurrentMatchDay currentMatchDay = createCurrentMatchDay();
        Mockito.when(currentMatchDayRepository.save(ArgumentMatchers.any(CurrentMatchDay.class))).thenReturn(currentMatchDay);
        //When
        CurrentMatchDayDto currentMatchDayDto = currentMatchDayService.saveCurrentMatchDay(new CurrentMatchDayDto());
        //Then
        Assert.assertEquals(currentMatchDay.getId(), currentMatchDayDto.getId());
        Assert.assertEquals(currentMatchDay.getMatchDay(), currentMatchDayDto.getMatchDay());
    }

    @Test
    public void testGetCurrentMatchDayBySeasonAndMatchDay() {
        //Given
        CurrentMatchDay currentMatchDay = createCurrentMatchDay();
        Mockito.when(currentMatchDayRepository.findByCompetitionSeasonAndMatchDay(ArgumentMatchers.any(CompetitionSeason.class),
                ArgumentMatchers.anyInt())).thenReturn(Optional.ofNullable(currentMatchDay));
        //When
        CurrentMatchDayDto currentMatchDayDto = currentMatchDayService.getCurrentMatchDayBySeasonAndMatchDay(new CompetitionSeasonDto(), 22);
        //Then
        Assert.assertEquals(currentMatchDay.getId(), currentMatchDayDto.getId());
        Assert.assertEquals(currentMatchDay.getMatchDay(), currentMatchDayDto.getMatchDay());
    }

    @Test
    public void testGetCurrentMatchDayBySeasonAndMatchDayShouldThrowExceptionWhenNotFound() {
        //Given
        Mockito.when(currentMatchDayRepository.findByCompetitionSeasonAndMatchDay(ArgumentMatchers.any(CompetitionSeason.class),
                ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
        //When
        //Then
        Assertions.assertThrows(BetWinnerException.class, () -> currentMatchDayService
                .getCurrentMatchDayBySeasonAndMatchDay(new CompetitionSeasonDto(), 26));
    }

    @Test
    public void testCurrentMatchDaysBySeasonId() {
        //Given
        List<CurrentMatchDay> currentMatchDayList = new ArrayList<>();
        currentMatchDayList.add(createCurrentMatchDay());
        currentMatchDayList.add(createCurrentMatchDay());
        Mockito.when(currentMatchDayRepository.findByCompetitionSeasonId(ArgumentMatchers.anyLong()))
                .thenReturn(currentMatchDayList);
        //When
        List<CurrentMatchDayDto> currentMatchDayDtoList = currentMatchDayService.getCurrentMatchDaysByCompetitionSeasonId(62L);
        //Then
        Assert.assertEquals(currentMatchDayList.size(), currentMatchDayDtoList.size());
    }


    private CurrentMatchDay createCurrentMatchDay() {
        return CurrentMatchDay.builder()
                .id(23484L)
                .matchDay(21)
                .competitionSeason(new CompetitionSeason())
                .competitionTableList(new ArrayList<>())
                .build();
    }
}