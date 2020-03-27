package org.lcydream.actuatorapplication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * management.endpoints.web.exposure.include=*
 * mvn spring-boot:run -Dmanagement.endpoints.web.exposure.include=beans,conditions,env
 *
 *  java -jar app.jar --name="luo" 可以通过外部命令行参数进行值覆盖
 *
 *  配置优先级:
 *   https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/reference/html/spring-boot-features.html#boot-features-external-config
 */
@SpringBootApplication
public class Application {

    @Value("${name}")
    private String name;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
