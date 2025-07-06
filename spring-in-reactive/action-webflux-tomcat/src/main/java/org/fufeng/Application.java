package org.fufeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    public static void main(String[] args) {
//        new SpringApplicationBuilder()
//                .sources(Application.class)
//                .initializers((ApplicationContextInitializer<GenericApplicationContext>) ctx -> {
//                    ctx.registerBean("apiRoute", RouterFunction.class, RouterConfig::apiRoute);
//                })
//                .run(args);
//    }
}