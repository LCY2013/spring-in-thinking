/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: thinking-in-spring-boot
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

import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.util.Lists;
import org.fufeng.data.springjpaoprator.domain.relationship.manytoone.User;
import org.fufeng.data.springjpaoprator.domain.relationship.manytoone.UserAddress;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program thinking-in-spring-boot
 * @description TODO
 * @create 2020-10-12
 */
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserAddressRepositoryTest {

    @Autowired
    UserAddressRepository userAddressRepository;

    @Autowired
    MoUserRepository moUserRepository;

    /**
     * 初始化数据
     */
    @BeforeAll
    @Rollback(false)
    @Transactional
    void init() {
        User user = User.builder().name("fufeng").email("fufeng@magic.com").build();
        UserAddress userAddress1 = UserAddress.builder().address("cd1").user(user).build();
        UserAddress userAddress2 = UserAddress.builder().address("cd2").user(user).build();
        userAddressRepository.saveAll(Lists.newArrayList(userAddress1,userAddress2));
    }

    /**
     * 测试用User关联关系操作
     * @throws JsonProcessingException
     */
    @Test
    @Rollback(false)
    public void testUserRelationships() throws JsonProcessingException {
        User user = moUserRepository.getOne(2L);
        System.out.println(user.getName());
        System.out.println(user.getAddress());
    }

}
