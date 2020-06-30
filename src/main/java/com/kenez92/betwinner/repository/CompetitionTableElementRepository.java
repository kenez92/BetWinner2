package com.kenez92.betwinner.repository;

import com.kenez92.betwinner.domain.CompetitionTable;
import com.kenez92.betwinner.domain.CompetitionTableElement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface CompetitionTableElementRepository extends CrudRepository<CompetitionTableElement, Long> {
    List<CompetitionTableElement> findByCompetitionTable(CompetitionTable competitionTable);
}
