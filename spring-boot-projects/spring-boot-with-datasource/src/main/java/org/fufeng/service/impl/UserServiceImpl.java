/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-08-06
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.service.impl;

import org.fufeng.domain.User;
import org.fufeng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @program: spring-in-thinking
 * @description: User服务实现
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-06
 * @see UserService
 */
@Service
@EnableTransactionManagement
public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Override
    @Transactional
    public boolean save(User user) {
        if (Objects.isNull(user)){
            throw new RuntimeException("user 信息不能为空");
        }
        return jdbcTemplate.execute("insert into user(name,age) values(?,?)", (PreparedStatementCallback<Boolean>) ps -> {
            ps.setString(1,user.getName());
            ps.setInt(2,user.getAge());
            return ps.executeUpdate() > 0;
        });
    }

    @Override
    @Transactional
    public boolean add(User user) {
        final DefaultTransactionDefinition defaultTransactionDefinition =
                new DefaultTransactionDefinition();
        final TransactionStatus transactionStatus =
                platformTransactionManager.getTransaction(defaultTransactionDefinition);
        try {
            final boolean save = save(user);
            platformTransactionManager.commit(transactionStatus);
            return save;
        }catch (Exception e){
            platformTransactionManager.rollback(transactionStatus);
        }
        return false;
    }
}
