/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: thinking-in-spring
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2020-10-23
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

import org.assertj.core.util.Lists;
import org.fufeng.data.domain.User;
import org.fufeng.data.repository.UserRepository;
import org.fufeng.data.specifica.MagicSpecification;
import org.fufeng.data.specifica.domain.Operator;
import org.fufeng.data.specifica.domain.SearchCriteria;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program thinking-in-spring
 * @description Specification 测试用例
 * @create 2020-10-23
 */
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MagicSpecificationTest {

    @Autowired
    private UserRepository userRepository;

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

        userRepository.save(user);
    }

    /**
     * 测试自定义的Specification语法
     */
    @Test
    public void givenLast_whenGettingListOfUsers_thenCorrect() {
        MagicSpecification<User> name =
                new MagicSpecification<>(new SearchCriteria("name", Operator.LK, "fufeng"));
        MagicSpecification<User> age =
                new MagicSpecification<>(new SearchCriteria("age", Operator.GT, 2));
        List<User> results = userRepository.findAll(Specification.where(name).and(age));
        results.forEach(System.out::println);
    }

}
