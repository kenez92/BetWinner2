package com.kenez92.betwinner.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginCredentials {
    private String password;
    private String login;
}
