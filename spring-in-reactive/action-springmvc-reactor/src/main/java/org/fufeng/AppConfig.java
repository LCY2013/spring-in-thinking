package org.fufeng;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAsync
@ComponentScan(basePackages = {"org.fufeng.security", "org.fufeng.reactive", "org.fufeng.jpa"})
@Slf4j
public class AppConfig {
    //    @Bean
//    public ThymeleafViewResolver thymeleafViewResolver(ISpringTemplateEngine engine) {
//        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
//        resolver.setTemplateEngine(engine);
//        resolver.setCharacterEncoding("UTF-8");
//        return resolver;
//    }
    public static void main(String[] args) throws InterruptedException {
        log.info("time to get before flux");
        Flux<String> flux = Flux.defer(() -> Flux.fromIterable(generateIterable()))
                .subscribeOn(Schedulers.newSingle("singleT"));
        log.info("time to get after flux");
        flux.subscribe(System.out::println);
        Thread.sleep(10000);

//        Mono.defer(() -> Mono.fromFuture())
    }

    private static List<String> generateIterable() {
        log.info("start generateiterable");
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            log.error("unlikelly");
        }
        List<String> result = new ArrayList<>();
        result.add("foo");
        result.add("bar");
        result.add("baz");
        return result;
    }
}
