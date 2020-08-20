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
package org.fufeng.mbean.dynamic;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

/**
 * @program: spring-in-thinking
 * @description: {@link }
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-20
 */
public class DynamicMBeanInfo {

    public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException {
        // MBean server - agent level
        final MBeanServer platformMBeanServer =
                ManagementFactory.getPlatformMBeanServer();

        // 创建注册对象
        final SimpleDynamicMBean simpleDynamicMBean = new SimpleDynamicMBean();

        // 创建注册的名字
        final ObjectName objectName = createObjectName(simpleDynamicMBean);

        // 注册MBean - SimpleDynamicMBean
        platformMBeanServer.registerMBean(simpleDynamicMBean,objectName);

        // 模拟死锁让主线程不休眠
        try {
            TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *  创建一个MBean 对象名称
     * @return ObjectName
     */
    private static ObjectName createObjectName(Object mBean) throws MalformedObjectNameException {
        final Class<?> mBeanClass = mBean.getClass();
        final String packageName = mBeanClass.getPackageName();
        final String simpleName = mBeanClass.getSimpleName();
        return new ObjectName(packageName+":type="+simpleName);
    }

}
