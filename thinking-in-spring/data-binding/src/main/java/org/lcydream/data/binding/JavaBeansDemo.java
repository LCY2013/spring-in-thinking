package org.lcydream.data.binding;

import org.lcydream.data.binding.domain.Student;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.stream.Stream;

/**
 * @program: spring-in-thinking
 * @description: 关于 JavaBeans 核心API {@link BeanInfo} 示例
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-06-08 09:05
 */
public class JavaBeansDemo {

    public static void main(String[] args) throws IntrospectionException {
        //通过Introspector 获取 Student bean信息, 排除Object对象
        final BeanInfo beanInfo = Introspector.getBeanInfo(Student.class,Object.class);
        //输出所有的属性描述信息 PropertyDescriptor
        //会获取到Object内的属性class，所以需要在获取BeanInfo的时候排除Object对象
        Stream.of(beanInfo.getPropertyDescriptors())
                .forEach(propertyDescriptor -> {
                    //获取可读方法 getXX()
                    //propertyDescriptor.getReadMethod();
                    //获取可写方法 setXX()
                    //propertyDescriptor.getWriteMethod();
                    System.out.println(propertyDescriptor);
                });

        //获取所有的方法表述信息
        //这里获取的方法表述信息包含了所有的字段
        Stream.of(beanInfo.getMethodDescriptors())
                .forEach(System.out::println);
    }

}
