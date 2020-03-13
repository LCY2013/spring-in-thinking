package org.lcydream.dependency.lookup;

import org.lcydream.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 类型安全的依赖查找示例
 */
public class TypeSafeDependencyLookup {

    public static void main(String[] args) {
        //创建应用上下文
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext();
        //注册配置Bean信息
        annotationConfigApplicationContext.register(TypeSafeDependencyLookup.class);
        //启动应用上下文
        annotationConfigApplicationContext.refresh();

        //测试BeanFactory#getBean()
        displayBeanFactoryGetBean(annotationConfigApplicationContext);
        //测试ObjectFactory#getObject()
        displayObjectFactoryGetBean(annotationConfigApplicationContext);
        //测试ObjectProvider#getIfAvailable()
        displayObjectProviderGetBean(annotationConfigApplicationContext);
        //测试DefaultListableBeanFactory#getBeansOfType()
        displayListableBeanFactoryGetBeans(annotationConfigApplicationContext);
        //测试测试ObjectProvider#Stream()
        displayObjectProviderStreamGetBeans(annotationConfigApplicationContext);

        //关闭应用上下文
        annotationConfigApplicationContext.close();
    }

    private static void displayObjectProviderStreamGetBeans(AnnotationConfigApplicationContext annotationConfigApplicationContext) {
        ObjectProvider<User> beanProvider =
                annotationConfigApplicationContext.getBeanProvider(User.class);
        printBeansException("displayObjectProviderStreamGetBeans",()->beanProvider.stream().forEach(System.err::println));
    }

    private static void displayListableBeanFactoryGetBeans(AnnotationConfigApplicationContext annotationConfigApplicationContext) {
        DefaultListableBeanFactory defaultListableBeanFactory =
                annotationConfigApplicationContext.getDefaultListableBeanFactory();
        printBeansException("displayListableBeanFactoryGetBeans",()->defaultListableBeanFactory.getBeansOfType(User.class));
    }

    private static void displayObjectProviderGetBean(AnnotationConfigApplicationContext annotationConfigApplicationContext) {
        final ObjectProvider<User> beanProvider =
                annotationConfigApplicationContext.getBeanProvider(User.class);
        printBeansException("displayObjectProviderGetBean",()->beanProvider.getIfAvailable());
    }

    private static void displayObjectFactoryGetBean(AnnotationConfigApplicationContext annotationConfigApplicationContext) {
        //ObjectProvider is ObjectFactory
        final ObjectProvider<User> beanProvider =
                annotationConfigApplicationContext.getBeanProvider(User.class);
        ObjectFactory<User> objectFactory = beanProvider;
        printBeansException("displayObjectFactoryGetBean",()->objectFactory.getObject());
    }

    private static void displayBeanFactoryGetBean(BeanFactory beanFactory) {
        printBeansException("displayBeanFactoryGetBean",()->beanFactory.getBean(User.class));
    }

    private static void printBeansException(String source,Runnable runnable){
        System.err.println("source from :"+source+"\n");
        try {
            runnable.run();
        }catch (BeansException e){
            e.printStackTrace();
        }
    }

}
