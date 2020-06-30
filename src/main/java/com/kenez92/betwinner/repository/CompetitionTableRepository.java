package com.kenez92.betwinner.repository;

import com.kenez92.betwinner.domain.CompetitionTable;
import com.kenez92.betwinner.domain.CurrentMatchDay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface CompetitionTableRepository extends CrudRepository<CompetitionTable, Long> {

    boolean existsByStageAndTypeAndCurrentMatchDay(String stage, String type, CurrentMatchDay currentMatchDay);

    Optional<CompetitionTable> findByStageAndTypeAndCurrentMatchDay(String stage, String type, CurrentMatchDay currentMatchDay);

    List<CompetitionTable> findByCurrentMatchDay(CurrentMatchDay currentMatchDay);

    @Override
    Optional<CompetitionTable> findById(Long competitionTableId);
}
