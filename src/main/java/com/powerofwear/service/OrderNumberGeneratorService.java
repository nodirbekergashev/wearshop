package com.powerofwear.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderNumberGeneratorService {

    private final JdbcTemplate jdbcTemplate;

    public OrderNumberGeneratorService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long generate() {

        return jdbcTemplate.queryForObject(
                "SELECT nextval('order_number_seq')",
                Long.class
        );
    }
}