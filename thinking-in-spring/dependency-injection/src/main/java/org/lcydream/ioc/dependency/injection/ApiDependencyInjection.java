package org.lcydream.ioc.dependency.injection;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 基于XMl资源的Setter注入示例
 */
public class ApiDependencyInjection {

    public static void main(String[] args) {
        //创建上下文环境
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();

        //创建BeanDefinition
        final BeanDefinition beanDefinition = createBeanDefinition();
        //注册BeanDefinition
        applicationContext.registerBeanDefinition("userHolder",beanDefinition);

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
        System.out.println(userHolder);
        //关闭容器
        applicationContext.close();
    }

    /**
     * 为{@link UserHolder} 生成 {@link BeanDefinition}
     * @return BeanDefinition
     */
    public static BeanDefinition createBeanDefinition(){
        BeanDefinitionBuilder beanDefinitionBuilder =
                BeanDefinitionBuilder.genericBeanDefinition(UserHolder.class);
        beanDefinitionBuilder.addPropertyReference("user","superUser");
        return beanDefinitionBuilder.getBeanDefinition();
    }

//    @Bean
//    public UserHolder userHolder(User user){
//        //return new UserHolder(user);
//        UserHolder userHolder = new UserHolder();
//        userHolder.setUser(user);
//        return userHolder;
//    }

}

