package org.fufeng;

import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactor.netty.http.server.HttpServer;
import org.fufeng.reactive.functional.RouterConfig;

import java.util.concurrent.CountDownLatch;

public class PureApplication {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(RouterFunctions.toHttpHandler(RouterConfig.apiRoute()));
        HttpServer.create().host("localhost").port(8087).handle(adapter).bind().block();
        latch.await();
    }
}
