package com.kenez92.betwinner.repository;

import com.kenez92.betwinner.domain.CompetitionTableElement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface CompetitionTableElementRepository extends CrudRepository<CompetitionTableElement, Long> {
}
