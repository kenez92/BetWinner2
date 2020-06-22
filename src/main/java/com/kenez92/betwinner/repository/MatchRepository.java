package com.kenez92.betwinner.repository;

import com.kenez92.betwinner.domain.Match;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Repository
public interface MatchRepository extends CrudRepository<Match, Long> {

    //Optional<Match> findByMatchId(Long matchId);
}
