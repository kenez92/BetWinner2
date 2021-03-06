package com.kenez92.betwinner.persistence.repository.table;

import com.kenez92.betwinner.persistence.entity.table.CompetitionSeason;
import com.kenez92.betwinner.persistence.entity.table.CurrentMatchDay;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface CurrentMatchDayRepository extends CrudRepository<CurrentMatchDay, Long> {

    @Override
    Optional<CurrentMatchDay> findById(final Long competitionId);

    @Override
    List<CurrentMatchDay> findAll();

    Optional<CurrentMatchDay> findByCompetitionSeasonAndMatchDay(final CompetitionSeason competitionSeason,
                                                                 final Integer matchDay);

    boolean existsByCompetitionSeasonAndMatchDay(final CompetitionSeason competitionSeason, final Integer matchDay);

    List<CurrentMatchDay> findByCompetitionSeason(final CompetitionSeason competitionSeason);

    @Query
    List<CurrentMatchDay> findByCompetitionSeasonId(@Param("COMPETITION_SEASON_ID") Long competitionSeasonId);

    @Query(nativeQuery = true)
    List<Integer> getActualCurrentMatchDay(@Param("COMPETITION_ID") Long competitionId);

    @Query("SELECT c FROM CurrentMatchDay c WHERE c.matchDay=?1 AND c.competitionSeason.footballId = ?2")
    Optional<CurrentMatchDay> findByCurrentMatchDayNumberAndCompetitionSeasonId(Integer currentMatchDayNumber, Long competitionSeasonId);

    List<CurrentMatchDay> findByCompetitionSeason_FootballId(Long competitionSeasonFootballId);
}
