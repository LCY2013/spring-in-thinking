/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-cloud-alibaba-projects
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2021-02-27
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.lcydream.aop.staticproxy;

import org.lcydream.aop.utils.DateUtil;

import java.util.Date;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program spring-cloud-alibaba-projects
 * @description 静态代理
 * @create 2021-02-27
 */
public class ProxyEchoService implements EchoService {

    /**
     *  持有的代理对象
     */
    private EchoService echoService;

    public ProxyEchoService(EchoService echoService) {
        this.echoService = echoService;
    }

    @Override
    public void echo(String message) {
        log(echoService.getClass());
        echoService.echo(message);
        log(echoService.getClass());
    }

    /**
     *  打印执行方法的名称
     * @param methodClass 方法
     */
    private void log(Class<?> methodClass){
        System.out.printf("[%s] star [%s]\n", DateUtil.yyyy_MM_dd_HH_mm_ss_sss(new Date()),methodClass.getName());
    }
}
