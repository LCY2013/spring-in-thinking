package org.fufeng.sca.order.controller;

import org.fufeng.sca.config.CustomConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author luocy
 * @description 动态配置控制器
 * @program customer-service
 * @create 2021-04-04
 * @since 1.0
 */
@RestController
public class ConfigController {

    /*
    非动态刷新，这里可以借助apollo实现@Value动态刷新
    @Value("${custom.flag}")
    private String flag;
    @Value("${custom.database}")
    private String database;*/

    /**
     * 动态刷新
     */
    @Resource
    private CustomConfig config;

    @GetMapping("/config")
    public String test(){
        //return "flag:" + config.getFlag() + "<br/> database:" + config.getDatabase();
        return config.toString();
    }


}
