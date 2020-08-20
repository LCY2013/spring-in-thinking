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
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static javax.management.MBeanOperationInfo.ACTION_INFO;

/**
 * @program: spring-in-thinking
 * @description: {@link DynamicMBean} 实现
 *  <p>
 *      自定义一个动态管理Bean
 *     <ul>
 *         <li>定义一个value属性</li>
 *         <li></li>
 *     </ul>
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-20
 * @see DynamicMBean
 */
public class SimpleDynamicMBean implements DynamicMBean {

    /**
     *  动态管理Bean 一个值
     */
    private String value;

    /**
     *  定义一个集合操作
     */
    private final Map<String,Object> showData = new ConcurrentHashMap<>();

    @Override
    public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException {
        return showData.get(attribute);
    }

    @Override
    public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
        final String name = attribute.getName();
        final Object value = attribute.getValue();
        this.showData.put(name,value);
        if ("value".equals(name)){
            this.value = value+"";
        }
    }

    @Override
    public AttributeList getAttributes(String[] attributes) {
        final AttributeList attributeList = new AttributeList();
        for (String attr : attributes){
            if (Objects.nonNull(attr) && !"".equals(attr.trim())) {
                final Attribute attribute = new Attribute(attr, this.showData.get(attr));
                attributeList.add(attribute);
            }
        }
        return attributeList;
    }

    @Override
    public AttributeList setAttributes(AttributeList attributes) {
        for (Object attribute : attributes){
            if (attribute instanceof Attribute){
                Attribute attr = (Attribute)attribute;
                this.showData.put(attr.getName(),attr.getValue());
                if ("value".equals(attr.getName())){
                    this.value = attr.getValue() + "";
                }
            }
        }
        return attributes;
    }

    @Override
    public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
        // 类似于API方法调用
        if ("displayValue".equals(actionName)){
            return this.value;
        }else if ("displayShowData".equals(actionName)){
            return this.showData;
        }
        return null;
    }

    @Override
    public MBeanInfo getMBeanInfo() {
        return new MBeanInfo(this.getClass().getName(),
                "simple MBean",
                of(new MBeanAttributeInfo("value",String.class.getName(),"动态属性Value",true,true,false)
                        ,new MBeanAttributeInfo("showData",Map.class.getName(),"动态属性Data",true,true,false)),
                of(new MBeanConstructorInfo(this.getClass().getSimpleName(), "默认构造器", new MBeanParameterInfo[0])),
                of(new MBeanOperationInfo("displayValue", "自定义 displayValue 方法", new MBeanParameterInfo[0], String.class.getName(), ACTION_INFO),
                        new MBeanOperationInfo("displayShowData", "自定义 displayShowData 方法", new MBeanParameterInfo[0], Map.class.getName(), ACTION_INFO)),
                new MBeanNotificationInfo[0]);
    }

    /**
     *  定义将可变参数转换成数组
     * @param array 可变参数
     * @param <T> 任意类型
     * @return 任意类型的数组
     */
    private static <T> T[] of(T... array){
        return array;
    }
}
