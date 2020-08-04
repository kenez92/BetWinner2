package com.kenez92.betwinner.repository;

import com.kenez92.betwinner.entity.User;
import jdk.jfr.Name;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @Override
    List<User> findAll();

    @Override
    Optional<User> findById(Long userId);

    Optional<User> findByLogin(String login);

    @Query
    Long quantity();
}
