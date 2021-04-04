package org.fufeng.sleuth.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author luocy
 * @description b service代理客户端
 * @program customer-service
 * @create 2021-04-04
 * @since 1.0
 */
@FeignClient("b-service")
public interface B_ServiceFeignClient {

    /**
     *  方法b
     * @return 方法
     */
    @GetMapping("/b")
    String methodB();

}
