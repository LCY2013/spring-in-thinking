package org.lcydream.container;

import org.lcydream.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Map;

/**
 * {@link BeanFactory}IoC容器示例
 */
public class AnnotationApplicationContextIoCContainer {

    public static void main(String[] args) {
        //创建IOC容器,ApplicationContext
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext();
        //将当前类AnnotationApplicationContextIoCContainer作为配置类(configuration class)
        annotationConfigApplicationContext.register(AnnotationApplicationContextIoCContainer.class);
        //注册完成后刷新应用上下文
        annotationConfigApplicationContext.refresh();
        lookUpCollectionType(annotationConfigApplicationContext);
        //关闭应用上下文
        annotationConfigApplicationContext.close();
    }

    @Bean
    public User user(){
        User user = new User();
        user.setId(2L);
        user.setName("fufeng");
        return user;
    }

    /**
     * 按集合类型查找
     *  {@link ListableBeanFactory}
     * @param beanFactory 工厂
     */
    private static void lookUpCollectionType(BeanFactory beanFactory) {
        //org.springframework.beans.factory.ListableBeanFactory
        if(beanFactory instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> beansOfType = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("通过类型查询一个集合数据:"+beansOfType);
        }
    }
}
