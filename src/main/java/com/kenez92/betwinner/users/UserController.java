package com.kenez92.betwinner.users;

import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.users.UserDto;
import com.kenez92.betwinner.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping(value = "/loggedIn", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto getLoggedUser(@AuthenticationPrincipal UsernamePasswordAuthenticationToken user) {
        if (user != null) {
            log.info("Getting logged user by name: {}", user.getPrincipal());
            UserDto userDto = userService.getUserByLogin(String.valueOf(user.getPrincipal()));
            log.info("Return player by name: {}", userDto);
            return userDto;
        } else {
            throw new BetWinnerException(BetWinnerException.ERR_USER_NOT_FOUND_EXCEPTION);
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDto createUser(@RequestBody UserDto userDto) {
        log.info("Creating user: {}", userDto);
        UserDto createdUserDto = userService.createUser(userDto);
        log.info("Return created user: {}", createdUserDto);
        return createdUserDto;
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDto updateUser(@RequestBody UserDto userDto, @AuthenticationPrincipal UsernamePasswordAuthenticationToken user) {
        log.info("Updating user: {}", userDto);
        UserDto updatedUserDTo = userService.updateUser(userDto, user);
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

    @PutMapping(value = "/{userId}/add/{money}")
    public UserDto putMoney(@PathVariable Long userId, @PathVariable Double money) {
        log.info("Adding money : {}, to user id {}", money, userId);
        UserDto userDto = userService.putMoney(userId, money);
        log.info("Added money successful: {}", userDto);
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
