package com.kenez92.betwinner.repository;

import com.kenez92.betwinner.domain.Competition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CompetitionRepository extends CrudRepository<Competition, Long> {

}
