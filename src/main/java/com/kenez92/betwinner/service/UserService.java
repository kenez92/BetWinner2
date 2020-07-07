package com.kenez92.betwinner.service;

import com.kenez92.betwinner.domain.User;
import com.kenez92.betwinner.domain.UserDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.UserMapper;
import com.kenez92.betwinner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<UserDto> getUsers() {
        log.debug("Getting all users");
        List<User> userList = userRepository.findAll();
        if (userList.size() == 0) {
            throw new BetWinnerException(BetWinnerException.ERR_USERS_NOT_FOUND_EXCEPTION);
        }
        List<UserDto> userDtoList = userMapper.mapToUserDtoList(userList);
        log.debug("Returns all users: {}", userDtoList);
        return userDtoList;
    }

    public UserDto getUser(Long userId) {
        log.debug("Getting user by id: {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() ->
                new BetWinnerException(BetWinnerException.ERR_USER_NOT_FOUND_EXCEPTION));
        UserDto UserDto = userMapper.mapToUserDto(user);
        log.debug("Return match: {}", UserDto);
        return UserDto;
    }

    public UserDto createUser(UserDto userDto) {
        if (userDto.getId() == null || userDto.getId() == 0) {
            log.debug("Creating new user: {}", userDto);
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            User user = userRepository.save(userMapper.mapToUser(userDto));
            UserDto createdUserDto = userMapper.mapToUserDto(user);
            log.debug("Return created user: {}", createdUserDto);
            return createdUserDto;
        } else {
            throw new BetWinnerException(BetWinnerException.ERR_USER_ID_MUST_BE_NULL_OR_0_EXCEPTION);
        }
    }

    public UserDto updateUser(UserDto userDto) {
        if (userDto.getId() != null) {
            isExisting(userDto.getId());
        } else {
            throw new BetWinnerException(BetWinnerException.ERR_USER_ID_MUST_BE_NOT_NULL_EXCEPTION);
        }
        log.debug("Updating user id: {}", userDto.getId());
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User updatedUser = userRepository.save(userMapper.mapToUser(userDto));
        UserDto updatedUserDto = userMapper.mapToUserDto(updatedUser);
        log.debug("Return updated user: {}", updatedUser);
        return updatedUserDto;
    }

    public boolean deleteUser(Long userId) {
        isExisting(userId);
        log.debug("Deleting user by id: {}", userId);
        userRepository.deleteById(userId);
        boolean result = userRepository.existsById(userId);
        if (result) {
            log.debug("User still existing in repository");
            return false;
        } else {
            log.debug("User deleted by id: {}", userId);
            return true;
        }
    }

    private void isExisting(Long userId) {
        boolean exist = userRepository.existsById(userId);
        if (!exist) {
            throw new BetWinnerException(BetWinnerException.ERR_MATCH_NOT_EXIST_EXCEPTION);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.debug("Trying to log in user: {}", username);
        UserDto userDto = userRepository.findByLogin(username).map(userMapper::mapToUserDto)
                .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_LOGIN_NOT_FOUND_EXCEPTION));
        log.debug("Logging in user: {}", userDto.getLogin());
        return userDto;
    }
}
