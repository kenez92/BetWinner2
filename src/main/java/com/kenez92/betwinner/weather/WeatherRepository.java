package com.kenez92.betwinner.weather;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface WeatherRepository extends CrudRepository<Weather, Long> {

    @Override
    List<Weather> findAll();

    boolean existsByDateAndCountry(final Date date, final String country);

    Optional<Weather> findByDateAndCountry(final Date date, final String country);
}
