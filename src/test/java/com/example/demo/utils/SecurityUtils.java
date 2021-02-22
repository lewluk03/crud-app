package com.example.demo.utils;

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static com.example.demo.SecurityTestConfig.TEST_USER;
import static com.example.demo.SecurityTestConfig.TEST_USER_PASSWORD;

public class SecurityUtils {

    public static RequestPostProcessor httpBasicForTest() {
        return SecurityMockMvcRequestPostProcessors.httpBasic(TEST_USER, TEST_USER_PASSWORD);
    }

}
