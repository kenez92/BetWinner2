package com.kenez92.betwinner.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenez92.betwinner.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    private final static String URL_COUPON_TYPE = "/v1/coupons/types";
    private final static String URL_COUPONS = "/v1/coupons";
    private final static String URL_ORDERS = "/v1/orders";
    private final static String URL_USERS = "/v1/users";

    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final RestAuthenticationFailureHandler failureHandler;
    private final RestAuthenticationSuccessHandler successHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, URL_COUPON_TYPE).authenticated()
                .antMatchers(HttpMethod.GET, URL_COUPON_TYPE + "/{couponTypeId").authenticated()
                .antMatchers(HttpMethod.PUT, URL_COUPON_TYPE + "/{couponTypeId}").authenticated()
                .antMatchers(HttpMethod.DELETE, URL_COUPON_TYPE + "/{couponTypeId}").authenticated()

                .antMatchers(HttpMethod.GET, URL_COUPONS).authenticated()
                .antMatchers(HttpMethod.GET, URL_COUPONS + "/{couponId}").authenticated()
                .antMatchers(HttpMethod.POST, URL_COUPONS).authenticated()
                .antMatchers(HttpMethod.PUT, URL_COUPONS + "/check/{couponId}").authenticated()
                .antMatchers(HttpMethod.PUT, URL_COUPONS + "/{couponId}").authenticated()
                .antMatchers(HttpMethod.PUT, URL_COUPONS + "/{couponId}/{rate}").authenticated()
                .antMatchers(HttpMethod.DELETE, URL_COUPONS + "/{couponId}").hasRole(ADMIN)

                .antMatchers(HttpMethod.GET, URL_ORDERS).authenticated()
                .antMatchers(HttpMethod.GET, URL_ORDERS + "/{orderId}").authenticated()
                .antMatchers(HttpMethod.POST, URL_ORDERS).authenticated()
                .antMatchers(HttpMethod.PUT, URL_ORDERS).hasRole(ADMIN)
                .antMatchers(HttpMethod.DELETE, URL_ORDERS).hasRole(ADMIN)

                .antMatchers(HttpMethod.GET, URL_USERS).hasRole(ADMIN)
                .antMatchers(HttpMethod.GET, URL_USERS + "/{userId}").hasRole(ADMIN)
                .antMatchers(HttpMethod.POST, URL_USERS).permitAll()
                .antMatchers(HttpMethod.PUT, URL_USERS).hasRole(ADMIN)
                .antMatchers(HttpMethod.PUT, URL_USERS + "/{userId}/{strategy}").hasRole(ADMIN)
                .antMatchers(HttpMethod.PUT, URL_USERS + "/{userId}/add/{money}").hasRole(ADMIN)
                .antMatchers(HttpMethod.DELETE, URL_USERS + "/{userId}").hasRole(ADMIN)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(authenticationFilter())
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userService, RestAuthenticationSuccessHandler.secret))
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .headers().frameOptions().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JsonObjectAuthenticationFilter authenticationFilter() throws Exception {
        JsonObjectAuthenticationFilter authenticationFilter = new JsonObjectAuthenticationFilter(objectMapper);
        authenticationFilter.setAuthenticationSuccessHandler(successHandler);
        authenticationFilter.setAuthenticationFailureHandler(failureHandler);
        authenticationFilter.setAuthenticationManager(super.authenticationManager());
        return authenticationFilter;
    }
}
