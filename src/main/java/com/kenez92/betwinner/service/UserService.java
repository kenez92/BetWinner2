package com.kenez92.betwinner.service;

import com.kenez92.betwinner.domain.UserDto;
import com.kenez92.betwinner.entity.Order;
import com.kenez92.betwinner.entity.User;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.UserMapper;
import com.kenez92.betwinner.repository.OrderRepository;
import com.kenez92.betwinner.repository.UserRepository;
import com.kenez92.betwinner.service.users.strategy.factory.UserStrategyFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final OrderRepository orderRepository;
    private final UserStrategyFactory userStrategyFactory;

    public Long getQuantityOfUsers() {
        log.debug("Counting users");
        Long result = userRepository.quantity();
        log.debug("Quantity of users: {}", result);
        return result;
    }

    public UserDto changeStrategy(Long userId, String strategy) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_USER_NOT_FOUND_EXCEPTION));
        user.setUserStrategy(userStrategyFactory.factory(strategy));
        User updateUser = userRepository.save(user);
        setOrderList(updateUser);
        return userMapper.mapToUserDto(updateUser);
    }

    public List<UserDto> getUsers() {
        log.debug("Getting all users");
        List<User> userList = userRepository.findAll();
        List<UserDto> userDtoList = userMapper.mapToUserDtoList(userList);
        log.debug("Returns all users: {}", userDtoList);
        return userDtoList;
    }

    public UserDto getUser(Long userId) {
        log.debug("Getting user by id: {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() ->
                new BetWinnerException(BetWinnerException.ERR_USER_NOT_FOUND_EXCEPTION));
        setOrderList(user);
        UserDto UserDto = userMapper.mapToUserDto(user);
        log.debug("Return user: {}", UserDto);
        return UserDto;
    }

    public UserDto createUser(UserDto userDto) {
        if (userDto.getId() != null) {
            if (isExisting(userDto.getId())) {
                throw new BetWinnerException(BetWinnerException.ERR_USER_WITH_THIS_ID_ALREADY_EXISTS_EXCEPTION);
            }
        } else {
            log.debug("Creating new user: {}", userDto);
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            User user = userRepository.save(userMapper.mapToUser(userDto));
            UserDto createdUserDto = userMapper.mapToUserDto(user);
            log.debug("Return created user: {}", createdUserDto);
            return createdUserDto;
        }
        throw new BetWinnerException(BetWinnerException.ERR_USER_ID_MUST_BE_NULL_OR_0);
    }

    public UserDto updateUser(UserDto userDto) {
        if (isExisting(userDto.getId())) {
            log.debug("Updating user id: {}", userDto.getId());
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            User updatedUser = userRepository.save(userMapper.mapToUser(userDto));
            setOrderList(updatedUser);
            UserDto updatedUserDto = userMapper.mapToUserDto(updatedUser);
            log.debug("Return updated user: {}", updatedUser);
            return updatedUserDto;
        }
        throw new BetWinnerException(BetWinnerException.ERR_USER_NOT_EXIST_EXCEPTION);
    }

    public boolean deleteUser(Long userId) {
        log.debug("Deleting user id: {}", userId);
        if (isExisting(userId)) {
            userRepository.deleteById(userId);
            if (isExisting(userId)) {
                throw new BetWinnerException(BetWinnerException.ERR_USER_NOT_DELETED);
            }
            log.debug("User deleted id: {}", userId);
            return true;
        }
        return false;
    }

    public UserDto putMoney(Long userId, Double money) {
        log.debug("Adding money {}, to user id: {}", money, userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_USER_NOT_FOUND_EXCEPTION));
        if (user.getMoney() == null) {
            user.setMoney(BigDecimal.ZERO);
        }
        BigDecimal result = user.getMoney().add(BigDecimal.valueOf(money));
        user.setMoney(result);
        User savedUser = userRepository.save(user);
        log.debug("Added money to user successful");
        setOrderList(savedUser);
        return userMapper.mapToUserDto(savedUser);
    }

    private boolean isExisting(Long userId) {
        boolean result = userRepository.existsById(userId);
        log.info("User exists: {}", result);
        return result;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.debug("Trying to log in user: {}", username);
        UserDto userDto = userRepository.findByLogin(username).map(userMapper::mapToUserDto)
                .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_LOGIN_NOT_FOUND_EXCEPTION));
        log.debug("Logging in user: {}", userDto.getLogin());
        return userDto;
    }

    private void setOrderList(User user) {
        List<Order> orderList = orderRepository.findByUser(user);
        for (int i = 0; i < orderList.size(); i++) {
            orderList.get(i).getCoupon().setCouponTypeList(new ArrayList<>());
        }
        user.setOrders(orderList);
    }

}
