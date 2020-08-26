package com.kenez92.betwinner.controller;

import com.kenez92.betwinner.domain.UserDto;
import com.kenez92.betwinner.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> getUsers() {
        log.info("Getting all users");
        List<UserDto> userDtoList = userService.getUsers();
        log.info("Return all users: {}", userDtoList);
        return userDtoList;
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto getUser(@PathVariable Long userId) {
        log.info("Getting player by id: {}", userId);
        UserDto userDto = userService.getUser(userId);
        log.info("Return player by id: {}", userId);
        return userDto;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDto createUser(@RequestBody UserDto userDto) {
        log.info("Creating user: {}", userDto);
        UserDto createdUserDto = userService.createUser(userDto);
        log.info("Return created user: {}", createdUserDto);
        return createdUserDto;
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDto updateUser(@RequestBody UserDto userDto) {
        log.info("Updating user: {}", userDto);
        UserDto updatedUserDTo = userService.updateUser(userDto);
        log.info("Return updated user: {}", updatedUserDTo);
        return updatedUserDTo;
    }

    @PutMapping(value = "/{userId}/{strategy}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto changeStrategy(@PathVariable Long userId, @PathVariable String strategy) {
        log.info("Changing strategy user : {}{}", userId, strategy);
        UserDto userDto = userService.changeStrategy(userId, strategy);
        log.info("Changed user strategy: {}", userDto);
        return userDto;
    }

    @DeleteMapping("/{userId}")
    public boolean deleteUser(@PathVariable Long userId) {
        log.info("Deleting user by id: {}", userId);
        boolean result = userService.deleteUser(userId);
        if (result) {
            log.info("Deleted user id: {}", userId);
            return true;
        } else {
            log.info("User not deleted id: {}", userId);
            return false;
        }
    }
}
