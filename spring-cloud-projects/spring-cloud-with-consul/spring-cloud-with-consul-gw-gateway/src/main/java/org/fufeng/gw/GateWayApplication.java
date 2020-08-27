/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: thinking-in-spring-boot
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
package org.fufeng.gw;

import org.fufeng.gw.config.IpResolver;
import org.fufeng.gw.config.filter.CustomerFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @program: thinking-in-spring-boot
 * @description: 网关 启动实例
 *   路由规则可以通过两种方式实现:
 *      (1):通过配置文件实现 - application.yml or applicaiton.yml
 *      (2):通过GateWay API实现 本列API配置如下:
 *              builder.routes()
 *                 .route("userRouter", r -> r.path("/user-service/**")
 *                         .filters(f ->
 *                                 f.addResponseHeader("X-CustomerHeader", "kite"))
 *                         .uri("lb://consul-user")
 *                 )
 *                 .route("orderRouter", r -> r.path("/order-service/**")
 *                         .filters(f -> f.stripPrefix(1)
 *                         )
 *                         .uri("lb://consul-order")
 *                 )
 *                 .build();
 *
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-27
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class,args);
    }

    /**
     *  通过API方式实现路由表设置
     * @param builder 构建路由规则
     * @return 路由规则加载器
     */
    /*@Bean
    public RouteLocator kiteRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("userRouter", r -> r.path("/user-service/**")
                        .filters(f ->
                                f.addResponseHeader("X-CustomerHeader", "wkx"))
                        .uri("lb://consul-user")
                )
                .route("orderRouter", r -> r.path("/order-service/**")
                        .filters(f -> f.stripPrefix(1)
                        )
                        .uri("lb://consul-order")
                )
                .build();
    }*/

    //    @Autowired
//    private KeyResolver ipResolver;

    @Bean
    public RouteLocator kiteRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("userRouter", r -> r.path("/user-service/**")
                        .filters(f ->
                                f.addResponseHeader("X-CustomerHeader", "wkx"))
                        .uri("lb://consul-user")
                )
                .route("orderRouter", r -> r.path("/order-service/**")
                        .filters(f -> f.stripPrefix(1)
                        )
                        .uri("lb://consul-order")
                )
                .route("hystrixRouter", r -> r.path("/hystrix/**")
                        .filters(f -> f.stripPrefix(1)
                                .hystrix(h -> h.setName("second2Command").setFallbackUri("forward:/hystrixfallback"))
                        )
                        .uri("lb://consul-user")
                )
                .route("limit_route", r -> r.path("/limiter/**")
                        .filters(f -> f.stripPrefix(1)
                                .requestRateLimiter(
                                        c -> c.setKeyResolver(ipResolver())
                                                .setRateLimiter(redisRateLimiter())
                                )
                        )
                        .uri("lb://consul-user"))
                .route("oauth_server", r -> r.path("/oauth-service/**")
                        .filters(f -> f.filter(new CustomerFilter()))
                        .uri("http://localhost:5010"))
                .route("oauth_client", r -> r.path("/consul-oauth-client/**")
                        .uri("http://localhost:5012"))
                .build();
    }

    @Bean
    IpResolver ipResolver() {
        return new IpResolver();
    }

    @Bean
    RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(1, 1);
    }


    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
        return http.csrf().disable().authorizeExchange()
                .anyExchange().permitAll()
                .and()
                .build();
//        return http.httpBasic().and()
//                .csrf().disable()
//                .authorizeExchange()
//                .pathMatchers("/limiter/**").authenticated()
//                .anyExchange().permitAll()
//                .and()
//                .build();
    }

    @Bean
    public MapReactiveUserDetailsService reactiveUserDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build();
        return new MapReactiveUserDetailsService(user);
    }

}
