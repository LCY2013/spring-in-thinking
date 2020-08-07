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
package org.fufeng.controller;

import org.fufeng.domain.User;
import org.fufeng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * @program: spring-in-thinking
 * @description: jdbc 控制器
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-06
 */
@RestController
public class JdbcController {

    @Autowired
    DataSource dataSource;

    @Autowired
    UserService userService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/jdbc/meta/transaction/supported")
    public boolean supportedTransaction() {
        boolean supported = false;
        try (Connection connection = dataSource.getConnection()) {
            final DatabaseMetaData metaData = connection.getMetaData();
            supported = metaData.supportsTransactions();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return supported;
    }

    @RequestMapping("/users")
    public List<Map<String, Object>> getUsers() {
        return jdbcTemplate.execute((StatementCallback<List<Map<String, Object>>>) stmt -> {
            final ResultSet resultSet = stmt.executeQuery("select id,name,age from user");
            // 获取结果集的元信息
            final ResultSetMetaData metaData = resultSet.getMetaData();
            // 获取表的总列数
            final int columnCount = metaData.getColumnCount();
            final List<String> columnNames = new ArrayList<>(columnCount);
            for (int i = 1; i <= columnCount; i++) {
                // 获取表列名
                final String columnName = metaData.getColumnName(i);
                columnNames.add(columnName);
            }
            // 构造结果集
            final List<Map<String,Object>> resultList = new ArrayList<>();
            while (resultSet.next()){
                final Map<String,Object> columnData = new LinkedHashMap<>();
                for (String columnName : columnNames){
                    final Object columnValue = resultSet.getObject(columnName);
                    columnData.put(columnName,columnValue);
                }
                resultList.add(columnData);
            }
            return resultList;
        });
    }

    @RequestMapping("/user/get")
    public Map<String,Object> getUser(@RequestParam(value = "id",
            required = false,defaultValue = "1") int id){
        Map<String,Object> resultMap = new HashMap<>();
        Connection connection = null;
        Savepoint savepoint = null;
        try {
            connection = dataSource.getConnection();
            // 设置手动提交
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();
            final PreparedStatement preparedStatement = connection.prepareStatement(
                    "select id,name,age from `user` where id = ?");
            preparedStatement.setInt(1,id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                final int id_ = resultSet.getInt("id");
                final String name_ = resultSet.getString("name");
                final int age_ = resultSet.getInt("age");
                resultMap.put("id",id_);
                resultMap.put("name",name_);
                resultMap.put("age",age_);
            }
            connection.commit();
        } catch (SQLException throwables) {
            if (Objects.nonNull(connection)){
                try {
                    connection.rollback(savepoint);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            throwables.printStackTrace();
        } finally {
            if (Objects.nonNull(connection)){
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return resultMap;
    }

    @RequestMapping(value = "/user/add",method = RequestMethod.POST)
    public Map<String,Object> addUser(@RequestBody User user){
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("successMethodAdd",userService.add(user));
        resultMap.put("successMethodSave",userService.save(user));
        return resultMap;
    }

}
