package com.kenez92.betwinner.service.matches;

import com.kenez92.betwinner.domain.matches.MatchScoreDto;
import com.kenez92.betwinner.persistence.entity.matches.MatchScore;
import com.kenez92.betwinner.persistence.repository.matches.MatchScoreRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class MatchScoreServiceTestSuite {

    @Autowired
    private MatchScoreService matchScoreService;

    @MockBean
    private MatchScoreRepository matchScoreRepository;

    @Test
    public void testGetMatchScores() {
        //Given
        List<MatchScore> matchScoreList = new ArrayList<>();
        matchScoreList.add(createMatchScore());
        matchScoreList.add(createMatchScore());
        Mockito.when(matchScoreRepository.findAll()).thenReturn(matchScoreList);
        //When
        List<MatchScoreDto> matchScoreDtoList = matchScoreService.getMatchScores();
        //Then
        Assert.assertEquals(matchScoreList.size(), matchScoreDtoList.size());
    }

    @Test
    public void testGetMatchScore() {
        //Given
        MatchScore matchScore = createMatchScore();
        Mockito.when(matchScoreRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.ofNullable(matchScore));
        //When
        MatchScoreDto matchScoreDto = matchScoreService.getMatchScore(234234L);
        //Then
        Assert.assertEquals(matchScore.getId(), matchScoreDto.getId());
        Assert.assertEquals(matchScore.getMatchId(), matchScoreDto.getMatchId());
        Assert.assertEquals(matchScore.getWinner(), matchScoreDto.getWinner());
        Assert.assertEquals(matchScore.getStatus(), matchScoreDto.getStatus());
        Assert.assertEquals(matchScore.getFullTimeHomeTeam(), matchScoreDto.getFullTimeHomeTeam());
        Assert.assertEquals(matchScore.getFullTimeAwayTeam(), matchScoreDto.getFullTimeAwayTeam());
        Assert.assertEquals(matchScore.getHalfTimeHomeTeam(), matchScoreDto.getHalfTimeHomeTeam());
        Assert.assertEquals(matchScore.getHalfTimeAwayTeam(), matchScoreDto.getHalfTimeAwayTeam());
    }

    @Test
    public void testCreateMatchScore() {
        //Given
        MatchScore matchScore = createMatchScore();
        Mockito.when(matchScoreRepository.save(ArgumentMatchers.any(MatchScore.class)))
                .thenReturn(matchScore);
        //When
        MatchScoreDto matchScoreDto = matchScoreService.saveMatchScore(new MatchScoreDto());
        //Then
        Assert.assertEquals(matchScore.getId(), matchScoreDto.getId());
        Assert.assertEquals(matchScore.getMatchId(), matchScoreDto.getMatchId());
        Assert.assertEquals(matchScore.getWinner(), matchScoreDto.getWinner());
        Assert.assertEquals(matchScore.getStatus(), matchScoreDto.getStatus());
        Assert.assertEquals(matchScore.getFullTimeHomeTeam(), matchScoreDto.getFullTimeHomeTeam());
        Assert.assertEquals(matchScore.getFullTimeAwayTeam(), matchScoreDto.getFullTimeAwayTeam());
        Assert.assertEquals(matchScore.getHalfTimeHomeTeam(), matchScoreDto.getHalfTimeHomeTeam());
        Assert.assertEquals(matchScore.getHalfTimeAwayTeam(), matchScoreDto.getHalfTimeAwayTeam());
    }

    @Test
    public void testGetMatchScoreByMatchId() {
        //Given
        MatchScore matchScore = createMatchScore();
        Mockito.when(matchScoreRepository.findByMatchId(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(matchScore));
        //When
        MatchScoreDto matchScoreDto = matchScoreService.getByMatchId(234234L);
        //Then
        Assert.assertEquals(matchScore.getId(), matchScoreDto.getId());
        Assert.assertEquals(matchScore.getMatchId(), matchScoreDto.getMatchId());
        Assert.assertEquals(matchScore.getWinner(), matchScoreDto.getWinner());
        Assert.assertEquals(matchScore.getStatus(), matchScoreDto.getStatus());
        Assert.assertEquals(matchScore.getFullTimeHomeTeam(), matchScoreDto.getFullTimeHomeTeam());
        Assert.assertEquals(matchScore.getFullTimeAwayTeam(), matchScoreDto.getFullTimeAwayTeam());
        Assert.assertEquals(matchScore.getHalfTimeHomeTeam(), matchScoreDto.getHalfTimeHomeTeam());
        Assert.assertEquals(matchScore.getHalfTimeAwayTeam(), matchScoreDto.getHalfTimeAwayTeam());
    }

    private MatchScore createMatchScore() {
        return MatchScore.builder()
                .id(324L)
                .matchId(234234L)
                .winner("test winner")
                .status("test status")
                .duration("test duration")
                .fullTimeHomeTeam(2)
                .fullTimeAwayTeam(1)
                .halfTimeHomeTeam(1)
                .halfTimeAwayTeam(0)
                .extraTimeAwayTeam(null)
                .extraTimeHomeTeam(null)
                .penaltiesAwayTeam(null)
                .penaltiesHomeTeam(null)
                .build();
    }
}