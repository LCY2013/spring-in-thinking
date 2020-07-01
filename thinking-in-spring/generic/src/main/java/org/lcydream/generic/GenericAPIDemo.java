/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-07-01
 * @version : 2.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.lcydream.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * @program: spring-in-thinking
 * @description: java 泛型API 示例
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-07-01 21:38
 */
public class GenericAPIDemo {

    public static void main(String[] args) {
        //原始类型 primary type : int float double boolean ...
        Class intClass = int.class;

        //数组类型 array type : int[] long[] string[]
        Class arrayClass = int[].class;

        //引用类型 reference type : String,Object
        Class refClass = String.class;

        //泛型类型 集合泛型
        Class genericClass = ArrayList.class;
        System.out.println(genericClass);

        //带参数的泛型
        final ParameterizedType genericSuperclass = (ParameterizedType) HashMap.class.getGenericSuperclass();
        System.out.println(genericSuperclass);

        //<E>
        Stream.of(genericSuperclass.getActualTypeArguments())
                .map(TypeVariable.class::cast) //Type -> TypeVariable
                .forEach(System.out::println);
    }
}
