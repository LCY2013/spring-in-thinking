/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: thinking-in-spring
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2020-10-26
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.data;

import org.fufeng.data.domain.User;
import org.fufeng.data.domain.UserAddress;
import org.fufeng.data.repository.UserAddressRepository;
import org.fufeng.data.repository.UserRepository;
import org.fufeng.data.repository.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program thinking-in-spring
 * @description {@link UserRepository} 测试用例
 * @create 2020-10-26
 */
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {

    /**
     * 利用该方式获得entityManager
     */
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAddressRepository userAddressRepository;

    @Autowired
    private UsersRepository usersRepository;

    private final Date date = new Date();

    /**
     * 负责添加数据，假设数据库里面已经有的数据
     */
    @BeforeAll
    @Rollback(false)
    @Transactional
    void init(){
        User user = User.builder().name("fufeng").age(18).email("fufeng@magic.com")
                .updateDate(date).createDate(Instant.now()).build();
        final UserAddress userAddress = UserAddress.builder()
                .address("cd").user(user).build();

        //userRepository.save(user);
        userAddressRepository.save(userAddress);
    }

    @Test
    @Rollback(value = false)
    public void testEntityManager(){
        //测试找到一个User对象
        User user = entityManager.find(User.class,2L);
        Assertions.assertEquals(user.getAddresses().get(0).getAddress(),"cd");

        //我们改变一下user的删除状态
        user.setDeleted(true);

        //merge方法
        entityManager.merge(user);

        //更新到数据库里面
        entityManager.flush();

        //再通过createQuery创建一个JPQL，进行查询
        List<User> users =  entityManager.createQuery("select u From User u where u.name=?1")
                .setParameter(1,"fufeng")
                .getResultList();
        Assertions.assertTrue(users.get(0).getDeleted());
    }

    @Test
    public void testCustomizedUserRepository() {
        //查出来一个User对象
        User user = usersRepository.findById(2L).get();
        //调用我们的逻辑删除方法进行删除
        usersRepository.logicallyDelete(user);
        //我们再重新查出来，看看值变了没有
        List<User> users = usersRepository.findAll();
        Assertions.assertEquals(users.get(0).getDeleted(),Boolean.TRUE);
    }

}
