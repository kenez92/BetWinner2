package com.kenez92.betwinner.persistence.repository.matches;

import com.kenez92.betwinner.persistence.entity.matches.MatchScore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface MatchScoreRepository extends CrudRepository<MatchScore, Long> {
    @Override
    List<MatchScore> findAll();

    boolean existsByFootballMatchId(final Long matchId);

    Optional<MatchScore> findByFootballMatchId(final Long matchId);
}
