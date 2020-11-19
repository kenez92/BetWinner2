package com.kenez92.betwinner.persistence.entity.matches;

import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.persistence.repository.matches.MatchDayRepository;
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
public class MatchDayTestSuite {

    @Autowired
    private MatchDayRepository matchDayRepository;

    @Test
    public void testFindById() {
        //Given
        MatchDay matchDay = matchDayRepository.save(createMatchDay());
        Long matchDayId = matchDay.getId();
        //When
        MatchDay dbMatchDay = matchDayRepository.findById(matchDayId).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_CURRENT_MATCH_DAY_NOT_FOUND_EXCEPTION));
        //Then
        try {
            Assert.assertEquals(matchDayId, dbMatchDay.getId());
            Assert.assertEquals(matchDay.getLocalDate(), dbMatchDay.getLocalDate());
        } finally {
            matchDayRepository.deleteById(matchDayId);
        }
    }

    @Test
    public void testSaveMatchDay() {
        //Given
        //When
        MatchDay matchDay = matchDayRepository.save(createMatchDay());
        //Then
        try {
            Assert.assertNotNull(matchDay);
            Assert.assertEquals(LocalDate.of(1550, 3, 20), matchDay.getLocalDate());
        } finally {
            matchDayRepository.deleteById(matchDay.getId());
        }
    }

    @Test
    public void testUpdateMatchDay() {
        //Given
        MatchDay matchDay = matchDayRepository.save(createMatchDay());
        Long matchDayId = matchDay.getId();
        MatchDay updatedMatchDay = MatchDay.builder()
                .id(matchDayId)
                .localDate(LocalDate.of(1551, 3, 21))
                .build();
        //When
        MatchDay dbMatchDay = matchDayRepository.save(updatedMatchDay);
        //Then
        try {
            Assert.assertNotNull(dbMatchDay);
            Assert.assertEquals(matchDayId, dbMatchDay.getId());
            Assert.assertEquals(LocalDate.of(1551, 3, 21), dbMatchDay.getLocalDate());
        } finally {
            matchDayRepository.deleteById(matchDayId);
        }
    }

    @Test
    public void testFindByIdShouldBeEmpty() {
        //Given
        //When
        Optional<MatchDay> matchDay = matchDayRepository.findById(-1L);
        //Then
        Assert.assertFalse(matchDay.isPresent());
    }

    private MatchDay createMatchDay() {
        return MatchDay.builder()
                .localDate(LocalDate.of(1550, 3, 20))
                .build();
    }
}