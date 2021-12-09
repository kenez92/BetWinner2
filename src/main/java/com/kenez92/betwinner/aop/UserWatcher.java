package com.kenez92.betwinner.aop;

import com.kenez92.betwinner.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@RequiredArgsConstructor
@Component
class UserWatcher {
    private final UserService userService;

    @AfterReturning("execution(* com.kenez92.betwinner.users.UserService.createUser(..))")
    public void logEvent() {
        Long quantityOfUsers = userService.getQuantityOfUsers();
        log.info("We have new user :) , now we have : " + quantityOfUsers + " users");
    }
}
