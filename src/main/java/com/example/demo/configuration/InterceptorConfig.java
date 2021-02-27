package com.example.demo.configuration;

import com.example.demo.interceptor.CustomerInterceptorLogger;
import com.example.demo.interceptor.InterceptorLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    CustomerInterceptorLogger customerInterceptorLogger;

    @Autowired
    InterceptorLogger interceptorLogger;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(customerInterceptorLogger)
                .addPathPatterns("/api/customer/**");


        registry.addInterceptor(interceptorLogger)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/customer/**");
    }
}
