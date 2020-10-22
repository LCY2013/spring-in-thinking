/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: thinking-in-spring
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2020-10-12
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

import org.fufeng.data.springjpaoprator.domain.relationship.onetoone.twoway.User;
import org.fufeng.data.springjpaoprator.domain.relationship.onetoone.twoway.UserInfo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program thinking-in-spring
 * @description TODO
 * @create 2020-10-12
 */
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OneToOneTwoWayRepositoryTest {

    @Autowired
    OneToOneTwoWayUserRepository oneToOneTwoWayUserRepository;

    @Autowired
    OneToOneTwoWayUserInfoRepository oneToOneTwoWayUserInfoRepository;

    @BeforeAll
    @Rollback(false)
    @Transactional
    void init() {
        User user = User.builder().name("fufeng").email("fufeng@magic.com").build();
        UserInfo userInfo = UserInfo.builder().ages(18).user(user).telephone("12345678").build();
        oneToOneTwoWayUserInfoRepository.saveAndFlush(userInfo);
    }

    @Test
    public void testOneToOneTwoWayUser(){
        oneToOneTwoWayUserRepository.save(User.builder()
        .address("cd").email("fufeng@magic.com").name("fufeng").sex("nan")
        .userInfo(UserInfo.builder().ages(18).telephone("2274842").build()).build());

        final Optional<User> user = oneToOneTwoWayUserRepository.findById(1L);

        System.out.println(user.get());
    }

    @Test
    public void testCascadeType(){
        final User user = User.builder().address("CD").email("fufeng@magic.com").name("fufeng").sex("nan").build();
        final UserInfo userInfo = UserInfo.builder().ages(18).telephone("2274842").user(user).build();

        // 保存UserInfo的时候级联保存User
        oneToOneTwoWayUserInfoRepository.saveAndFlush(userInfo);

        // 删除UserInfo的时候级联删除User
        oneToOneTwoWayUserInfoRepository.delete(userInfo);

        System.out.println();
    }

    @Test
    @Rollback(value = false)
    public void testUserRelationships(){
        final UserInfo userInfo = oneToOneTwoWayUserInfoRepository.getOne(1L);
        System.out.println(userInfo);
        System.out.println(userInfo.getUser().getId());
    }

}
