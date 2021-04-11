package org.fufeng.sca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luocy
 * @description gateway启动类
 * @program customer-service
 * @create 2021-04-10
 * @since 1.0
 */
@RestController
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @GetMapping("/get")
    public ResponseEntity<?> proxy() throws Exception {
        return ResponseEntity.ok("success");
    }

}
