package com.kenez92.betwinner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//@EnableAspectJAutoProxy
//@SpringBootApplication
//public class BetWinnerApplication {
//
//    public static void main(String[] args) {
//        SpringApplication.run(BetWinnerApplication.class, args);
//    }
//
//}

@EnableAspectJAutoProxy
@SpringBootApplication
public class BetWinnerApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(BetWinnerApplication.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BetWinnerApplication.class);
    }
}
