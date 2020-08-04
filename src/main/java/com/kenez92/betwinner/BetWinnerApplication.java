package com.kenez92.betwinner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class BetWinnerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BetWinnerApplication.class, args);
    }

}
