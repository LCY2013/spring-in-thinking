package org.fufeng.sleuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author luocy
 * @description c服务应用
 * @program customer-service
 * @create 2021-04-04
 * @since 1.0
 */
@EnableDiscoveryClient  // 声明这个是一个服务发现的客户端
@SpringBootApplication
public class C_Application {

    public static void main(String[] args) {
        SpringApplication.run(C_Application.class,args);
    }

}
