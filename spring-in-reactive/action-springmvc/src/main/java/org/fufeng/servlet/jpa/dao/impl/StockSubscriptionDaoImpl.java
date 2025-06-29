//package com.viso.jpa.dao.impl;
//
//import com.viso.jpa.dao.StockSubscriptionDao;
//import com.viso.jpa.model.StockSubscription;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public class StockSubscriptionDaoImpl implements StockSubscriptionDao {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    private final String SELECT_BY_EMAIL = "SELECT * FROM STOCK_SUBSCRIPTION WHERE EMAIL = ?";
//
//    @Override
//    public List<StockSubscription> findByEmail(String email) {
//        return jdbcTemplate.query(SELECT_BY_EMAIL, new BeanPropertyRowMapper<>(StockSubscription.class), email);
//    }
//}
