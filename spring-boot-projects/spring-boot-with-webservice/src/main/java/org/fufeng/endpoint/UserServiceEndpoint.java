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
package org.fufeng.endpoint;

import org.fufeng.domain.User;
import org.fufeng.domain.UserIdRequest;
import org.fufeng.domain.UserResponse;
import org.fufeng.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.time.Instant;
import java.time.ZonedDateTime;

import static java.time.ZoneId.systemDefault;

/**
 * @program: spring-in-thinking
 * @description: 用户 webService 服务端点
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-19
 */
@Endpoint
public class UserServiceEndpoint {

    @Autowired
    private UserRepository userRepository;

    @PayloadRoot(namespace = "http://fufeng.org/schemas",localPart = "UserIdRequest")
    @ResponsePayload
    public UserResponse getUser(@RequestPayload UserIdRequest userIdRequest){
        final long userId = userIdRequest.getUserId();
        final Instant instant = Instant.ofEpochMilli(userIdRequest.getTimestamp());
        final ZonedDateTime zonedDateTime = instant.atZone(systemDefault());
        System.out.println("web service userId : "+userId+ " 请求时间 : "+zonedDateTime);
        final User user = userRepository.getUserById(userId);
        final UserResponse userResponse = new UserResponse();
        userResponse.setUser(user);
        userResponse.setTimestamp(Instant.now().toEpochMilli());
        return userResponse;
    }

}
