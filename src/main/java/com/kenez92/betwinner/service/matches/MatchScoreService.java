package com.kenez92.betwinner.service.matches;

import com.kenez92.betwinner.domain.matches.MatchScore;
import com.kenez92.betwinner.domain.matches.MatchScoreDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.matches.MatchScoreMapper;
import com.kenez92.betwinner.repository.matches.MatchScoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MatchScoreService {
    private final MatchScoreRepository matchScoreRepository;
    private final MatchScoreMapper matchScoreMapper;

    public List<MatchScoreDto> getMatchScores() {
        log.debug("Getting all match scores");
        List<MatchScore> matchScoreList = matchScoreRepository.findAll();
        List<MatchScoreDto> matchScoreDtoList = matchScoreMapper.mapToMatchScoreDtoList(matchScoreList);
        log.debug("Return all match score: {}", matchScoreDtoList);
        return matchScoreDtoList;
    }

    public MatchScoreDto getMatchScore(final Long matchScoreId) {
        log.debug("Getting match score id: {}", matchScoreId);
        MatchScore matchScore = matchScoreRepository.findById(matchScoreId).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_MATCH_SCORE_NOT_FOUND_EXCEPTION));
        MatchScoreDto matchScoreDto = matchScoreMapper.mapToMatchScoreDto(matchScore);
        log.debug("Return match score: {}", matchScoreDto);
        return matchScoreDto;
    }

    public MatchScoreDto saveMatchScore(final MatchScoreDto matchScoreDto) {
        log.debug("Saving match score: {}", matchScoreDto);
        MatchScore matchScore = matchScoreRepository.save(matchScoreMapper.mapToMatchScore(matchScoreDto));
        MatchScoreDto savedMatchScoreDto = matchScoreMapper.mapToMatchScoreDto(matchScore);
        log.debug("Saved match score: {}", savedMatchScoreDto);
        return savedMatchScoreDto;
    }

    public boolean existByMatchId(final Long matchId) {
        boolean result = matchScoreRepository.existsByMatchId(matchId);
        log.debug("Match score exists in repository: {}", result);
        return result;
    }

    public MatchScoreDto getByMatchId(final Long matchId) {
        log.debug("Find match score by match id: {}", matchId);
        MatchScore matchScore = matchScoreRepository.findByMatchId(matchId).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_MATCH_SCORE_NOT_FOUND_EXCEPTION));
        MatchScoreDto matchScoreDto = matchScoreMapper.mapToMatchScoreDto(matchScore);
        log.debug("Found match score by match id: {}", matchScoreDto);
        return matchScoreDto;
    }
}
