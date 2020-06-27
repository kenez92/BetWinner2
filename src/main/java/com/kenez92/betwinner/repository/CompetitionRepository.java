package com.kenez92.betwinner.repository;

import com.kenez92.betwinner.domain.Competition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CompetitionRepository extends CrudRepository<Competition, Long> {

    @Override
    Optional<Competition> findById(Long competitionId);

    @Override
    List<Competition> findAll();

    Optional<Competition> findByFootballId(Long id);

    boolean existsByFootballId(Long id);
}
