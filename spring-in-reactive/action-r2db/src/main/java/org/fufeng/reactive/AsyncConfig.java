package org.fufeng.reactive;//package com.org.fufeng.reactive;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.security.concurrent.DelegatingSecurityContextExecutor;
//
//import java.util.concurrent.Executor;
//
//
//@Configuration
//public class AsyncConfig extends AsyncConfigurerSupport {
//    @Bean(name="asyncExecutor")
//    public Executor asyncExecutor(){
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(3);
//        executor.setMaxPoolSize(3);
//        executor.setQueueCapacity(100);
//        executor.setThreadNamePrefix("AsyncMvc-");
//        executor.initialize();
//        return executor;
//        // if you define this bean ONLY,
//        // you can you use @Async("asyncExecutor") at method level
//    }
//
////    @Override
////    public Executor getAsyncExecutor() {
////        return asyncExecutor();
////        // this is a more appropriate way:
////        //      - implements AsyncConfigurer
////        //      - override getAsyncExecutor method
////    }
//
//    @Override
//    public Executor getAsyncExecutor(){
//        return new DelegatingSecurityContextExecutor(asyncExecutor());
//    }
//}
