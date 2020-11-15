/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: thinking-in-spring
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2020-11-15
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.intervention.hystrix;

import com.netflix.hystrix.*;
import org.fufeng.intervention.dto.UserDto;
import org.fufeng.intervention.service.remote.UserServiceRemote;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program thinking-in-spring
 * @description 获取用户信息熔断
 * @create 2020-11-15
 */
public class GetUserCommand extends HystrixCommand<UserDto> {

    /**
     * 远程调用 user-service 的客户端工具类
     */
    private UserServiceRemote userServiceRemote;

    /**
     * 构造熔断器
     * <p>
     * HystrixCommandGroupKey 和 HystrixCommandKey 分别用来配置服务分组名称和服务名称
     * HystrixThreadPoolKey 用来配置线程池的名称
     *
     * @param name 线程池名称
     */
    protected GetUserCommand(String name) {
        super(Setter.withGroupKey(
                // 设置命令组建
                HystrixCommandGroupKey.Factory.asKey("springCloudGroup"))
                // 设置命令建
                .andCommandKey(HystrixCommandKey.Factory.asKey("interventionKey"))
                // 设置线程池建
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(name))
                // 设置命令属性
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withExecutionTimeoutInMilliseconds(5000))
                // 设置线程池属性
                .andThreadPoolPropertiesDefaults(
                        HystrixThreadPoolProperties.Setter()
                                .withMaxQueueSize(5)
                                .withCoreSize(Runtime.getRuntime().availableProcessors())
                )
        );
    }

    @Override
    protected UserDto run() throws Exception {
        return userServiceRemote.getUserByUserName("fufeng");
    }

    @Override
    protected UserDto getFallback() {
        return new UserDto();
    }
}
