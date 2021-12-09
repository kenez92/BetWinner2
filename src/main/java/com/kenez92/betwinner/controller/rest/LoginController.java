package com.kenez92.betwinner.controller.rest;

import com.kenez92.betwinner.users.UserDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/login")
    public void login(@RequestBody UserDto userDto) {
    }
}
