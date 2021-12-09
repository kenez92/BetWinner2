package com.kenez92.betwinner.users;

import com.kenez92.betwinner.coupons.CouponMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {CouponMapper.class})
@Component
public interface UserMapper {
    User mapToUser(UserDto userDto);

    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "coupons", qualifiedByName = "couponDtosForUser")
    UserDto mapToUserDto(final User user);

    @Named("userDtoForCouponDto")
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "coupons", ignore = true)
    @Mapping(target = "password", ignore = true)
    UserDto mapToUserDtoForCoupon(final User user);

    default List<UserDto> mapToUserDtoList(final List<User> userList) {
        return new ArrayList<>(userList).stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }
}
