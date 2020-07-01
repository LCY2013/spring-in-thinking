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

import java.util.ArrayList;
import java.util.List;

/**
 * @program: spring-in-thinking
 * @description: 泛型在java语言中的使用
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-07-01 21:25
 */
public class GenericTest {

    public static void main(String[] args) {
        //创建一个String类型的容器
        List<String> strList = new ArrayList<>();
        //向容器添加数据
        strList.add("fu");
        strList.add("feng");
        //这里就不能通过编译，泛型检测
        //strList.add(1);

        //接下来就可以绕过泛型检测
        List integerList = strList;
        integerList.add(7);

        //这里就会进行类型转换，会抛出类型转换异常
        //strList.forEach(System.out::println);

        //这里就不会异常，因为integerList没有泛型编译，所以会返回object不存在类型转换
        integerList.forEach(System.out::println);

        //这里直接打印出数据就可以避免类型转换
        System.out.println(strList);
    }
}
