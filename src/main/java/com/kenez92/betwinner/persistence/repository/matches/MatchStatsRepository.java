package com.kenez92.betwinner.persistence.repository.matches;

import com.kenez92.betwinner.persistence.entity.matches.MatchStats;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface MatchStatsRepository extends CrudRepository<MatchStats, Long> {

    boolean existsByFootballMatchId(Long footballMatchId);

    Optional<MatchStats> findByFootballMatchId(Long footballMatchId);
}
