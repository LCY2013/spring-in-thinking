/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-08-01
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.project.annotation.aliases;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @program: spring-in-thinking
 * @description: {@link AliasFor} 别名显示传递演示 {@link ComponentScan} 组件扫描器
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-01
 * @see ComponentScan
 * @see AliasFor
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ComponentScan
public @interface OneCustomAliasesComponentScan {

    /*
        注解属性显示传递依赖
           value -> basePackages
           basePackages -> value
        @AliasFor("basePackages")
        String[] value() default {};
        @AliasFor("value")
        String[] basePackages() default {};
    */

    // 隐性别名 利用注解属性引用父注解和父注解的属性
    //@AliasFor(annotation = ComponentScan.class, attribute = "basePackages")
    @AliasFor(annotation = ComponentScan.class, attribute = "value")
    String[] scanBasePackages() default {};

    // 本例子传递关系如下
    // scanBasePackages ->
    //          @AliasFor ComponentScan.basePackages() ->
    //             @AliasFor ComponentScan.value() (显性别名)
    //  @AliasFor ComponentScan.basePackages() 隐性别名

}
