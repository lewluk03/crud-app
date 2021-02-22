package com.example.demo;

import com.example.demo.configuration.SecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;

@Profile("test")
@Configuration
@EnableWebSecurity
public class SecurityTestConfig extends SecurityConfig {

    public static final String TEST_USER = "admin";
    public static final String TEST_USER_PASSWORD = "nimda";

    @Override
    public void configure(AuthenticationManagerBuilder authentication)
            throws Exception {
        authentication.jdbcAuthentication()
                .dataSource(dataSource)
                .withDefaultSchema()
                .withUser(
                        User.withUsername(TEST_USER)
                                .password(passwordEncoder().encode(TEST_USER_PASSWORD))
                                .roles("USER"));
    }
}
