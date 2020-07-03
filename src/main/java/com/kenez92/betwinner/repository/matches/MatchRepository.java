package com.kenez92.betwinner.repository.matches;

import com.kenez92.betwinner.domain.matches.Match;
import com.kenez92.betwinner.domain.matches.MatchDay;
import com.kenez92.betwinner.domain.matches.Weather;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface MatchRepository extends CrudRepository<Match, Long> {
    List<Match> findByMatchDay(MatchDay matchDay);

    boolean existsByHomeTeamAndAwayTeamAndRound(String homeTeam, String awayTeam, Integer round);

    Optional<Match> findByHomeTeamAndAwayTeamAndRound(String homeTeam, String awayTeam, Integer round);

    List<Match> findByWeather(Weather weather);
}
