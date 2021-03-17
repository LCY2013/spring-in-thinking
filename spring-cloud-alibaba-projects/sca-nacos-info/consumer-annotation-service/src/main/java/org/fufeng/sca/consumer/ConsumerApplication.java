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
package org.fufeng.sca.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program spring-cloud-alibaba-projects
 * @description 消费者启动类，通过注解驱动负载均衡相关客户端的逻辑
 * @create 2021-03-17
 * @since 1.0
 */
@SpringBootApplication
public class ConsumerApplication {

    /**
     * Ribbon 内置多种负载均衡策略，常用的分为以下几种。
     *
     * RoundRobinRule：
     * 轮询策略，Ribbon 默认策略。默认超过 10 次获取到的 server 都不可用，会返回⼀个空的 server。
     *
     * RandomRule：
     * 随机策略，如果随机到的 server 为 null 或者不可用的话。会不停地循环选取。
     *
     * RetryRule：
     * 重试策略，⼀定时限内循环重试。默认继承 RoundRobinRule，也⽀持自定义注⼊，RetryRule 会在每次选取之后，对选举的 server 进⾏判断，是否为 null，是否 alive，并且在 500ms 内会不停地选取判断。而 RoundRobinRule 失效的策略是超过 10 次，RandomRule 没有失效时间的概念，只要 serverList 没都挂。
     *
     * BestAvailableRule：
     * 最小连接数策略，遍历 serverList，选取出可⽤的且连接数最小的⼀个 server。那么会调用 RoundRobinRule 重新选取。
     *
     * AvailabilityFilteringRule：
     * 可用过滤策略。扩展了轮询策略，会先通过默认的轮询选取⼀个 server，再去判断该 server 是否超时可用、当前连接数是否超限，都成功再返回。
     *
     * ZoneAvoidanceRule：
     * 区域权衡策略。扩展了轮询策略，除了过滤超时和链接数过多的 server，还会过滤掉不符合要求的 zone 区域⾥⾯的所有节点，始终保证在⼀个区域/机房内的服务实例进行轮询。
     *
     * 这里所有负载均衡策略名本质都是 com.netflix.loadbalancer 包下的类
     *
     * 更改微服务通信时采用的负载均衡策略也很简单，在 application.yml 中采用下面格式书写即可。
     * provider-service: #服务提供者的微服务id
     *   ribbon:
     *     NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule #设置对应的负载均衡类
     *
     * @return {@link RestTemplate}
     */
    @Bean
    @LoadBalanced // 通过该注解直接使用引用的Ribbon的负载均衡特性加入到RestTemplate
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class,args);
    }

}
