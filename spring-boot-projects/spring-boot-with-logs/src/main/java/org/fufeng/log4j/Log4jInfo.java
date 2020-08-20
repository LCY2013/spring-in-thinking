/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-08-20
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.log4j;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.log4j.xml.DOMConfigurator;

import java.net.URL;

import static org.apache.log4j.LogManager.DEFAULT_CONFIGURATION_KEY;

/**
 * @program: spring-in-thinking
 * @description: log4j 使用示例
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-20
 */
public class Log4jInfo {

    public static void main(String[] args) {
        // 设置系统环境信息
        System.setProperty(DEFAULT_CONFIGURATION_KEY,"log4j-conf.xml");
        Logger logger = Logger.getLogger(Log4jInfo.class);
        logger.setLevel(Level.INFO);
        logger.info("hello log4j");

        // 重新加载log4j 配置信息
        final URL resource = Thread.currentThread()
                .getContextClassLoader()
                .getResource("log4j-api.xml");
        DOMConfigurator.configure(resource);
        MDC.put("requestURI","http://www.baidu.com");
        logger = Logger.getLogger(Log4jWarn.class);
        // 跳转日志输出级别
        logger.info("hello log4j");
        logger.error("hello fufeng");
    }

}
