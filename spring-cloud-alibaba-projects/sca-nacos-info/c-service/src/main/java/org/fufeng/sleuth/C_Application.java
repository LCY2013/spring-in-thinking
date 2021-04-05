package org.fufeng.sleuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author luocy
 * @description c服务应用
 * skywalking 启动参数如下：
 * -javaagent:/Users/magicLuoMacBook/software/java/apm/apache-skywalking-apm-bin-es7/agent/skywalking-agent.jar -Dskywalking.agent.service_name=c-service -Dskywalking.collector.backend_service=192.168.0.190:11800 -Dskywalking.logging.file_name=a-service-api.log
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
