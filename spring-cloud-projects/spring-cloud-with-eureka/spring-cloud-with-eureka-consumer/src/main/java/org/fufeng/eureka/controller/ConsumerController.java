/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2020 Ramostear.All Rights Reserved.
 *
 * ProjectName: thinking-in-spring
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2020-08-26
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.eureka.controller;

import org.fufeng.eureka.feign.IProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static org.fufeng.eureka.feign.IProviderService.APPLICATION_NAME;

/**
 * @program: thinking-in-spring
 * @description: 消费服务的REST API
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-26
 */
@RestController
public class ConsumerController {

    final IProviderService providerService;

    final RestTemplate restTemplate;

    /**
     *  注入ProviderService提供的Feign服务
     * @param providerService Feign代理对象
     */
    public ConsumerController(@Autowired IProviderService providerService,
                              @Autowired RestTemplate restTemplate) {
        this.providerService = providerService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/feign/hello")
    public String hello(){
        return providerService.hello();
    }

    @GetMapping("/rest/info")
    public String info(){
        final String url = "http://"+APPLICATION_NAME+"/info";
        return this.restTemplate.getForObject(url,String.class);
    }

}
