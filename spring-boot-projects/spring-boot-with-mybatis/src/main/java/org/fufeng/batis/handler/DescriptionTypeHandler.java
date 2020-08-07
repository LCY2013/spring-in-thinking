/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-08-07
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.batis.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.fufeng.batis.entity.Description;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @program: spring-in-thinking
 * @description: 自定义mybatis的 类型转换器 {@link TypeHandler}
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-07
 * @see TypeHandler
 */
public class DescriptionTypeHandler implements TypeHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setParameter(PreparedStatement preparedStatement,
                             int i, Object parameter, JdbcType jdbcType) throws SQLException {
        try {
            final StringWriter writer = new StringWriter();
            objectMapper.writeValue(writer,parameter);
            final String writeContext = writer.toString();
            preparedStatement.setString(i,writeContext);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getResult(ResultSet resultSet, String columnName) throws SQLException {
        final String columnValue = resultSet.getString(columnName);
        Description description = null;
        try {
            if (StringUtils.hasText(columnValue)){
                description = objectMapper.readValue(columnValue,Description.class);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return description;
    }

    @Override
    public Object getResult(ResultSet resultSet, int columnIndex) throws SQLException {
        final String columnValue = resultSet.getString(columnIndex);
        Description description = null;
        try {
            if (StringUtils.hasText(columnValue)){
                description = objectMapper.readValue(columnValue,Description.class);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return description;
    }

    @Override
    public Object getResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        final String columnValue = callableStatement.getString(columnIndex);
        Description description = null;
        try {
            if (StringUtils.hasText(columnValue)){
                description = objectMapper.readValue(columnValue,Description.class);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return description;
    }

}
