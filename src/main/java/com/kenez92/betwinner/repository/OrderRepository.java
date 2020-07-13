package com.kenez92.betwinner.repository;

import com.kenez92.betwinner.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    @Override
    List<Order> findAll();
}
