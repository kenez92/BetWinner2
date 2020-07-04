package com.kenez92.betwinner.repository.matches;

import com.kenez92.betwinner.domain.matches.Weather;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Transactional
@Repository
public interface WeatherRepository extends CrudRepository<Weather, Long> {

    boolean existsByDateAndCountry(final Date date, final String country);

    Optional<Weather> findByDateAndCountry(final Date date, final String country);
}
