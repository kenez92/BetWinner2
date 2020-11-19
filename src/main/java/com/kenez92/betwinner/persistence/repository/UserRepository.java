package com.kenez92.betwinner.persistence.repository;

import com.kenez92.betwinner.persistence.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

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

    @Query
    List<User> usersForSubscription();
}
