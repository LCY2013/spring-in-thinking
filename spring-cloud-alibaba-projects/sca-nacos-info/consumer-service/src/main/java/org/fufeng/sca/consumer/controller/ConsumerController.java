/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-cloud-alibaba-projects
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2021-03-17
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.sca.consumer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program spring-cloud-alibaba-projects
 * @description 消费端控制器
 *  通过API方式实现负载均衡
 *  先从 Nacos 获取可用服务提供者实例信息，再通过 RestTemplate.getForObject() 向该实例发起 RESTful 请求完成处理。
 *  但代码模式使用复杂，需要自己获取可用实例 IP、端口信息，再拼接 URL 实现服务间通信
 *  那有没有更简单的办法呢？可以利用 @LoadBalanced 注解可自动化实现这一过程。
 * @create 2021-03-17
 * @since 1.0
 */
@Slf4j
@RestController
public class ConsumerController {

    /**
     * 注入 Ribbon 负载均衡器对象
     * 在引入 starter-netflix-ribbo n后在 SpringBoot 启动时会自动实例化 LoadBalancerClient 对象。
     * 在 Controlle 使用 @Resource 注解进行注入即可
     */
    @Resource
    private LoadBalancerClient loadBalancerClient;

    /**
     * 注入自定义的RestTemplate
     */
    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/msg")
    public String getProviderMessage() {
        // loadBalancerClient.choose() 方法会从Nacos获取所有的provider-service可用实例
        // 按照特定的负载均衡规则选择一个可用的服务实例，封装成ServiceInstance对象
        final ServiceInstance serviceInstance =
                loadBalancerClient.choose("provider-service");
        // 通过获取的服务实例获取对应的主机地址
        final String host = serviceInstance.getHost();
        // 通过获取的服务实例获取对应的主机端口
        final int port = serviceInstance.getPort();
        // 打印本次获取到的服务实例的信息
        log.info("本次调用由 {} 提供服务 , Host : {} , Port : {} , 实例节点处理", serviceInstance.getServiceId(), host, port);
        //通过 RestTemplate 对象的 getForObject() 方法向指定 URL 发送请求，并接收响应。
        //getForObject()方法有两个参数：
        //1. 具体发送的 URL，结合当前环境发送地址为：http://host:port/provider/msg
        //2. String.class说明 URL 返回的是纯字符串，如果第二参数是实体类， RestTemplate 会自动进行反序列化，为实体属性赋值
        final String result = restTemplate.getForObject(String.format("http://%s:%s/provider/msg", host, port), String.class);
        // 记录响应信息
        log.info("{} 响应信息是: {}",serviceInstance.getServiceId(),result);
        // 反馈给浏览器
        return String.format("consumer service 响应数据: %s",result);
    }
}
