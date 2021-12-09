package com.kenez92.betwinner.match;

import com.kenez92.betwinner.matchDay.MatchDay;
import com.kenez92.betwinner.weather.Weather;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface MatchRepository extends CrudRepository<Match, Long> {
    @Override
    List<Match> findAll();

    List<Match> findByMatchDay(final MatchDay matchDay);

    boolean existsByHomeTeamAndAwayTeamAndRound(final String homeTeam, final String awayTeam, final Integer round);

    Optional<Match> findByHomeTeamAndAwayTeamAndRound(final String homeTeam, final String awayTeam, final Integer round);

    List<Match> findByWeather(final Weather weather);

    @Query
    List<Match> findMatchesAtDate(@Param("DATE") Date date);

    boolean existsByFootballId(Long footballId);

    Optional<Match> findByFootballId(Long footballId);
}
