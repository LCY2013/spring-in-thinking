package org.fufeng;

import org.fufeng.reactive.functional.RouterConfig;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.reactive.function.server.RouterFunction;

@SpringBootApplication
public class Application {
//    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
//    }

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(Application.class)
                .web(WebApplicationType.REACTIVE)
                .initializers((ApplicationContextInitializer<GenericApplicationContext>) ctx -> {
                    ctx.registerBean("apiRoute", RouterFunction.class, RouterConfig::apiRoute);
                })
                .run(args);
    }
}