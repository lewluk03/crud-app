package com.example.demo.configuration;

import com.example.demo.interceptor.CustomerInterceptorLogger;
import com.example.demo.interceptor.InterceptorLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
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
