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
package org.fufeng.batis.generator;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.fufeng.batis.entity_.User;
import org.fufeng.batis.entity_.UserExample;
import org.fufeng.batis.mapper.UserMapper;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

/**
 * @program: spring-in-thinking
 * @description: mybatis 生成器使用
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-07
 */
public class MybatisGeneratorInfo {

    public static void main(String[] args) throws UnsupportedEncodingException {
        // 获取当前线程的类加载器
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // 读取配置文件
        final InputStream resourceAsMybatis =
                classLoader.getResourceAsStream("mybatis/mybatis-config.xml");
        // 将输入流转换成Reader
        final InputStreamReader reader =
                new InputStreamReader(resourceAsMybatis, "UTF-8");
        // 创建数据库Session
        final SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        final SqlSessionFactory dev =
                sqlSessionFactoryBuilder.build(reader, "dev", new Properties());
        final SqlSession sqlSession = dev.openSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        UserExample userExample = new UserExample();

        UserExample.Criteria criteria = userExample.createCriteria();

        criteria.andIdEqualTo(1);

        List<User> users = userMapper.selectByExample(userExample);

        if (users.size() > 0) {
            User user = users.get(0);
            System.out.println(user);
        }else {
            System.out.println("没有查询到对应的用户数据");
        }
        sqlSession.close();
    }

}
