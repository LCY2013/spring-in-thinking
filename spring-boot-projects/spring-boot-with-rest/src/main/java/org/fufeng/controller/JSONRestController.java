/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-08-06
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @program: spring-in-thinking
 * @description: JSON 格式的REST接口信息
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-06
 */
@RestController
public class JSONRestController {

    @Bean
    public User currentUser(){
        final User user = new User();
        user.setAge(18);
        user.setName("fufeng");
        return user;
    }

    @Autowired
    @Qualifier("currentUser")
    private User user;

    @GetMapping(path = "/json/user",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public User user(){
        user.removeLinks();
        user.add(linkTo(methodOn(JSONRestController.class).setUserName(user.getName()+System.currentTimeMillis())).withSelfRel());
        user.add(linkTo(methodOn(JSONRestController.class).setUserAge(user.getAge())).withSelfRel());
        return user;
    }

    @PostMapping(path = "/json/user/set/name",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public User setUserName(@RequestParam String name){
        user.setName(name);
        return user;
    }

    @PostMapping(path = "/json/user/set/age",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public User setUserAge(@RequestParam Integer age){
        user.setAge(age);
        return user;
    }

}
