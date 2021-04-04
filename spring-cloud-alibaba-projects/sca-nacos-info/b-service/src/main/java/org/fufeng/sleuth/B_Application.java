package org.fufeng.sleuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author luocy
 * @description b 服务启动类
 * @program customer-service
 * @create 2021-04-04
 * @since 1.0
 */
@EnableFeignClients  // 声明这个可以使用feign
@EnableDiscoveryClient  // 声明这个是一个服务发现的客户端
@SpringBootApplication
public class B_Application {

    public static void main(String[] args) {
        SpringApplication.run(B_Application.class,args);
    }

}

