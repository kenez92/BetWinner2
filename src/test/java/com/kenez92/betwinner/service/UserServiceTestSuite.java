package com.kenez92.betwinner.service;

import com.kenez92.betwinner.common.enums.UserStrategy;
import com.kenez92.betwinner.domain.UserDto;
import com.kenez92.betwinner.domain.UserRole;
import com.kenez92.betwinner.exception.BetWinnerException;
import com.kenez92.betwinner.mapper.UserMapper;
import com.kenez92.betwinner.persistence.entity.User;
import com.kenez92.betwinner.persistence.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTestSuite {
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    @Test
    public void testQuantityOfUsers() {
        //Given
        Mockito.when(userRepository.quantity()).thenReturn(158354L);
        //When
        Long result = userRepository.quantity();
        //Then
        Assert.assertEquals(158354L, result, 0.0001);
    }

    @Test
    public void testGetUser() {
        //Given
        User user = createUser();
        Mockito.when(userRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.ofNullable(user));
        //When
        UserDto userDto = userService.getUser(3L);
        //Then
        Assert.assertEquals(user.getId(), userDto.getId());
        Assert.assertEquals(user.getFirstName(), userDto.getFirstName());
        Assert.assertEquals(user.getLastName(), userDto.getLastName());
        Assert.assertEquals(user.getLogin(), userDto.getLogin());
        Assert.assertEquals(user.getPassword(), userDto.getPassword());
        Assert.assertEquals(user.getRole().toString(), userDto.getRole());
        Assert.assertEquals(user.getEmail(), userDto.getEmail());
        Assert.assertTrue(user.getSubscription());
        Assert.assertEquals(UserStrategy.NORMAL_STRATEGY, userDto.getUserStrategy());
    }

    @Test
    public void testGetUserShouldThrowExceptionWhenUserNotFound() {
        //Given
        Mockito.when(userRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
        //When
        //Then
        Assertions.assertThrows(BetWinnerException.class, () -> userService.getUser(3L));
    }

    @Test
    public void testGetUsers() {
        //Given
        List<User> userList = new ArrayList<>();
        userList.add(createUser());
        userList.add(createUser());
        Mockito.when(userRepository.findAll()).thenReturn(userList);
        //When
        List<UserDto> userDtoList = userService.getUsers();
        //Then
        Assert.assertEquals(userList.size(), userDtoList.size());
    }

    @Test
    public void testCreateUser() {
        //Given
        User user = createUser();
        UserDto createdUserDto = createUserDto();
        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);
        //When
        UserDto userDto = userService.createUser(createdUserDto);
        //Then
        Assert.assertEquals(user.getId(), userDto.getId());
        Assert.assertEquals(user.getFirstName(), userDto.getFirstName());
        Assert.assertEquals(user.getLastName(), userDto.getLastName());
        Assert.assertEquals(user.getLogin(), userDto.getLogin());
        Assert.assertEquals(user.getPassword(), userDto.getPassword());
        Assert.assertEquals(user.getRole().toString(), userDto.getRole());
        Assert.assertEquals(user.getEmail(), userDto.getEmail());
        Assert.assertEquals(UserStrategy.NORMAL_STRATEGY, userDto.getUserStrategy());
    }

    @Test
    public void testCreateUserShouldThrowExceptionWhenIdIsDifferentThanNullOr0() {
        //Given
        UserDto userDto = createUserDto();
        userDto.setId(23423L);
        //When
        //Then
        Assertions.assertThrows(BetWinnerException.class, () -> userService.createUser(userDto));
    }

    @Test
    public void testUpdateUser() {
        //Given
        User user = createUser();
        UserDto tmpUserDto = createUserDto();
        tmpUserDto.setId(343L);
        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);
        Mockito.when(userRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true);
        //when
        UserDto userDto = userService.updateUser(tmpUserDto);
        //Then
        Assert.assertEquals(user.getId(), userDto.getId());
        Assert.assertEquals(user.getFirstName(), userDto.getFirstName());
        Assert.assertEquals(user.getLastName(), userDto.getLastName());
        Assert.assertEquals(user.getLogin(), userDto.getLogin());
        Assert.assertEquals(user.getPassword(), userDto.getPassword());
        Assert.assertEquals(user.getRole().toString(), userDto.getRole());
        Assert.assertEquals(user.getEmail(), userDto.getEmail());
    }

    @Test
    public void testUpdateUserShouldThrowExceptionWhenUserIdIsNullOr0() {
        //Given
        UserDto userDto = createUserDto();
        //When
        //Then
        Assertions.assertThrows(BetWinnerException.class, () -> userService.updateUser(userDto));
    }

    @Test
    public void testDeleteUserShouldReturnFalseWhenUserNotExists() {
        //Given
        Mockito.when(userRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(false);
        //When
        boolean result = userService.deleteUser(123123L);
        //Then
        Assert.assertFalse(result);
    }

    @Test
    public void testDeleteUserShouldThrowBetWinnerExceptionWhenUserNotDeleted() {
        //Given
        Mockito.when(userRepository.existsById((ArgumentMatchers.anyLong()))).thenReturn(true);
        //When
        //Then
        Assertions.assertThrows(BetWinnerException.class, () -> userService.deleteUser(234235L));
    }

    private User createUser() {
        return User.builder()
                .id(123L)
                .firstName("Test first name")
                .lastName("Test last name")
                .login("Test")
                .password("Test password")
                .role(UserRole.ROLE_ADMIN)
                .email("test@test.pl")
                .userStrategy(UserStrategy.NORMAL_STRATEGY)
                .subscription(true)
                .coupons(new ArrayList<>())
                .build();
    }

    private UserDto createUserDto() {
        return UserDto.builder()
                .firstName("Test first name")
                .lastName("Test last name")
                .login("Test")
                .password("Test password")
                .role(UserRole.ROLE_ADMIN.toString())
                .email("test@test.pl")
                .coupons(new ArrayList<>())
                .userStrategy(UserStrategy.NORMAL_STRATEGY)
                .build();
    }
}
