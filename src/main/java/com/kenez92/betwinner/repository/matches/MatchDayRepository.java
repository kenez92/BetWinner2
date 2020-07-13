package com.kenez92.betwinner.repository.matches;

import com.kenez92.betwinner.entity.matches.MatchDay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface MatchDayRepository extends CrudRepository<MatchDay, Long> {
    @Override
    List<MatchDay> findAll();

    boolean existsByLocalDate(final LocalDate localDate);

    Optional<MatchDay> findByLocalDate(final LocalDate localDate);
}
