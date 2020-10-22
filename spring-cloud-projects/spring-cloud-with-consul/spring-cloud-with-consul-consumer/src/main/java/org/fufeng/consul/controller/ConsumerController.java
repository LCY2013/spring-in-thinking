/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: thinking-in-spring
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2020-08-27
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.consul.controller;

import org.fufeng.consul.service.IProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static org.fufeng.consul.service.IProviderService.PROVIDER_APPLICATION_NAME;

/**
 * @program: thinking-in-spring
 * @description: 消费者 控制层实现REST API
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-27
 */
@RestController
public class ConsumerController {

    final IProviderService providerService;

    final DiscoveryClient discoveryClient;

    final RestTemplate restTemplate;

    final LoadBalancerClient loadBalancerClient;

    public ConsumerController(@Autowired IProviderService providerService,
                              @Autowired DiscoveryClient discoveryClient,
                              @Autowired RestTemplate restTemplate,
                              @Autowired LoadBalancerClient loadBalancerClient) {
        this.providerService = providerService;
        this.discoveryClient = discoveryClient;
        this.restTemplate = restTemplate;
        this.loadBalancerClient = loadBalancerClient;
    }

    /**
     *  使用原生的RestTemplate实现服务调用
     * @return RestTemplate调用结果
     */
    @GetMapping("/rest")
    String rest(){
        String url = "http://"+PROVIDER_APPLICATION_NAME+"/provider";
        return this.restTemplate.getForObject(url,String.class);
    }

    /**
     *  使用OpenFeign去实现服务调用
     * @return OpenFeign调用结果
     */
    @GetMapping("/feign")
    String feign(){
        return this.providerService.info();
    }

    /**
     *  获取客户端发现的所有服务信息
     * @return 某个服务的所有实例
     */
    @GetMapping("/services")
    Object services(){
        return this.discoveryClient.getInstances(PROVIDER_APPLICATION_NAME);
    }

    /**
     *  从当前设置的某个应用中选择其中一个服务实例
     * @return 返回某个应用中的服务实例
     */
    @GetMapping("/choose")
    Object choose(){
        return this.loadBalancerClient.choose(PROVIDER_APPLICATION_NAME);
    }

}
