package org.lcydream.dependency.lookup;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *  {@link BeanInstantiationException}
 */
public class BeanInstantiationExceptionDemo {

    public static void main(String[] args) {
        //创建应用上下文
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext();
        //注册配置Bean信息
        annotationConfigApplicationContext.register(NoUniqueBeanDefinitionExceptionDemo.class);

        //生成一个BeanDefinition构造器
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(AbstractClass.class);
        annotationConfigApplicationContext.registerBeanDefinition("abstractClass",beanDefinitionBuilder.getBeanDefinition());

        //启动应用上下文
        annotationConfigApplicationContext.refresh();

        //关闭应用上下文
        annotationConfigApplicationContext.close();
    }

    public interface AbstractClass{

    }

}
