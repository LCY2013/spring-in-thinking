/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: thinking-in-spring-boot
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2020-10-22
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.intervention.service.remote;

import org.fufeng.intervention.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program thinking-in-spring-boot
 * @description 用户服务远程使用
 * @create 2020-10-22
 */
@Component
public class UserServiceRemote {

    /**
     *  用户服务地址
     */
    private static final String USER_SERVICE_URL = "http://user-service/users/";

    /**
     *  带有负载均衡的RestTemplate
     */
    private RestTemplate restTemplate;

    public UserServiceRemote(@Autowired RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     *  通过用户名称查询用户信息
     * @param userName 用户名称
     * @return 用户传输对象
     */
    public UserDto getUserByUserName(String userName){
        final ResponseEntity<UserDto> responseEntity = this.restTemplate.exchange(USER_SERVICE_URL + "{username}",
                HttpMethod.GET,
                null,
                UserDto.class,
                userName);
        final UserDto userDto = responseEntity.getBody();
        return userDto;
    }


}















