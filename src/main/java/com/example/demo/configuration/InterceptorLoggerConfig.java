package com.example.demo.configuration;

import com.example.demo.interceptor.InterceptorLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class InterceptorLoggerConfig implements WebMvcConfigurer {

    @Autowired
    InterceptorLogger interceptorLogger;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptorLogger);
    }
}
