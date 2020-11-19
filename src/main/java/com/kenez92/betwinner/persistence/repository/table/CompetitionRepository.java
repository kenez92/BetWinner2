package com.kenez92.betwinner.persistence.repository.table;

import com.kenez92.betwinner.persistence.entity.table.Competition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CompetitionRepository extends CrudRepository<Competition, Long> {

    @Override
    Optional<Competition> findById(final Long competitionId);

    @Override
    List<Competition> findAll();

    Optional<Competition> findByFootballId(final Long id);

    boolean existsByFootballId(final Long id);

    Optional<Competition> findByName(String name);
}
