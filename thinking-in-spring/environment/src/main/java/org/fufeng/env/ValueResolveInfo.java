/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-08-11
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.env;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @program: spring-in-thinking
 * @description: {@link Value} 注入示例
 *
 *  处理@Value的位置
 *  org.springframework.beans.factory.support.DefaultListableBeanFactory#doResolveDependency(org.springframework.beans.factory.config.DependencyDescriptor, java.lang.String, java.util.Set, org.springframework.beans.TypeConverter)
 *  org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver#getSuggestedValue(org.springframework.beans.factory.config.DependencyDescriptor)
 *      org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver#findValue(java.lang.annotation.Annotation[])
 *          org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver#extractValue(org.springframework.core.annotation.AnnotationAttributes)
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-11
 */
public class ValueResolveInfo {

    // 获取操作系统中的系统用户名称
    @Value("${user.name}")
    private String userName;

    public static void main(String[] args) {
        // 应用上下文
        final AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();
        context.register(ValueResolveInfo.class);
        context.refresh();

        final ValueResolveInfo valueResolveInfo =
                context.getBean(ValueResolveInfo.class);
        System.out.println(valueResolveInfo.userName);

        // 关闭应用上下文
        context.close();
    }

}
