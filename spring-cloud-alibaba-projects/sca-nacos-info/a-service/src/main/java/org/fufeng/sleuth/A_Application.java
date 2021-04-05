package org.fufeng.sleuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author luocy
 * @description 控制器
 * skywalking 启动参数如下：
 * -javaagent:/Users/magicLuoMacBook/software/java/apm/apache-skywalking-apm-bin-es7/agent/skywalking-agent.jar -Dskywalking.agent.service_name=a-service -Dskywalking.collector.backend_service=192.168.0.190:11800 -Dskywalking.logging.file_name=a-service-api.log
 * @program customer-service
 * @create 2021-04-04
 * @since 1.0
 */
@EnableFeignClients  // 声明这个可以使用feign
@EnableDiscoveryClient  // 声明这个是一个服务发现的客户端
@SpringBootApplication
public class A_Application {

    public static void main(String[] args) {
        SpringApplication.run(A_Application.class,args);
    }

}
