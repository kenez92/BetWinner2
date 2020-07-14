package com.kenez92.betwinner.entity.table;

import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.repository.table.CompetitionTableRepository;
import com.kenez92.betwinner.repository.table.CurrentMatchDayRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompetitionTableTestSuite {
    private static final String TYPE = "TOTAL";
    private static final String UPDATED_TYPE = "HOME";
    private static final String STAGE = "REGULAR_SEASON";

    @Autowired
    private CompetitionTableRepository competitionTableRepository;

    @Autowired
    private CurrentMatchDayRepository currentMatchDayRepository;

    @Test
    public void testFindById() {
        //Given
        CurrentMatchDay currentMatchDay = currentMatchDayRepository.save(createCurrentMatchDay());
        CompetitionTable competitionTable = createCompetitionTable();
        competitionTable.setCurrentMatchDay(currentMatchDay);
        CompetitionTable createdCompetitionTable = competitionTableRepository.save(competitionTable);
        //When
        CompetitionTable dbCompetitionTable = competitionTableRepository.findById(createdCompetitionTable.getId())
                .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_COMPETITION_TABLE_NOT_FOUND_EXCEPTION));
        //Then
        try {
            Assert.assertEquals(createdCompetitionTable.getId(), dbCompetitionTable.getId());
            Assert.assertEquals(STAGE, dbCompetitionTable.getStage());
            Assert.assertEquals(TYPE, dbCompetitionTable.getType());
            Assert.assertEquals(currentMatchDay.getId(), dbCompetitionTable.getCurrentMatchDay().getId());
        } finally {
            competitionTableRepository.deleteById(createdCompetitionTable.getId());
            currentMatchDayRepository.deleteById(currentMatchDay.getId());
        }
    }

    @Test
    public void testFindByIdShouldBeEmpty() {
        //Given
        //When
        Optional<CompetitionTable> dbCompetitionTable = competitionTableRepository.findById(-123L);
        //Then
        Assert.assertFalse(dbCompetitionTable.isPresent());
    }

    @Test
    public void testFindAll() {
        //Given
        CurrentMatchDay currentMatchDay = currentMatchDayRepository.save(createCurrentMatchDay());
        CompetitionTable competitionTable = createCompetitionTable();
        competitionTable.setCurrentMatchDay(currentMatchDay);
        CompetitionTable createdCompetitionTable = competitionTableRepository.save(competitionTable);
        //When
        List<CompetitionTable> competitionTableList = competitionTableRepository.findAll();
        //Then
        try {
            Assert.assertTrue(competitionTableList.size() >= 1);
        } finally {
            competitionTableRepository.deleteById(createdCompetitionTable.getId());
            currentMatchDayRepository.deleteById(currentMatchDay.getId());
        }
    }

    @Test
    public void testSave() {
        //Given
        CurrentMatchDay currentMatchDay = currentMatchDayRepository.save(createCurrentMatchDay());
        CompetitionTable competitionTable = createCompetitionTable();
        competitionTable.setCurrentMatchDay(currentMatchDay);
        //When
        CompetitionTable dbCompetitionTable = competitionTableRepository.save(competitionTable);
        //Then
        try {
            Assert.assertNotNull(dbCompetitionTable.getId());
            Assert.assertEquals(STAGE, dbCompetitionTable.getStage());
            Assert.assertEquals(TYPE, dbCompetitionTable.getType());
            Assert.assertEquals(currentMatchDay.getId(), dbCompetitionTable.getCurrentMatchDay().getId());
        } finally {
            competitionTableRepository.deleteById(dbCompetitionTable.getId());
            currentMatchDayRepository.deleteById(currentMatchDay.getId());
        }
    }

    @Test
    public void testUpdate() {
        //Given
        CurrentMatchDay currentMatchDay = currentMatchDayRepository.save(createCurrentMatchDay());
        CompetitionTable competitionTable = createCompetitionTable();
        competitionTable.setCurrentMatchDay(currentMatchDay);
        CompetitionTable createdCompetitionTable = competitionTableRepository.save(competitionTable);
        CompetitionTable updatedCompetitionTable = CompetitionTable.builder()
                .id(createdCompetitionTable.getId())
                .stage(STAGE)
                .type(UPDATED_TYPE)
                .build();
        updatedCompetitionTable.setCurrentMatchDay(currentMatchDay);
        //When
        CompetitionTable dbCompetitionTable = competitionTableRepository.save(updatedCompetitionTable);
        //Then
        try {
            Assert.assertEquals(createdCompetitionTable.getId(), dbCompetitionTable.getId());
            Assert.assertEquals(STAGE, dbCompetitionTable.getStage());
            Assert.assertEquals(UPDATED_TYPE, dbCompetitionTable.getType());
            Assert.assertEquals(currentMatchDay.getId(), dbCompetitionTable.getCurrentMatchDay().getId());
        } finally {
            competitionTableRepository.deleteById(createdCompetitionTable.getId());
            currentMatchDayRepository.deleteById(currentMatchDay.getId());
        }
    }

    private CurrentMatchDay createCurrentMatchDay() {
        return CurrentMatchDay.builder()
                .matchDay(-2)
                .build();
    }

    private CompetitionTable createCompetitionTable() {
        return CompetitionTable.builder()
                .stage(STAGE)
                .type(TYPE)
                .build();
    }

}