package com.kenez92.betwinner.service;

import com.kenez92.betwinner.common.enums.UserStrategy;
import com.kenez92.betwinner.domain.UserDto;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.UserMapper;
import com.kenez92.betwinner.persistence.entity.Coupon;
import com.kenez92.betwinner.persistence.entity.User;
import com.kenez92.betwinner.persistence.repository.CouponRepository;
import com.kenez92.betwinner.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private final CouponRepository couponRepository;

    public Long getQuantityOfUsers() {
        log.debug("Counting users");
        Long result = userRepository.quantity();
        log.debug("Quantity of users: {}", result);
        return result;
    }

    public UserDto changeStrategy(Long userId, String strategy) {
        UserStrategy userStrategy;
        try {
            userStrategy = UserStrategy.valueOf(strategy);
        } catch (Exception e) {
            throw new BetWinnerException(BetWinnerException.ERR_STRATEGY_NOT_EXIST);
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_USER_NOT_FOUND_EXCEPTION));
        user.setUserStrategy(userStrategy);
        User updateUser = userRepository.save(user);
        setCouponList(updateUser);
        return userMapper.mapToUserDto(updateUser);
    }

    public List<UserDto> getUsers() {
        log.debug("Getting all users");
        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            setCouponList(user);
        }
        List<UserDto> userDtoList = userMapper.mapToUserDtoList(userList);
        log.debug("Returns all users: {}", userDtoList);
        return userDtoList;
    }

    public UserDto getUser(Long userId) {
        log.debug("Getting user by id: {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() ->
                new BetWinnerException(BetWinnerException.ERR_USER_NOT_FOUND_EXCEPTION));
        setCouponList(user);
        UserDto UserDto = userMapper.mapToUserDto(user);
        log.debug("Return user: {}", UserDto);
        return UserDto;
    }

    public UserDto createUser(UserDto userDto) {
        try {
            if (userDto.getId() != null) {
                if (isExisting(userDto.getId())) {
                    throw new BetWinnerException(BetWinnerException.ERR_USER_WITH_THIS_ID_ALREADY_EXISTS_EXCEPTION);
                }
            } else {
                log.debug("Creating new user: {}", userDto);
                userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
                User user = userRepository.save(userMapper.mapToUser(userDto));
                setCouponList(user);
                UserDto createdUserDto = userMapper.mapToUserDto(user);
                log.debug("Return created user: {}", createdUserDto);
                return createdUserDto;
            }
            throw new BetWinnerException(BetWinnerException.ERR_USER_ID_MUST_BE_NULL_OR_0);
        } catch (DataIntegrityViolationException e) {
            log.info("Exception occured : {}", e + e.getMessage());
            throw new BetWinnerException(BetWinnerException.USER_WITH_LOGIN_OR_EMAIL_ALREADY_EXIST_EXCEPTION);
        }
    }

    public UserDto updateUser(UserDto userDto, UsernamePasswordAuthenticationToken user) {
        User dbUser = userRepository.findByLogin(user.getName()).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_USER_NOT_FOUND_EXCEPTION));
        log.debug("Updating user {}", dbUser);
        if (userDto.getFirstName() != null) {
            dbUser.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null) {
            dbUser.setLastName(userDto.getLastName());
        }
        if (userDto.getPassword() != null) {
            dbUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        if (userDto.getEmail() != null) {
            dbUser.setEmail(userDto.getEmail());
        }
        if (userDto.getUserStrategy() != null) {
            dbUser.setUserStrategy(userDto.getUserStrategy());
        }
        if (userDto.getSubscription() != null) {
            dbUser.setSubscription(userDto.getSubscription());
        }
        User updatedUser = userRepository.save(dbUser);
        UserDto updatedUserDto = userMapper.mapToUserDto(updatedUser);
        log.debug("Return updated user: {}", updatedUser);
        return updatedUserDto;
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
        setCouponList(savedUser);
        return userMapper.mapToUserDto(savedUser);
    }

    private boolean isExisting(Long userId) {
        boolean result = userRepository.existsById(userId);
        log.info("User exists: {}", result);
        return result;
    }

    public UserDto getUserByLogin(String login) {
        log.debug("Getting user by login {}", login);
        User user = userRepository.findByLogin(login).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_USER_NOT_FOUND_EXCEPTION));
        UserDto userDto = userMapper.mapToUserDto(user);
        log.debug("Return user by login {}", userDto);
        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.debug("Trying to log in user: {}", username);
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new BetWinnerException(BetWinnerException.ERR_LOGIN_NOT_FOUND_EXCEPTION));
        setCouponList(user);
        UserDto userDto = userMapper.mapToUserDto(user);
        userDto.setPassword(user.getPassword());
        log.debug("Logging in user: {}", userDto.getLogin());
        return userDto;
    }

    private void setCouponList(User user) {
        List<Coupon> couponList = couponRepository.findByUser(user);
        for (int i = 0; i < couponList.size(); i++) {
            couponList.get(i).setCouponTypeList(new ArrayList<>());
        }
        user.setCoupons(couponList);
    }

    public void takePointsForCoupon(BigDecimal rate, UsernamePasswordAuthenticationToken user) {
        User userFromDb = userRepository.findByLogin(user.getName()).orElseThrow(()
                -> new BetWinnerException(BetWinnerException.ERR_USER_NOT_FOUND_EXCEPTION));
        if (userFromDb.getMoney().compareTo(rate) == -1) {
            throw new BetWinnerException(BetWinnerException.ERR_USER_DONT_HAVE_ENOUGH_MONEY);
        }
        userFromDb.setMoney(userFromDb.getMoney().subtract(rate));
        userRepository.save(userFromDb);
    }
}
