package com.kenez92.betwinner.mapper;

import com.kenez92.betwinner.domain.UserDto;
import com.kenez92.betwinner.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    User mapToUser(UserDto userDto);

    @Mapping(target = "authorities", ignore = true)
    UserDto mapToUserDto(User user);

    List<UserDto> mapToUserDtoList(List<User> userList);
}
