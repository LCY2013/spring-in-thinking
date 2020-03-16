package org.lcydream.ioc.dependency.injection.field;

import org.lcydream.domain.User;
import org.lcydream.ioc.dependency.injection.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * 基于注解的Field注入示例
 */
public class AnnotationDependencyInjection {

    @Autowired
    private
    //static @Autowired 会忽略static字段
            UserHolder userHolder;

    @Resource //直接不支持static字段
    private UserHolder userHolderOne;

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
        AnnotationDependencyInjection dependencyInjection = applicationContext.getBean(AnnotationDependencyInjection.class);
        System.out.println(dependencyInjection.userHolder);
        System.out.println(dependencyInjection.userHolderOne);
        System.out.println(dependencyInjection.userHolder == dependencyInjection.userHolderOne);
        //关闭容器
        applicationContext.close();
    }

    @Bean
    public UserHolder userHolder(User user) {
        return new UserHolder(user);
    }

}

