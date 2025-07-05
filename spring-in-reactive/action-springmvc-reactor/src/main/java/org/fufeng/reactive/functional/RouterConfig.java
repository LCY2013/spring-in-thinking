package org.fufeng.reactive.functional;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

public class RouterConfig {
    public static RouterFunction<ServerResponse> apiRoute() {
        ApiHandler handler = new ApiHandler();
        return route(GET("/api/prices"), handler::getPrices)
                .and(route(GET("/api/price/{symbol}"), handler::getPrice));
    }
}
