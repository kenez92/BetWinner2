package com.kenez92.betwinner.service.matches;

import com.kenez92.betwinner.domain.matches.MatchScore;
import com.kenez92.betwinner.domain.matches.MatchScoreDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.matches.MatchScoreMapper;
import com.kenez92.betwinner.repository.matches.MatchScoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MatchScoreService {
    private final MatchScoreRepository matchScoreRepository;
    private final MatchScoreMapper matchScoreMapper;

    public MatchScoreDto saveMatchScore(MatchScoreDto matchScoreDto) {
        log.info("Saving match score: {}", matchScoreDto);
        MatchScore matchScore = matchScoreRepository.save(matchScoreMapper.mapToMatchScore(matchScoreDto));
        MatchScoreDto savedMatchScoreDto = matchScoreMapper.mapToMatchScoreDto(matchScore);
        log.info("Saved match score: {}", savedMatchScoreDto);
        return savedMatchScoreDto;
    }

    public boolean existByMatchId(Long matchId) {
        boolean result = matchScoreRepository.existsByMatchId(matchId);
        return result;
    }

    public MatchScoreDto getByMatchId(Long matchId) {
        log.info("Find match score by match id: {}", matchId);
        MatchScore matchScore = matchScoreRepository.findByMatchId(matchId).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_MATCH_SCORE_NOT_FOUND_EXCEPTION));
        MatchScoreDto matchScoreDto = matchScoreMapper.mapToMatchScoreDto(matchScore);
        log.info("Found match score by match id: {}", matchScoreDto);
        return matchScoreDto;
    }
}
