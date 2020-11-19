package com.kenez92.betwinner.persistence.entity.table;

import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.persistence.repository.table.CompetitionTableElementRepository;
import com.kenez92.betwinner.persistence.repository.table.CompetitionTableRepository;
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
public class CompetitionTableElementTestSuite {
    private static final String TYPE = "TOTAL";
    private static final String STAGE = "REGULAR_SEASON";
    private static final String NAME = "NAME";
    private static final String UPDATED_NAME = "UPDATED_NAME";

    @Autowired
    private CompetitionTableElementRepository competitionTableElementRepository;

    @Autowired
    private CompetitionTableRepository competitionTableRepository;

    @Test
    public void testFindById() {
        //Given
        CompetitionTable competitionTable = competitionTableRepository.save(createCompetitionTable());
        CompetitionTableElement competitionTableElement = competitionTableElementRepository
                .save(createCompetitionTableElement(competitionTable));
        CompetitionTableElement createdCompetitionTableElement = competitionTableElementRepository.save(competitionTableElement);
        //When
        CompetitionTableElement dbCompetitionTableElement = competitionTableElementRepository.findById(competitionTableElement.getId())
                .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_COMPETITION_TABLE_ELEMENT_NOT_FOUND_EXCEPTION));
        //Then
        try {
            Assert.assertEquals(createdCompetitionTableElement.getId(), dbCompetitionTableElement.getId());
            Assert.assertEquals(NAME, dbCompetitionTableElement.getName());
            Assert.assertEquals(12, dbCompetitionTableElement.getPosition(), 0.001);
            Assert.assertEquals(22, dbCompetitionTableElement.getPlayedGames(), 0.001);
            Assert.assertEquals(11, dbCompetitionTableElement.getWon(), 0.001);
            Assert.assertEquals(5, dbCompetitionTableElement.getDraw(), 0.001);
            Assert.assertEquals(6, dbCompetitionTableElement.getLost(), 0.001);
            Assert.assertEquals(38, dbCompetitionTableElement.getPoints(), 0.001);
            Assert.assertEquals(58, dbCompetitionTableElement.getGoalsFor(), 0.001);
            Assert.assertEquals(30, dbCompetitionTableElement.getGoalsAgainst(), 0.001);
            Assert.assertEquals(28, dbCompetitionTableElement.getGoalDifference(), 0.001);
            Assert.assertEquals(competitionTable.getId(), dbCompetitionTableElement.getCompetitionTable().getId());
        } finally {
            competitionTableElementRepository.deleteById(createdCompetitionTableElement.getId());
            competitionTableRepository.deleteById(competitionTable.getId());
        }
    }

    @Test
    public void testFindByIdShouldBeEmpty() {
        //Given
        //When
        Optional<CompetitionTableElement> competitionTableElement = competitionTableElementRepository.findById(-1L);
        //Then
        Assert.assertFalse(competitionTableElement.isPresent());
    }

    @Test
    public void testFindAll() {
        //Given
        CompetitionTable competitionTable = competitionTableRepository.save(createCompetitionTable());
        CompetitionTableElement competitionTableElement = competitionTableElementRepository
                .save(createCompetitionTableElement(competitionTable));
        CompetitionTableElement createdCompetitionTableElement = competitionTableElementRepository.save(competitionTableElement);
        //When
        List<CompetitionTableElement> competitionTableElementList = competitionTableElementRepository.findAll();
        //Then
        try {
            Assert.assertTrue(competitionTableElementList.size() >= 1);
        } finally {
            competitionTableElementRepository.deleteById(createdCompetitionTableElement.getId());
            competitionTableRepository.deleteById(competitionTable.getId());
        }
    }

    @Test
    public void testSave() {
        //Given
        CompetitionTable competitionTable = competitionTableRepository.save(createCompetitionTable());
        CompetitionTableElement competitionTableElement = competitionTableElementRepository
                .save(createCompetitionTableElement(competitionTable));
        //When
        CompetitionTableElement dbCompetitionTableElement = competitionTableElementRepository.save(competitionTableElement);
        //Then
        try {
            Assert.assertNotNull(dbCompetitionTableElement.getId());
            Assert.assertEquals(NAME, dbCompetitionTableElement.getName());
            Assert.assertEquals(12, dbCompetitionTableElement.getPosition(), 0.001);
            Assert.assertEquals(22, dbCompetitionTableElement.getPlayedGames(), 0.001);
            Assert.assertEquals(11, dbCompetitionTableElement.getWon(), 0.001);
            Assert.assertEquals(5, dbCompetitionTableElement.getDraw(), 0.001);
            Assert.assertEquals(6, dbCompetitionTableElement.getLost(), 0.001);
            Assert.assertEquals(38, dbCompetitionTableElement.getPoints(), 0.001);
            Assert.assertEquals(58, dbCompetitionTableElement.getGoalsFor(), 0.001);
            Assert.assertEquals(30, dbCompetitionTableElement.getGoalsAgainst(), 0.001);
            Assert.assertEquals(28, dbCompetitionTableElement.getGoalDifference(), 0.001);
            Assert.assertEquals(competitionTable.getId(), dbCompetitionTableElement.getCompetitionTable().getId());
        } finally {
            competitionTableElementRepository.deleteById(dbCompetitionTableElement.getId());
            competitionTableRepository.deleteById(competitionTable.getId());
        }
    }

    @Test
    public void testUpdate() {
        //Given
        CompetitionTable competitionTable = competitionTableRepository.save(createCompetitionTable());
        CompetitionTableElement competitionTableElement = competitionTableElementRepository
                .save(createCompetitionTableElement(competitionTable));
        CompetitionTableElement createdCompetitionTableElement = competitionTableElementRepository.save(competitionTableElement);
        CompetitionTableElement updatedCompetitionTableElement = CompetitionTableElement.builder()
                .id(createdCompetitionTableElement.getId())
                .name(UPDATED_NAME)
                .position(11)
                .playedGames(23)
                .won(12)
                .draw(5)
                .lost(6)
                .points(41)
                .goalsFor(60)
                .goalsAgainst(31)
                .goalDifference(29)
                .competitionTable(competitionTable)
                .build();
        //When
        CompetitionTableElement dbCompetitionTableElement = competitionTableElementRepository.save(updatedCompetitionTableElement);
        //Then
        try {
            Assert.assertEquals(createdCompetitionTableElement.getId(), dbCompetitionTableElement.getId());
            Assert.assertEquals(UPDATED_NAME, dbCompetitionTableElement.getName());
            Assert.assertEquals(11, dbCompetitionTableElement.getPosition(), 0.001);
            Assert.assertEquals(23, dbCompetitionTableElement.getPlayedGames(), 0.001);
            Assert.assertEquals(12, dbCompetitionTableElement.getWon(), 0.001);
            Assert.assertEquals(5, dbCompetitionTableElement.getDraw(), 0.001);
            Assert.assertEquals(6, dbCompetitionTableElement.getLost(), 0.001);
            Assert.assertEquals(41, dbCompetitionTableElement.getPoints(), 0.001);
            Assert.assertEquals(60, dbCompetitionTableElement.getGoalsFor(), 0.001);
            Assert.assertEquals(31, dbCompetitionTableElement.getGoalsAgainst(), 0.001);
            Assert.assertEquals(29, dbCompetitionTableElement.getGoalDifference(), 0.001);
            Assert.assertEquals(competitionTable.getId(), dbCompetitionTableElement.getCompetitionTable().getId());
        } finally {
            competitionTableElementRepository.deleteById(dbCompetitionTableElement.getId());
            competitionTableRepository.deleteById(competitionTable.getId());
        }

    }

    private CompetitionTable createCompetitionTable() {
        return CompetitionTable.builder()
                .stage(STAGE)
                .type(TYPE)
                .build();
    }

    private CompetitionTableElement createCompetitionTableElement(CompetitionTable competitionTable) {
        return CompetitionTableElement.builder()
                .competitionTable(competitionTable)
                .name(NAME)
                .position(12)
                .playedGames(22)
                .won(11)
                .draw(5)
                .lost(6)
                .points(38)
                .goalsFor(58)
                .goalsAgainst(30)
                .goalDifference(28)
                .build();
    }
}