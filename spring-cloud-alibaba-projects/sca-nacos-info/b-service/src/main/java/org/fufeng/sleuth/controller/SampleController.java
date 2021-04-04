package org.fufeng.sleuth.controller;

import org.fufeng.sleuth.feign.C_ServiceFeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author luocy
 * @description 示例控制器
 * @program customer-service
 * @create 2021-04-04
 * @since 1.0
 */
@Controller
public class SampleController {
    @Resource
    private C_ServiceFeignClient cService;
    @GetMapping("/b")
    @ResponseBody
    public String methodB(){
        String result = cService.methodC();
        result = " -> Service B" + result;
        return result;
    }
}