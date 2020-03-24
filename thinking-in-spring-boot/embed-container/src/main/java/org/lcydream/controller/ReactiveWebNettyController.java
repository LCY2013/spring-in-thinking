package org.lcydream.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class ReactiveWebNettyController {

    @Bean
    public RouterFunction<ServerResponse> webNettyReactive(){
        return route(GET("/web/netty/reactive"),
                serverRequest -> ok().body(Mono.just("web netty reactive"),String.class));
    }

}
