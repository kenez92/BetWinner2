package com.kenez92.betwinner.repository;

import com.kenez92.betwinner.domain.CurrentMatchDay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface CurrentMatchDayRepository extends CrudRepository<CurrentMatchDay, Long> {
}
