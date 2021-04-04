package org.fufeng.sleuth.controller;

import org.fufeng.sleuth.feign.B_ServiceFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author luocy
 * @description 简单控制器
 * @program customer-service
 * @create 2021-04-04
 * @since 1.0
 */
@RestController
public class SampleController {

    @Resource
    private B_ServiceFeignClient bService;

    @GetMapping("/a")
    public String methodA() {
        String result = bService.methodB();
        result = "-> Service A" + result;
        return result;
    }
}
