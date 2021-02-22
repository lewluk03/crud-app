package com.example.demo.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties(prefix="spring.datasource")
public class DBConfig {

    private static Logger logger = LoggerFactory.getLogger(DBConfig.class);

    private String driverClassName;
    private String url;
    private String username;
    private String password;

    @Profile("dev")
    @Bean
    public String devDatabaseConnection(){
        logger.info("DB Connection for Dev profile");
        logger.info(driverClassName);
        logger.info(url);
        return "DB Connection for Dev profile";
    }

    @Profile("prod")
    @Bean
    public String prodDatabaseConnection(){
        logger.info("DB Connection for Prod profile");
        logger.info(driverClassName);
        logger.info(url);
        return "DB Connection for Prod profile";
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
