package com.kenez92.betwinner.competitionTableElement;

import com.kenez92.betwinner.competitionTable.CompetitionTable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface CompetitionTableElementRepository extends CrudRepository<CompetitionTableElement, Long> {
    @Override
    List<CompetitionTableElement> findAll();

    List<CompetitionTableElement> findByCompetitionTable(final CompetitionTable competitionTable);

    boolean existsByNameAndCompetitionTable(final String name, final CompetitionTable competitionTable);

    Optional<CompetitionTableElement> findByNameAndCompetitionTable(final String name, final CompetitionTable competitionTable);

    List<CompetitionTableElement> findByName(final String homeTeam);
}
