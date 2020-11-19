package com.kenez92.betwinner.mapper;

import com.kenez92.betwinner.domain.OrderDto;
import com.kenez92.betwinner.persistence.entity.Order;
import com.kenez92.betwinner.service.users.strategy.factory.UserStrategyConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper(uses = UserStrategyConverter.class)
public interface OrderMapper {

    Order mapToOrder(OrderDto orderDto);

    @Mapping(target = "user.authorities", ignore = true)
    OrderDto mapToOrderDto(Order order);

    default List<OrderDto> mapToOrderDtoList(List<Order> orders) {
        if (orders == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(orders).stream()
                .map(this::mapToOrderDto)
                .collect(Collectors.toList());
    }
}
