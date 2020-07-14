package com.kenez92.betwinner.entity.table;

import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.repository.table.CompetitionRepository;
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
public class CompetitionTestSuite {
    private static final String COMPETITION = "EKSTRAKLASA";

    @Autowired
    private CompetitionRepository competitionRepository;

    @Test
    public void testFindById() {
        //Given
        Competition competition = competitionRepository.save(createCompetition());
        //When
        Competition dbCompetition = competitionRepository.findById(competition.getId()).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_COMPETITION_NOT_FOUND_EXCEPTION));
        //Then
        try {
            Assert.assertEquals(competition.getId(), dbCompetition.getId());
            Assert.assertEquals(-1L, dbCompetition.getFootballId(), 0.001);
            Assert.assertEquals(COMPETITION, dbCompetition.getName());
        } finally {
            competitionRepository.deleteById(competition.getId());
        }
    }

    @Test
    public void testFindByIdShouldBeEmpty() {
        //Given
        //When
        Optional<Competition> dbCompetition = competitionRepository.findById(-1L);
        //Then
        Assert.assertFalse(dbCompetition.isPresent());
    }

    @Test
    public void testFindAll() {
        //Given
        Competition competition = competitionRepository.save(createCompetition());
        //When
        List<Competition> competitionList = competitionRepository.findAll();
        //Then
        try {
            Assert.assertTrue(competitionList.size() >= 1);
        } finally {
            competitionRepository.deleteById(competition.getId());
        }
    }

    @Test
    public void testSave() {
        //Given
        Competition competition = createCompetition();
        //When
        Competition dbCompetition = competitionRepository.save(competition);
        //Then
        try {
            Assert.assertNotNull(dbCompetition.getId());
            Assert.assertEquals(-1L, dbCompetition.getFootballId(), 0.001);
            Assert.assertEquals(COMPETITION, dbCompetition.getName());
        } finally {
            competitionRepository.deleteById(dbCompetition.getId());
        }
    }

    @Test
    public void testUpdate() {
        //Given
        Competition competition = competitionRepository.save(createCompetition());
        Competition updatedCompetition = Competition.builder()
                .id(competition.getId())
                .footballId(-2L)
                .name(COMPETITION)
                .build();
        //When
        Competition dbCompetition = competitionRepository.save(updatedCompetition);
        //Then
        try {
            Assert.assertEquals(competition.getId(), dbCompetition.getId());
            Assert.assertEquals(-2L, dbCompetition.getFootballId(), 0.001);
            Assert.assertEquals(COMPETITION, dbCompetition.getName());
        } finally {
            competitionRepository.deleteById(competition.getId());
        }
    }

    private Competition createCompetition() {
        return Competition.builder()
                .footballId(-1L)
                .name(COMPETITION)
                .build();
    }
}