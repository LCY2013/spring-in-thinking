/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: thinking-in-spring-boot
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2020-09-16
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
import org.fufeng.data.springjpaoprator.domain.User;
import org.fufeng.data.springjpaoprator.dto.UserPojo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Streamable;

import java.util.List;
import java.util.stream.Stream;

/**
 * @program: thinking-in-spring-boot
 * @description: 用户仓储测试
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-09-16
 */
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void testSaveUser() {
        final User user = this.userRepository
                .save(User.builder().name("fufeng").email("fufeng@magic.com").build());
        Assert.assertNotNull(user);
        System.out.println(user);
        final List<User> users = this.userRepository.findAll();
        Assert.assertNotNull(users);
        System.out.println(users);
    }

    @Test
    public void testStreamable() {
        final User user = this.userRepository
                .save(User.builder().name("fufeng").email("fufeng@magic.com").build());
        Assert.assertNotNull(user);
        System.out.println(user);
        final Streamable<User> users = this.userRepository.findAll(
                PageRequest.of(0, 10, Sort.by("name")))
                .and(User.builder().name("fufeng").build());
        users.map(userMap -> {
            userMap.setName(userMap.getName() + " - Streamable");
            return userMap;
        }).forEach(System.out::println);
    }

    @Test
    public void testQuery() throws JsonProcessingException {

        // 插入测试数据
        this.userRepository.save(User.builder().name("fufeng1").email("fufeng@magic.com").build());
        this.userRepository.save(User.builder().name("fufeng2").email("fufeng@magic.com").build());
        this.userRepository.save(User.builder().name("fufeng3").email("fufeng@magic.com").build());
        this.userRepository.save(User.builder().name("fufeng4").email("fufeng@magic.com").build());
        this.userRepository.save(User.builder().name("fufeng5").email("fufeng@magic.com").build());
        this.userRepository.save(User.builder().name("fufeng6").email("fufeng@magic.com").build());

        // json to String
        ObjectMapper objectMapper = new ObjectMapper();

        final Slice<User> allByCustomQueryAndSlice =
                this.userRepository.findAllByCustomQueryAndSlice(PageRequest.of(0, 10));
        allByCustomQueryAndSlice.map(user -> {
            user.setName("slice - "+user.getName());
            return user;
        }).forEach(System.out::println);

        System.out.println("-----------------------");

        final Stream<User> allByCustomQueryAndStream =
                this.userRepository.findAllByCustomQueryAndStream(PageRequest.of(0, 10));
        allByCustomQueryAndStream.map(user -> {
            user.setName("stream - "+user.getName());
            return user;
        }).forEach(System.out::println);

        System.out.println("-----------------------");

        final Page<User> users = this.userRepository.findAll(PageRequest.of(0, 3));
        System.out.println(objectMapper.writeValueAsString(users));

    }

    @Test
    public void testUserPojo(){
        this.userRepository.save(User.builder().name("fufeng").email("fufeng@magic.com").build());
        final UserPojo userPojo = this.userRepository.getOneByEmail("fufeng@magic.com");
        System.out.println(userPojo.getName());
    }

}
