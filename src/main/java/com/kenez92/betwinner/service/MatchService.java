package com.kenez92.betwinner.service;

import com.kenez92.betwinner.domain.Match;
import com.kenez92.betwinner.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MatchService {
    private final MatchRepository matchRepository;

    public void saveMatch(final Match match) {
        if (!matchRepository.existsById(match.getId())) {
            matchRepository.save(match);
        } else {
            log.info("Sorry this match already exist: {}", match.getId());
        }
    }
}
