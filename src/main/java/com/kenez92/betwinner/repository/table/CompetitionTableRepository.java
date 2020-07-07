package com.kenez92.betwinner.repository.table;

import com.kenez92.betwinner.domain.table.CompetitionTable;
import com.kenez92.betwinner.domain.table.CurrentMatchDay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface CompetitionTableRepository extends CrudRepository<CompetitionTable, Long> {

    @Override
    Optional<CompetitionTable> findById(final Long competitionTableId);

    @Override
    List<CompetitionTable> findAll();

    boolean existsByStageAndTypeAndCurrentMatchDay(final String stage, final String type,
                                                   final CurrentMatchDay currentMatchDay);

    Optional<CompetitionTable> findByStageAndTypeAndCurrentMatchDay(final String stage, final String type,
                                                                    final CurrentMatchDay currentMatchDay);

    List<CompetitionTable> findByCurrentMatchDay(final CurrentMatchDay currentMatchDay);

}
