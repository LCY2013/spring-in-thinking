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
import org.fufeng.batis.entity.User;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

/**
 * @program: spring-in-thinking
 * @description: mybatis xml 配置使用
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-07
 */
public class MybatisXmlConfigurationInfo {

    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("user.dir"));
        final ResourceLoader resourceLoader = new DefaultResourceLoader();
        final Resource resource =
                resourceLoader.getResource("classpath:/mybatis/mybatis-config.xml");
        final EncodedResource encodedResource = new EncodedResource(resource);
        final Reader reader = encodedResource.getReader();
        final SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        final SqlSessionFactory dev = builder.build(reader, "dev", new Properties());
        final SqlSession sqlSession = dev.openSession();
        User user = sqlSession.selectOne("org.fufeng.batis.mapper.UserMapper.selectOneUser",1);
        System.out.println(user);
        sqlSession.close();
    }

}
