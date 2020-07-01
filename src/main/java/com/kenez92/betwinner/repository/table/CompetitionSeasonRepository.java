package com.kenez92.betwinner.repository.table;

import com.kenez92.betwinner.domain.table.Competition;
import com.kenez92.betwinner.domain.table.CompetitionSeason;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface CompetitionSeasonRepository extends CrudRepository<CompetitionSeason, Long> {
    @Override
    Optional<CompetitionSeason> findById(Long competitionId);

    @Override
    List<CompetitionSeason> findAll();

    Optional<CompetitionSeason> findByFootballId(Long id);

    boolean existsByFootballId(Long id);

    List<CompetitionSeason> findByCompetition(Competition competition);
}
