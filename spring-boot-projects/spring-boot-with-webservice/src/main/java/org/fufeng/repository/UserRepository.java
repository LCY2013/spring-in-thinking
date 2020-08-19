/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-08-19
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.repository;

import org.fufeng.domain.User;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: spring-in-thinking
 * @description: 用户仓储
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-19
 */
@Repository
public class UserRepository {

    // 定义仓储的容器
    private final Map<Long, User> userRepository = new ConcurrentHashMap<>();

    @PostConstruct
    public void init(){
        userRepository.put(1L,creteUser(1L,"l",18));
        userRepository.put(2L,creteUser(2L,"c",19));
        userRepository.put(3L,creteUser(3L,"y",20));
    }

    public User getUserById(long id){
        return userRepository.get(id);
    }

    /**
     *  创建用户对象方法
     * @param id id
     * @param name 名称
     * @param age 年龄
     * @return 用户对象
     */
    private User creteUser(long id, String name, int age) {
        final User user = new User();
        user.setId(id);
        user.setAge(age);
        user.setName(name);
        return user;
    }

}
