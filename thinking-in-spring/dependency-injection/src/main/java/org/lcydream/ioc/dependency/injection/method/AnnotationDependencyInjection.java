package org.lcydream.ioc.dependency.injection.method;

import org.lcydream.domain.User;
import org.lcydream.ioc.dependency.injection.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * 基于注解的Method注入示例
 */
public class AnnotationDependencyInjection {

    private UserHolder userHolder;

    private UserHolder userHolderOne;

    @Autowired
    public void initOne(UserHolder userHolder){
        this.userHolder = userHolder;
    }

    @Resource
    public void initTwo(UserHolder userHolderOne){
        this.userHolderOne = userHolderOne;
    }

    @Bean
    public UserHolder userHolder(User user) {
        return new UserHolder(user);
    }

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

}

