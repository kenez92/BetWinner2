package com.kenez92.betwinner.repository.table;

import com.kenez92.betwinner.domain.table.CompetitionSeason;
import com.kenez92.betwinner.domain.table.CurrentMatchDay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface CurrentMatchDayRepository extends CrudRepository<CurrentMatchDay, Long> {

    @Override
    Optional<CurrentMatchDay> findById(Long competitionId);

    @Override
    List<CurrentMatchDay> findAll();

    Optional<CurrentMatchDay> findByCompetitionSeasonAndMatchDay(CompetitionSeason competitionSeason, Integer matchDay);

    boolean existsByCompetitionSeasonAndMatchDay(CompetitionSeason competitionSeason, Integer matchDay);

    List<CurrentMatchDay> findByCompetitionSeason(CompetitionSeason competitionSeason);
}
