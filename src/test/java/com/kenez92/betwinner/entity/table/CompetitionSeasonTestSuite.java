package com.kenez92.betwinner.entity.table;

import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.repository.table.CompetitionRepository;
import com.kenez92.betwinner.repository.table.CompetitionSeasonRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompetitionSeasonTestSuite {
    private static final String WINNER = "Winner";
    private static final String COMPETITION = "Ekstraklasa";

    @Autowired
    private CompetitionSeasonRepository competitionSeasonRepository;

    @Autowired
    private CompetitionRepository competitionRepository;

    @Test
    public void testFindById() {
        //Given
        Competition competition = competitionRepository.save(createCompetition());
        CompetitionSeason competitionSeason = createCompetitionSeason(competition);
        CompetitionSeason createdCompetitionSeason = competitionSeasonRepository.save(competitionSeason);
        Long competitionSeasonId = createdCompetitionSeason.getId();
        //When
        CompetitionSeason dbCompetitionSeason = competitionSeasonRepository.findById(createdCompetitionSeason.getId())
                .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_COMPETITION_SEASON_NOT_FOUND_EXCEPTION));
        //Then
        try {
            Assert.assertEquals(competitionSeasonId, dbCompetitionSeason.getId());
            Assert.assertEquals(-1L, dbCompetitionSeason.getFootballId(), 0.001);
            Assert.assertEquals(LocalDate.of(1550, 3, 3), dbCompetitionSeason.getStartDate());
            Assert.assertEquals(LocalDate.of(1551, 4, 4), dbCompetitionSeason.getEndDate());
            Assert.assertEquals(WINNER, dbCompetitionSeason.getWinner());
            Assert.assertEquals(competition.getId(), dbCompetitionSeason.getCompetition().getId(), 0.001);
        } finally {
            competitionSeasonRepository.deleteById(competitionSeasonId);
            competitionRepository.deleteById(competition.getId());
        }
    }

    @Test
    public void testFindByIdShouldBeEmpty() {
        //Given
        //When
        Optional<CompetitionSeason> dbCompetitionSeason = competitionSeasonRepository.findById(-1L);
        //Then
        Assert.assertFalse(dbCompetitionSeason.isPresent());
    }

    @Test
    public void testFindAll() {
        //Given
        Competition competition = competitionRepository.save(createCompetition());
        CompetitionSeason competitionSeason = createCompetitionSeason(competition);
        CompetitionSeason createdCompetitionSeason = competitionSeasonRepository.save(competitionSeason);
        Long competitionSeasonId = createdCompetitionSeason.getId();
        //When
        List<CompetitionSeason> competitionSeasonList = competitionSeasonRepository.findAll();
        //Then
        try {
            Assert.assertTrue(competitionSeasonList.size() >= 1);
        } finally {
            competitionSeasonRepository.deleteById(competitionSeasonId);
            competitionRepository.deleteById(competition.getId());

        }
    }

    @Test
    public void testSave() {
        //Given
        Competition competition = competitionRepository.save(createCompetition());
        CompetitionSeason competitionSeason = createCompetitionSeason(competition);
        //When
        CompetitionSeason dbCompetitionSeason = competitionSeasonRepository.save(competitionSeason);
        //Then
        try {
            Assert.assertNotNull(dbCompetitionSeason.getId());
            Assert.assertEquals(-1L, dbCompetitionSeason.getFootballId(), 0.001);
            Assert.assertEquals(LocalDate.of(1550, 3, 3), dbCompetitionSeason.getStartDate());
            Assert.assertEquals(LocalDate.of(1551, 4, 4), dbCompetitionSeason.getEndDate());
            Assert.assertEquals(WINNER, dbCompetitionSeason.getWinner());
            Assert.assertEquals(competition.getId(), dbCompetitionSeason.getCompetition().getId(), 0.001);
        } finally {
            competitionSeasonRepository.deleteById(dbCompetitionSeason.getId());
            competitionRepository.deleteById(competition.getId());
        }
    }

    @Test
    public void testUpdate() {
        //Given
        Competition competition = competitionRepository.save(createCompetition());
        CompetitionSeason competitionSeason = createCompetitionSeason(competition);
        CompetitionSeason createdCompetitionSeason = competitionSeasonRepository.save(competitionSeason);
        Long competitionSeasonId = createdCompetitionSeason.getId();
        CompetitionSeason updatedCompetitionSeason = CompetitionSeason.builder()
                .id(competitionSeasonId)
                .footballId(-5L)
                .competition(competition)
                .startDate(LocalDate.of(1555, 4, 4))
                .endDate(LocalDate.of(1556, 5, 5))
                .winner(WINNER)
                .build();
        //When
        CompetitionSeason dbCompetitionSeason = competitionSeasonRepository.save(updatedCompetitionSeason);
        //Then
        try {
            Assert.assertEquals(competitionSeasonId, dbCompetitionSeason.getId());
            Assert.assertEquals(-5L, dbCompetitionSeason.getFootballId(), 0.001);
            Assert.assertEquals(LocalDate.of(1555, 4, 4), dbCompetitionSeason.getStartDate());
            Assert.assertEquals(LocalDate.of(1556, 5, 5), dbCompetitionSeason.getEndDate());
            Assert.assertEquals(WINNER, dbCompetitionSeason.getWinner());
            Assert.assertEquals(competition.getId(), dbCompetitionSeason.getCompetition().getId(), 0.001);
        } finally {
            competitionSeasonRepository.deleteById(competitionSeasonId);
            competitionRepository.deleteById(competition.getId());
        }
    }

    private CompetitionSeason createCompetitionSeason(Competition competition) {
        return CompetitionSeason.builder()
                .footballId(-1L)
                .competition(competition)
                .startDate(LocalDate.of(1550, 3, 3))
                .endDate(LocalDate.of(1551, 4, 4))
                .winner(WINNER)
                .build();
    }

    private Competition createCompetition() {
        return Competition.builder()
                .footballId(-2L)
                .name(COMPETITION)
                .build();
    }
}