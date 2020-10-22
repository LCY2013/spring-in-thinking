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

import org.fufeng.data.springjpaoprator.domain.relationship.onetoone.oneway.User;
import org.fufeng.data.springjpaoprator.domain.relationship.onetoone.oneway.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program thinking-in-spring
 * @description one to one 测试用例
 * @create 2020-10-12
 */
@DataJpaTest
public class OneToOneOneWayRepositoryTest {

    @Autowired
    OneToOneUserInfoRepository oneToOneUserInfoRepository;

    @Autowired
    OneToOneUserRepository oneToOneUserRepository;

    @Test
    public void testOneToOne(){
//        oneToOneUserRepository
//                .save(User.builder().address("cd").email("fufeng@magic.com").id(1L).sex("nan").name("fufeng").build());
        oneToOneUserInfoRepository.save(UserInfo.builder().ages(18).telephone("238742").user(User.builder().address("cd").email("fufeng@magic.com").id(1L).sex("nan").name("fufeng").build()).build());

        final Optional<UserInfo> userInfo = oneToOneUserInfoRepository.findById(1L);
        System.out.println(userInfo.get());
    }

}
