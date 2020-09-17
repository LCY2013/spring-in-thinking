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
package org.fufeng.data.springjpaoprator.controller;

import org.fufeng.data.springjpaoprator.domain.User;
import org.fufeng.data.springjpaoprator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @program: thinking-in-spring-boot
 * @description: 控制器
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-09-16
 */
@RestController
@RequestMapping(path = "/api/v1")
public class UserController {

    private final UserRepository userRepository;

    public UserController(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     *  保存新用户
     * @param user 用户 User
     * @return 返回一个带有主键的User
     */
    @PostMapping(path = "user",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public User addNewUser(@RequestBody User user){
        return this.userRepository.save(user);
    }

    /**
     *  分页查询用户信息
     * @param request  分页参数实体
     * @return 分页的用户信息
     */
    @GetMapping(path = "users")
    public Page<User> findAllUser(Pageable request){
        return this.userRepository.findAll(request);
    }

    /**
     * 验证排序和分页查询方法，Pageable的默认实现类：PageRequest
     * @return 分页的用户信息
     */
    @GetMapping(path = "/page")
    @ResponseBody
    public Page<User> getAllUserByPage() {
        return userRepository.findAll(
                PageRequest.of(1, 20, Sort.by(new Sort.Order(Sort.Direction.ASC,"name"))));
    }
    /**
     * 排序查询方法，使用Sort对象
     * @return 分页的用户信息
     */
    @GetMapping(path = "/sort")
    @ResponseBody
    public Iterable<User> getAllUsersWithSort() {
        return userRepository.findAll(Sort.by(new Sort.Order(Sort.Direction.ASC,"name")));
    }

}
