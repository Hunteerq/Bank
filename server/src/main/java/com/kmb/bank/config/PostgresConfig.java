package com.kmb.bank.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


@Configuration
public class PostgresConfig {

    @Value("${jdbc.url}")
    private String postgresUrl;

    @Value("${jdbc.username}")
    private String postgresUsername;

    @Value("${jdbc.password}")
    private String postgresPassword;

    @Value("${jdbc.driver}")
    private String postgresDriver;


    @Bean
    public JdbcTemplate jdbcTemplate() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(postgresDriver);
        dataSource.setUrl(postgresUrl);
        dataSource.setUsername(postgresUsername);
        dataSource.setPassword(postgresPassword);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        return jdbcTemplate;
    }



}
