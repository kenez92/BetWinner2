package com.kenez92.betwinner.controller;

import com.google.gson.Gson;
import com.kenez92.betwinner.common.enums.UserStrategy;
import com.kenez92.betwinner.users.UserDto;
import com.kenez92.betwinner.users.UserService;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testGetUser() throws Exception {
        //Given
        Mockito.when(userService.getUser(ArgumentMatchers.anyLong())).thenReturn(createUserDto());
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/users/22341324")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(123)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Test first name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("Test last name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login", Matchers.is("Test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.is("Test password")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role", Matchers.is("ROLE_ADMIN")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("test@test.pl")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userStrategy", Matchers.is("NORMAL_STRATEGY")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subscription", Matchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.money", Matchers.is("1200")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coupons", Matchers.hasSize(0)));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testGetUsers() throws Exception {
        //Given
        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(createUserDto());
        userDtoList.add(createUserDto());
        Mockito.when(userService.getUsers()).thenReturn(userDtoList);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id", Matchers.is(123)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].firstName", Matchers.is("Test first name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].lastName", Matchers.is("Test last name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].login", Matchers.is("Test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].password", Matchers.is("Test password")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].role", Matchers.is("ROLE_ADMIN")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].email", Matchers.is("test@test.pl")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].userStrategy", Matchers.is("NORMAL_STRATEGY")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].subscription", Matchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].money", Matchers.is("1200")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].coupons", Matchers.hasSize(0)));
    }

    @Test
    public void testCreateUser() throws Exception {
        //Given
        UserDto userDto = createUserDto();
        Mockito.when(userService.createUser(ArgumentMatchers.any(UserDto.class))).thenReturn(userDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(123)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Test first name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("Test last name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login", Matchers.is("Test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.is("Test password")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role", Matchers.is("ROLE_ADMIN")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("test@test.pl")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userStrategy", Matchers.is("NORMAL_STRATEGY")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subscription", Matchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.money", Matchers.is("1200")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coupons", Matchers.hasSize(0)));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testUpdateUser() throws Exception {
        //Given
        UserDto userDto = createUserDto();
        Mockito.when(userService.updateUser(ArgumentMatchers.any(UserDto.class),
                ArgumentMatchers.any(UsernamePasswordAuthenticationToken.class))).thenReturn(userDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(123)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Test first name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("Test last name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login", Matchers.is("Test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.is("Test password")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role", Matchers.is("ROLE_ADMIN")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("test@test.pl")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userStrategy", Matchers.is("NORMAL_STRATEGY")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subscription", Matchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.money", Matchers.is("1200")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coupons", Matchers.hasSize(0)));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testChangeStrategy() throws Exception {
        //Given
        UserDto userDto = createUserDto();
        Mockito.when(userService.changeStrategy(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString())).thenReturn(userDto);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/users/2342/NORMAL_STRATEGY")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(123)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Test first name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("Test last name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login", Matchers.is("Test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.is("Test password")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role", Matchers.is("ROLE_ADMIN")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("test@test.pl")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userStrategy", Matchers.is("NORMAL_STRATEGY")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subscription", Matchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.money", Matchers.is("1200")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coupons", Matchers.hasSize(0)));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testPutMoney() throws Exception {
        //Given
        UserDto userDto = createUserDto();
        Mockito.when(userService.putMoney(ArgumentMatchers.anyLong(), ArgumentMatchers.anyDouble())).thenReturn(userDto);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/users/2342/add/2342")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(123)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Test first name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("Test last name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login", Matchers.is("Test")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.is("Test password")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.role", Matchers.is("ROLE_ADMIN")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("test@test.pl")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userStrategy", Matchers.is("NORMAL_STRATEGY")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subscription", Matchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.money", Matchers.is("1200")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coupons", Matchers.hasSize(0)));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testDeleteUser() throws Exception {
        //Given
        Mockito.when(userService.deleteUser(ArgumentMatchers.anyLong())).thenReturn(true);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/v1/users/2342")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(true)));
    }

    private UserDto createUserDto() {
        return UserDto.builder()
                .id(123L)
                .firstName("Test first name")
                .lastName("Test last name")
                .login("Test")
                .password("Test password")
                .role("ROLE_ADMIN")
                .email("test@test.pl")
                .userStrategy(UserStrategy.NORMAL_STRATEGY)
                .subscription(true)
                .money("1200")
                .coupons(new ArrayList<>())
                .build();
    }
}
