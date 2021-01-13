package com.kenez92.betwinner.mapper;

import com.kenez92.betwinner.domain.UserDto;
import com.kenez92.betwinner.persistence.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(uses = CouponMapper.class)
@Component
public interface UserMapper {
    User mapToUser(UserDto userDto);

    @Mapping(target = "authorities", ignore = true)
    UserDto mapToUserDto(User user);

    List<UserDto> mapToUserDtoList(List<User> userList);
}
