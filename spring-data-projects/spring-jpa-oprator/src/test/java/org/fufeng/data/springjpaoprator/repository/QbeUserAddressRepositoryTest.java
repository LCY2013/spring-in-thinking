/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: thinking-in-spring-boot
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2020-10-15
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.data.springjpaoprator.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.fufeng.data.springjpaoprator.domain.qbe.SexEnum;
import org.fufeng.data.springjpaoprator.domain.qbe.User;
import org.fufeng.data.springjpaoprator.domain.qbe.UserAddress;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Date;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program thinking-in-spring-boot
 * @description {@link QbeUserAddressRepository} 测试用例
 * @create 2020-10-15
 */
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QbeUserAddressRepositoryTest {

    @Autowired
    QbeUserAddressRepository qbeUserAddressRepository;

    Date date = new Date();

    /**
     * 负责添加数据，假设数据库里面已经有的数据
     */
    @BeforeAll
    @Rollback(false)
    @Transactional
    void init(){
        User user = User.builder().name("fufeng").age(18).sex(SexEnum.BOY).email("fufeng@magic.com")
                .updateDate(date).createDate(Instant.now()).build();

        qbeUserAddressRepository.saveAll(Lists.newArrayList(
                UserAddress.builder().address("CD").user(user).build(),
                UserAddress.builder().address("BJ").user(user).build(),
                UserAddress.builder().address("SH").user(user).build()
        ));
    }

    @Test
    @Rollback(false)
    public void testQbeUserAddress() throws JsonProcessingException {
        final User user = User.builder().name("fufeng").email("fufeng@magic.com").build();
        final UserAddress userAddress = UserAddress.builder().user(user).address("CD").build();

        final ObjectMapper objectMapper = new ObjectMapper();
        System.out.println((objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(userAddress)));

        // 创建Example 查询实体匹配器
        // 满足 email 前缀匹配、地址前缀匹配的动态查询条件
        /*final ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("user.email",ExampleMatcher.GenericPropertyMatchers.startsWith())
                .withMatcher("address",ExampleMatcher.GenericPropertyMatchers.startsWith());*/

        //创建匹配器，即如何使用查询条件

        ExampleMatcher exampleMatcher = ExampleMatcher
                //采用默认and的查询方式
                .matchingAll()
                //忽略大小写
                .withIgnoreCase()
                //忽略所有null值的字段
                .withIgnoreNullValues()
                .withIgnorePaths("id","createDate")
                //默认采用精准匹配规则
                .withStringMatcher(ExampleMatcher.StringMatcher.EXACT)
                //级联查询，字段user.email采用字符前缀匹配规则
                .withMatcher("user.email", ExampleMatcher.GenericPropertyMatchers.startsWith())
                //特殊指定address字段采用后缀匹配
                .withMatcher("address", ExampleMatcher.GenericPropertyMatchers.endsWith());

        final Page<UserAddress> userAddresses =
                qbeUserAddressRepository.findAll(Example.of(userAddress, exampleMatcher),
                        PageRequest.of(0, 2));
        System.out.println((objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(userAddresses)));
    }

}
