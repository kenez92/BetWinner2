package com.kenez92.betwinner.persistence.repository;

import com.kenez92.betwinner.persistence.entity.Order;
import com.kenez92.betwinner.persistence.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    @Override
    List<Order> findAll();

    List<Order> findByUser(User user);
}
