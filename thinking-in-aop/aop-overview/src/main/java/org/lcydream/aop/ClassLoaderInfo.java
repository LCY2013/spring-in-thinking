/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: thinking-in-spring
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2020-12-19
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.lcydream.aop;

import org.springframework.asm.ClassVisitor;
import org.springframework.cglib.proxy.Enhancer;

import java.net.URLClassLoader;
import java.util.Objects;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program thinking-in-spring
 * @description {@link ClassLoader} 示例
 * @create 2020-12-19
 * @see  ClassVisitor
 * @see org.springframework.context.annotation.ConfigurationClassEnhancer spring configuration标注的类被提升的地方
 * @see Enhancer
 * @see URLClassLoader#findClass(java.lang.String)
 */
public class ClassLoaderInfo {

    public static void main(String[] args) {
        // jdk.internal.loader.ClassLoaders$AppClassLoader@2c13da15
        ClassLoader contextClassLoader =
                Thread.currentThread().getContextClassLoader();

        for (; ; ) {
            System.out.println(contextClassLoader);
            contextClassLoader = contextClassLoader.getParent();
            if (Objects.isNull(contextClassLoader)) {
                break;
            }
        }
    }

}
