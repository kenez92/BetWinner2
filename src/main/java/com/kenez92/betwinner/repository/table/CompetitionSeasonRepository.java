package com.kenez92.betwinner.repository.table;

import com.kenez92.betwinner.entity.table.Competition;
import com.kenez92.betwinner.entity.table.CompetitionSeason;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface CompetitionSeasonRepository extends CrudRepository<CompetitionSeason, Long> {
    @Override
    Optional<CompetitionSeason> findById(final Long competitionId);

    @Override
    List<CompetitionSeason> findAll();

    Optional<CompetitionSeason> findByFootballId(final Long id);

    boolean existsByFootballId(final Long id);

    List<CompetitionSeason> findByCompetition(final Competition competition);
}
