package com.kenez92.betwinner.persistence.entity.table;

import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.persistence.repository.table.CompetitionSeasonRepository;
import com.kenez92.betwinner.persistence.repository.table.CurrentMatchDayRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrentMatchDayTestSuite {
    private static final String WINNER = "Winner";

    @Autowired
    private CompetitionSeasonRepository competitionSeasonRepository;

    @Autowired
    private CurrentMatchDayRepository currentMatchDayRepository;

    @Test
    public void testFindById() {
        //Given
        CompetitionSeason competitionSeason = competitionSeasonRepository.save(createCompetitionSeason());
        CurrentMatchDay currentMatchDay = currentMatchDayRepository.save(createCurrentMatchDay(competitionSeason));
        //When
        CurrentMatchDay dbCurrentMatchDay = currentMatchDayRepository.findById(currentMatchDay.getId()).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_CURRENT_MATCH_DAY_NOT_FOUND_EXCEPTION));
        //Then
        try {
            Assert.assertEquals(currentMatchDay.getId(), dbCurrentMatchDay.getId());
            Assert.assertEquals(99, dbCurrentMatchDay.getMatchDay(), 0.001);
            Assert.assertEquals(competitionSeason.getId(), dbCurrentMatchDay.getCompetitionSeason().getId());

        } finally {
            currentMatchDayRepository.deleteById(currentMatchDay.getId());
            competitionSeasonRepository.deleteById(competitionSeason.getId());
        }
    }

    @Test
    public void testFindByIdShouldBeEmpty() {
        //Given
        //When
        Optional<CurrentMatchDay> dbCurrentMatchDay = currentMatchDayRepository.findById(-1L);
        //Then
        Assert.assertFalse(dbCurrentMatchDay.isPresent());
    }

    @Test
    public void testSave() {
        //Given
        CompetitionSeason competitionSeason = competitionSeasonRepository.save(createCompetitionSeason());
        //When
        CurrentMatchDay dbCurrentMatchDay = currentMatchDayRepository.save(createCurrentMatchDay(competitionSeason));
        //Then
        try {
            Assert.assertNotNull(dbCurrentMatchDay.getId());
            Assert.assertEquals(99, dbCurrentMatchDay.getMatchDay(), 0.001);
            Assert.assertEquals(competitionSeason.getId(), dbCurrentMatchDay.getCompetitionSeason().getId());

        } finally {
            currentMatchDayRepository.deleteById(dbCurrentMatchDay.getId());
            competitionSeasonRepository.deleteById(competitionSeason.getId());
        }
    }

    @Test
    public void testUpdated() {
        //Given
        CompetitionSeason competitionSeason = competitionSeasonRepository.save(createCompetitionSeason());
        CurrentMatchDay currentMatchDay = currentMatchDayRepository.save(createCurrentMatchDay(competitionSeason));
        CurrentMatchDay updatedCurrentMatchDay = CurrentMatchDay.builder()
                .id(currentMatchDay.getId())
                .matchDay(101)
                .competitionSeason(competitionSeason)
                .build();
        //When
        CurrentMatchDay dbCurrentMatchDay = currentMatchDayRepository.save(updatedCurrentMatchDay);
        //Then
        try {
            Assert.assertEquals(currentMatchDay.getId(), dbCurrentMatchDay.getId());
            Assert.assertEquals(101, dbCurrentMatchDay.getMatchDay(), 0.001);
            Assert.assertEquals(competitionSeason.getId(), dbCurrentMatchDay.getCompetitionSeason().getId());

        } finally {
            currentMatchDayRepository.deleteById(currentMatchDay.getId());
            competitionSeasonRepository.deleteById(competitionSeason.getId());
        }
    }

    private CurrentMatchDay createCurrentMatchDay(CompetitionSeason competitionSeason) {
        return CurrentMatchDay.builder()
                .matchDay(99)
                .competitionSeason(competitionSeason)
                .build();
    }

    private CompetitionSeason createCompetitionSeason() {
        return CompetitionSeason.builder()
                .footballId(-1L)
                .startDate(LocalDate.of(1550, 3, 3))
                .endDate(LocalDate.of(1551, 4, 4))
                .winner(WINNER)
                .build();
    }
}