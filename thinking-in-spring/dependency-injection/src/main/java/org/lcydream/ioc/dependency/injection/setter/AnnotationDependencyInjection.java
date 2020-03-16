package org.lcydream.ioc.dependency.injection.setter;

import org.lcydream.domain.User;
import org.lcydream.ioc.dependency.injection.UserHolder;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 基于XMl资源的Setter注入示例
 */
public class AnnotationDependencyInjection {

    public static void main(String[] args) {
        //创建上下文环境
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        //注册配置类
        applicationContext.register(AnnotationDependencyInjection.class);
        //创建XML资源BeanDefinition解析器
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        //定义资源路径
        String xmlPath = "classpath:/META-INF/dependency-lookup-context.xml";
        //加载BeanDefinition的资源
        xmlBeanDefinitionReader.loadBeanDefinitions(xmlPath);
        //启动容器上下文
        applicationContext.refresh();
        //获取Bean信息
        UserHolder userHolder = applicationContext.getBean(UserHolder.class);
        System.out.println(userHolder.getUser());
        //关闭容器
        applicationContext.close();
    }

    @Bean
    public UserHolder userHolder(User user){
        UserHolder userHolder = new UserHolder();
        userHolder.setUser(user);
        return userHolder;
    }

}

