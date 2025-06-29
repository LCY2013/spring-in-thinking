//package com.viso.jpa.dao.impl;
//
//import com.viso.jpa.dao.StockDao;
//import com.viso.jpa.model.Stock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public class StockDaoImpl implements StockDao {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    private final String SELECT_ALL = "SELECT * FROM STOCK";
//    private final String SELECT_BY_SYMBOL = "SELECT * FROM STOCK WHERE SYMBOL = ?";
//
//    @Override
//    public List<Stock> findAll() {
//        return jdbcTemplate.query(SELECT_ALL, new BeanPropertyRowMapper<>(Stock.class));
//    }
//
//    @Override
//    public Stock findBySymbol(String symbol) {
//        return jdbcTemplate.queryForObject(SELECT_BY_SYMBOL, new BeanPropertyRowMapper<>(Stock.class), symbol);
//    }
//
//}
