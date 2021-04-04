package org.fufeng.sleuth.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author luocy
 * @description c 服务feign客户端
 * @program customer-service
 * @create 2021-04-04
 * @since 1.0
 */
@FeignClient("c-service")
public interface C_ServiceFeignClient {

    /**
     *  调用方法C
     * @return 方法c字符串
     */
    @GetMapping("/c")
    String methodC();

}
