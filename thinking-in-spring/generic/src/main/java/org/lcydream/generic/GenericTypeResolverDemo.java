/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-07-02
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

import org.springframework.core.GenericTypeResolver;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;
import java.util.Map;

import static org.springframework.core.GenericTypeResolver.*;

/**
 * @program: spring-in-thinking
 * @description: {@link GenericTypeResolver} spring 泛型工具集
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-07-02 21:45
 * @see GenericTypeResolver
 */
public class GenericTypeResolverDemo {

    public static void main(String[] args) throws NoSuchMethodException {
        //String 类型是 Comparable<String> 的具体实现
        displayReturnTypeGenericInfo(GenericTypeResolverDemo.class,Comparable.class,"getString");
        //List<Object> 是List泛型参数具体化
        displayReturnTypeGenericInfo(GenericTypeResolverDemo.class,List.class,"getList");
        //StringList 是List泛型参数具体化
        displayReturnTypeGenericInfo(GenericTypeResolverDemo.class,List.class,"getStringList");

        // 具备 ParameterizedType 返回，否则 null

        //TypeVariable
        final Map<TypeVariable, Type> typeVariableMap = getTypeVariableMap(StringList.class);
        System.out.println(typeVariableMap);
    }

    public static StringList getStringList(){
        return null;
    }

    public static List<Object> getList(){ //泛型固定
        return null;
    }

    public static String getString(){
        return null;
    }

    /**
     *  展示返回类型的参数信息
     * @param containingClass 主类
     * @param genericInfo 泛型类
     * @param methodName 方法名称
     * @param argumentTypes 参数类列表
     */
    public static void displayReturnTypeGenericInfo(Class<?> containingClass,
                                                    Class<?> genericInfo,
                                                    String methodName,
                                                    Class... argumentTypes) throws NoSuchMethodException {
        //获取主类的方法
        final Method method = containingClass.getMethod(methodName, argumentTypes);
        //主类的普通方法返回类类型
        final Class<?> resolveReturnType = resolveReturnType(method, containingClass);
        //输出主类的方法返回值类型
        System.out.printf("resolveReturnType(%s,%s) = %s\n",methodName,containingClass.getSimpleName(),resolveReturnType);
        //主类的泛型类参数返回
        final Class<?> returnTypeArgument = resolveReturnTypeArgument(method, genericInfo);
        System.out.printf("resolveReturnTypeArgument(%s,%s) = %s\n",methodName,genericInfo.getSimpleName(),returnTypeArgument);
    }

}
