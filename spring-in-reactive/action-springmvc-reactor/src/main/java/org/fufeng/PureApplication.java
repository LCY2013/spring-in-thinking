package org.fufeng;

import org.fufeng.reactive.functional.RouterConfig;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactor.netty.http.server.HttpServer;

import java.util.concurrent.CountDownLatch;

public class PureApplication {
    public static void main(String[] args) throws InterruptedException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));
        CountDownLatch latch = new CountDownLatch(1);
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(RouterFunctions.toHttpHandler(RouterConfig.apiRoute()));
        HttpServer.create().host("localhost").port(8087).handle(adapter).bind().block();
        latch.await();
    }
}
