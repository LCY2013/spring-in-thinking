/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-07-06
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

import org.springframework.core.GenericCollectionTypeResolver;
import org.springframework.core.ResolvableType;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @program: spring-in-thinking
 * @description: {@link GenericCollectionTypeResolver} 示例
 *      4.3 以后这个类就被 {@link ResolvableType} 替换
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-07-06 21:20
 */
public class GenericCollectionTypeResolverDemo {

    private StringList stringList;

    private ArrayList<Object> objects;

    public static void main(String[] args) throws NoSuchFieldException {
        //getCollectionType() 获取的是具体泛型化的类型信息
        System.out.println(GenericCollectionTypeResolver.getCollectionType(StringList.class));
        //这里获取不到，因为并没有具体类型的泛型化
        System.out.println(GenericCollectionTypeResolver.getCollectionType(ArrayList.class));

        //获取成员变量的信息
        Field stringListField = GenericCollectionTypeResolverDemo.class.getDeclaredField("stringList");
        System.out.println((GenericCollectionTypeResolver.getCollectionFieldType(stringListField)));
        stringListField = GenericCollectionTypeResolverDemo.class.getDeclaredField("objects");
        System.out.println((GenericCollectionTypeResolver.getCollectionFieldType(stringListField)));
    }

}
