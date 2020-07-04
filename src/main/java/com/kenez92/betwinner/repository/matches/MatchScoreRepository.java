package com.kenez92.betwinner.repository.matches;

import com.kenez92.betwinner.domain.matches.MatchScore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Repository
public interface MatchScoreRepository extends CrudRepository<MatchScore, Long> {

    boolean existsByMatchId(final Long matchId);

    Optional<MatchScore> findByMatchId(final Long matchId);
}
