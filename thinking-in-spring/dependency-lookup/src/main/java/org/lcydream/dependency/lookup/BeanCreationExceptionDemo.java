package org.lcydream.dependency.lookup;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;

/**
 * {@link BeanCreationException} 异常演示
 */
public class BeanCreationExceptionDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext();
        //注册配置Bean信息
        annotationConfigApplicationContext.register(BeanCreationExceptionDemo.class);

        BeanDefinitionBuilder beanDefinitionBuilder =
                BeanDefinitionBuilder.genericBeanDefinition(BeanCreationClass.class);
        annotationConfigApplicationContext.registerBeanDefinition("BeanCreationClass",
                beanDefinitionBuilder.getBeanDefinition());

        //启动应用上下文
        annotationConfigApplicationContext.refresh();

        //关闭应用上下文
        annotationConfigApplicationContext.close();
    }

    public class BeanCreationClass implements InitializingBean {

        @PostConstruct
        public void init() throws Throwable{
            throw new Exception("init exception");
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            int num = 1/0;
        }
    }

}
