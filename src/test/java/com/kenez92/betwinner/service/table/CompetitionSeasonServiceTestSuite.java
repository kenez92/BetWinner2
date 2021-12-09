package com.kenez92.betwinner.service.table;

import com.kenez92.betwinner.competitionSeason.CompetitionSeasonService;
import com.kenez92.betwinner.competitions.CompetitionDto;
import com.kenez92.betwinner.competitionSeason.CompetitionSeasonDto;
import com.kenez92.betwinner.competitions.Competition;
import com.kenez92.betwinner.competitionSeason.CompetitionSeason;
import com.kenez92.betwinner.currentMatchDay.CurrentMatchDay;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.competitionSeason.CompetitionSeasonRepository;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CompetitionSeasonServiceTestSuite {
    @Autowired
    private CompetitionSeasonService competitionSeasonService;
    @MockBean
    private CompetitionSeasonRepository competitionSeasonRepository;

    @Test
    public void testCompetitionSeasonExistsByFootballId() {
        //Given
        Mockito.when(competitionSeasonRepository.existsByFootballId(ArgumentMatchers.anyLong()))
                .thenReturn(true);
        //When
        boolean result = competitionSeasonService.competitionSeasonExistByFootballId(23423L);
        //Then
        Assert.assertTrue(result);
    }

    @Test
    public void testSaveCompetitionSeason() {
        //Given
        CompetitionSeason competitionSeason = createCompetitionSeason();
        CompetitionSeasonDto tmpCompetitionSeasonDto = CompetitionSeasonDto.builder()
                .competition(new CompetitionDto())
                .build();
        Mockito.when(competitionSeasonRepository.save(ArgumentMatchers.any(CompetitionSeason.class)))
                .thenReturn(competitionSeason);
        //When
        CompetitionSeasonDto competitionSeasonDto = competitionSeasonService.saveCompetitionSeason(tmpCompetitionSeasonDto);
        //Then
        Assert.assertEquals(competitionSeason.getId(), competitionSeasonDto.getId());
        Assert.assertEquals(competitionSeason.getFootballId(), competitionSeasonDto.getFootballId());
        Assert.assertEquals(competitionSeason.getStartDate(), competitionSeasonDto.getStartDate());
        Assert.assertEquals(competitionSeason.getEndDate(), competitionSeasonDto.getEndDate());
        Assert.assertEquals(competitionSeason.getWinner(), competitionSeasonDto.getWinner());
        Assert.assertEquals(competitionSeason.getCompetition().getId(), competitionSeasonDto.getCompetition().getId());
        Assert.assertEquals(competitionSeason.getCurrentMatchDayList().size(), competitionSeasonDto.getCurrentMatchDayList().size());
    }

    @Test
    public void testGetCompetitionSeasonByFootballId() {
        //Given
        CompetitionSeason competitionSeason = createCompetitionSeason();
        Mockito.when(competitionSeasonRepository.findByFootballId(ArgumentMatchers.anyLong()))
                .thenReturn(java.util.Optional.ofNullable(competitionSeason));
        //When
        CompetitionSeasonDto competitionSeasonDto = competitionSeasonService.getCompetitionSeasonByFootballId(234L);
        //Then
        Assert.assertEquals(competitionSeason.getId(), competitionSeasonDto.getId());
        Assert.assertEquals(competitionSeason.getFootballId(), competitionSeasonDto.getFootballId());
        Assert.assertEquals(competitionSeason.getStartDate(), competitionSeasonDto.getStartDate());
        Assert.assertEquals(competitionSeason.getEndDate(), competitionSeasonDto.getEndDate());
        Assert.assertEquals(competitionSeason.getWinner(), competitionSeasonDto.getWinner());
        Assert.assertEquals(competitionSeason.getCompetition().getId(), competitionSeasonDto.getCompetition().getId());
        Assert.assertEquals(competitionSeason.getCurrentMatchDayList().size(), competitionSeasonDto.getCurrentMatchDayList().size());
    }

    @Test
    public void testGetCompetitionSeasonByFootballIdShouldThrowExceptionWhenCompetitionSeasonNotFound() {
        //Given
        Mockito.when(competitionSeasonRepository.findByFootballId(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());
        //When
        //Then
        Assertions.assertThrows(BetWinnerException.class, () -> competitionSeasonService.getCompetitionSeasonByFootballId(143L));

    }

    @Test
    public void testGetCompetitionSeasons() {
        //Given
        List<CompetitionSeason> competitionSeasons = new ArrayList<>();
        competitionSeasons.add(createCompetitionSeason());
        competitionSeasons.add(createCompetitionSeason());
        Mockito.when(competitionSeasonRepository.findAll()).thenReturn(competitionSeasons);
        //When
        List<CompetitionSeasonDto> competitionSeasonDtoList = competitionSeasonService.getCompetitionSeasons();
        //Then
        Assert.assertEquals(competitionSeasons.size(), competitionSeasonDtoList.size());
    }

    @Test
    public void testGetCompetitionSeason() {
        //Given
        CompetitionSeason competitionSeason = createCompetitionSeason();
        Mockito.when(competitionSeasonRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(competitionSeason));
        //When
        CompetitionSeasonDto competitionSeasonDto = competitionSeasonService.getCompetitionSeason(234L);
        //Then
        Assert.assertEquals(competitionSeason.getId(), competitionSeasonDto.getId());
        Assert.assertEquals(competitionSeason.getFootballId(), competitionSeasonDto.getFootballId());
        Assert.assertEquals(competitionSeason.getStartDate(), competitionSeasonDto.getStartDate());
        Assert.assertEquals(competitionSeason.getEndDate(), competitionSeasonDto.getEndDate());
        Assert.assertEquals(competitionSeason.getWinner(), competitionSeasonDto.getWinner());
        Assert.assertEquals(competitionSeason.getCompetition().getId(), competitionSeasonDto.getCompetition().getId());
        Assert.assertEquals(competitionSeason.getCurrentMatchDayList().size(), competitionSeasonDto.getCurrentMatchDayList().size());
    }

    @Test
    public void testGetCompetitionSeasonShouldThrowExceptionWhenCompetitionSeasonNotFound() {
        //Given
        Mockito.when(competitionSeasonRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        //When
        //Then
        Assertions.assertThrows(BetWinnerException.class, () -> competitionSeasonService.getCompetitionSeason(234L));
    }

    public CompetitionSeason createCompetitionSeason() {
        List<CurrentMatchDay> currentMatchDayList = new ArrayList<>();
        currentMatchDayList.add(createCurrentMatchDay());
        currentMatchDayList.add(createCurrentMatchDay());
        currentMatchDayList.add(createCurrentMatchDay());
        return CompetitionSeason.builder()
                .id(23423L)
                .footballId(252323L)
                .startDate(LocalDate.now().minusYears(1))
                .endDate(LocalDate.now())
                .winner("WINNER")
                .competition(createCompetition())
                .currentMatchDayList(currentMatchDayList)
                .build();
    }

    public Competition createCompetition() {
        return Competition.builder()
                .id(2342L)
                .build();
    }

    public CurrentMatchDay createCurrentMatchDay() {
        return CurrentMatchDay.builder()
                .id(2342111L)
                .build();
    }
}
