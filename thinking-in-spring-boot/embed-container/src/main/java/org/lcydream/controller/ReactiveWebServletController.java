package org.lcydream.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RouterFunctions.route;
import static org.springframework.web.servlet.function.ServerResponse.ok;

@Configuration
public class ReactiveWebServletController {

    @Bean
    public RouterFunction<ServerResponse> webServletReactive(){
        return route(GET("/web/servlet/reactive"), serverRequest ->
            ok().body(Mono.just("web servlet reactive"))
        );
    }

}
