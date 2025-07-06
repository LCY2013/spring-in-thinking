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
//                    System.out.println("在程序运行前向上下文注入SampleService");
//                    ctx.registerBean("sampleService", SampleService.class, SampleService::new);
//                })
//                .run(args);
//    }
}