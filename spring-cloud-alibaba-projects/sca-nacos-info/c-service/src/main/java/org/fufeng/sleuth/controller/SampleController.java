package org.fufeng.sleuth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author luocy
 * @description 简单控制器
 *  SampleController，methodC方法产生响应字符串“-> Service C”，方法映射地址“/c”
 * @program customer-service
 * @create 2021-04-04
 * @since 1.0
 */
@RestController
public class SampleController {

    @GetMapping("/c")
    public String methodC(){
        return " -> Service C";
    }

}
