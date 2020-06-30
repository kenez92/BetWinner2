package com.kenez92.betwinner.repository;

import com.kenez92.betwinner.domain.CompetitionTable;
import com.kenez92.betwinner.domain.CompetitionTableElement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface CompetitionTableElementRepository extends CrudRepository<CompetitionTableElement, Long> {
    List<CompetitionTableElement> findByCompetitionTable(CompetitionTable competitionTable);

    Boolean existsByNameAndCompetitionTable(String name, CompetitionTable competitionTable);

    Optional<CompetitionTableElement> findByNameAndCompetitionTable(String name, CompetitionTable competitionTable);
}
