/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: thinking-in-spring-boot
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2020-08-27
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.config.controller;

import lombok.extern.slf4j.Slf4j;
import org.fufeng.config.config.MysqlComplexConfig;
import org.fufeng.config.config.MysqlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: thinking-in-spring-boot
 * @description: mysql配置类展示控制器
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-27
 */
@Slf4j
@RestController
public class MysqlConfigController {

    final MysqlConfig mysqlConfig;

    final MysqlComplexConfig mysqlComplexConfig;

    public MysqlConfigController(@Autowired MysqlConfig mysqlConfig,
                                 @Autowired MysqlComplexConfig mysqlComplexConfig) {
        this.mysqlConfig = mysqlConfig;
        this.mysqlComplexConfig = mysqlComplexConfig;
    }

    @GetMapping("/mysql/host")
    String mysqlHost(){
        log.info("host {}",this.mysqlConfig.getHost());
        return this.mysqlConfig.getHost();
    }

    @GetMapping("/mysql/user")
    Object mysqlUser(){
        log.info(this.mysqlComplexConfig.getHost()+" {}",
                this.mysqlComplexConfig.getUser());
        return this.mysqlComplexConfig.getUser();
    }
}
