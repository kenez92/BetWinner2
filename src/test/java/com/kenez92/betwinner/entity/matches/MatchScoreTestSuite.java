package com.kenez92.betwinner.entity.matches;

import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.repository.matches.MatchScoreRepository;
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
public class MatchScoreTestSuite {
    private final static String HOME_TEAM = "Home team";
    private final static String DURATION = "REGULAR";
    private final static String STATUS = "FINISHED";
    @Autowired
    private MatchScoreRepository matchScoreRepository;

    @Test
    public void testFindById() {
        //Given
        MatchScore matchScore = matchScoreRepository.save(createMatchScore());
        //When
        MatchScore dbMatchScore = matchScoreRepository.findById(matchScore.getId()).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_MATCH_NOT_FOUND_EXCEPTION));
        //Then
        try {
            Assert.assertEquals(matchScore.getId(), dbMatchScore.getId());
            Assert.assertEquals(123L, dbMatchScore.getMatchId(), 0.001);
            Assert.assertEquals(HOME_TEAM, dbMatchScore.getWinner());
            Assert.assertEquals(DURATION, dbMatchScore.getDuration());
            Assert.assertEquals(STATUS, dbMatchScore.getStatus());
            Assert.assertEquals(5, dbMatchScore.getFullTimeHomeTeam(), 0.001);
            Assert.assertEquals(5, dbMatchScore.getFullTimeAwayTeam(), 0.001);
            Assert.assertEquals(2, dbMatchScore.getHalfTimeHomeTeam(), 0.001);
            Assert.assertEquals(1, dbMatchScore.getHalfTimeAwayTeam(), 0.001);
            Assert.assertEquals(1, dbMatchScore.getExtraTimeHomeTeam(), 0.001);
            Assert.assertEquals(1, dbMatchScore.getExtraTimeAwayTeam(), 0.001);
            Assert.assertEquals(4, dbMatchScore.getPenaltiesHomeTeam(), 0.001);
            Assert.assertEquals(3, dbMatchScore.getPenaltiesAwayTeam(), 0.001);

        } finally {
            matchScoreRepository.deleteById(matchScore.getId());
        }
    }

    @Test
    public void testFindByIdShouldBeEmpty() {
        //Given
        //When
        Optional<MatchScore> matchScore = matchScoreRepository.findById(-1L);
        //Then
        Assert.assertFalse(matchScore.isPresent());
    }

    @Test
    public void testFindAll() {
        //Given
        MatchScore matchScore = matchScoreRepository.save(createMatchScore());
        //When
        List<MatchScore> matchScoreList = matchScoreRepository.findAll();
        //Then
        try {
            Assert.assertTrue(matchScoreList.size() >= 1);
        } finally {
            matchScoreRepository.deleteById(matchScore.getId());
        }
    }

    @Test
    public void testSaveMatchScore() {
        //Given
        MatchScore matchScore = createMatchScore();
        //When
        MatchScore dbMatchScore = matchScoreRepository.save(matchScore);
        //Then
        try {
            Assert.assertNotNull(dbMatchScore.getId());
            Assert.assertEquals(123L, dbMatchScore.getMatchId(), 0.001);
            Assert.assertEquals(HOME_TEAM, dbMatchScore.getWinner());
            Assert.assertEquals(DURATION, dbMatchScore.getDuration());
            Assert.assertEquals(STATUS, dbMatchScore.getStatus());
            Assert.assertEquals(5, dbMatchScore.getFullTimeHomeTeam(), 0.001);
            Assert.assertEquals(5, dbMatchScore.getFullTimeAwayTeam(), 0.001);
            Assert.assertEquals(2, dbMatchScore.getHalfTimeHomeTeam(), 0.001);
            Assert.assertEquals(1, dbMatchScore.getHalfTimeAwayTeam(), 0.001);
            Assert.assertEquals(1, dbMatchScore.getExtraTimeHomeTeam(), 0.001);
            Assert.assertEquals(1, dbMatchScore.getExtraTimeAwayTeam(), 0.001);
            Assert.assertEquals(4, dbMatchScore.getPenaltiesHomeTeam(), 0.001);
            Assert.assertEquals(3, dbMatchScore.getPenaltiesAwayTeam(), 0.001);
        } finally {
            matchScoreRepository.deleteById(dbMatchScore.getId());
        }
    }

    @Test
    public void testUpdateMatchScore() {
        //Given
        MatchScore matchScore = matchScoreRepository.save(createMatchScore());
        Long matchScoreId = matchScore.getId();
        MatchScore updatedMatchScore = MatchScore.builder()
                .id(matchScoreId)
                .matchId(122L)
                .winner(HOME_TEAM)
                .status(STATUS)
                .duration(DURATION)
                .fullTimeHomeTeam(1)
                .fullTimeAwayTeam(1)
                .halfTimeHomeTeam(1)
                .halfTimeAwayTeam(1)
                .extraTimeHomeTeam(1)
                .extraTimeAwayTeam(1)
                .penaltiesHomeTeam(2)
                .penaltiesAwayTeam(1)
                .build();
        //When
        MatchScore dbMatchScore = matchScoreRepository.save(updatedMatchScore);
        //Then
        try {
            Assert.assertEquals(matchScoreId, dbMatchScore.getId());
            Assert.assertEquals(122L, dbMatchScore.getMatchId(), 0.001);
            Assert.assertEquals(HOME_TEAM, dbMatchScore.getWinner());
            Assert.assertEquals(DURATION, dbMatchScore.getDuration());
            Assert.assertEquals(STATUS, dbMatchScore.getStatus());
            Assert.assertEquals(1, dbMatchScore.getFullTimeHomeTeam(), 0.001);
            Assert.assertEquals(1, dbMatchScore.getFullTimeAwayTeam(), 0.001);
            Assert.assertEquals(1, dbMatchScore.getHalfTimeHomeTeam(), 0.001);
            Assert.assertEquals(1, dbMatchScore.getHalfTimeAwayTeam(), 0.001);
            Assert.assertEquals(1, dbMatchScore.getExtraTimeHomeTeam(), 0.001);
            Assert.assertEquals(1, dbMatchScore.getExtraTimeAwayTeam(), 0.001);
            Assert.assertEquals(2, dbMatchScore.getPenaltiesHomeTeam(), 0.001);
            Assert.assertEquals(1, dbMatchScore.getPenaltiesAwayTeam(), 0.001);
        } finally {
            matchScoreRepository.deleteById(matchScoreId);
        }
    }

    private MatchScore createMatchScore() {
        return MatchScore.builder()
                .matchId(123L)
                .winner(HOME_TEAM)
                .status(STATUS)
                .duration(DURATION)
                .fullTimeHomeTeam(5)
                .fullTimeAwayTeam(5)
                .halfTimeHomeTeam(2)
                .halfTimeAwayTeam(1)
                .extraTimeHomeTeam(1)
                .extraTimeAwayTeam(1)
                .penaltiesHomeTeam(4)
                .penaltiesAwayTeam(3)
                .build();
    }
}