package org.lcydream.bean.definition;

import org.lcydream.domain.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 通过Spring API的方式注册BeanDefinition
 */
@Import(ApiBeanDefinitionDemo.UserConfiguration.class) //导入配置类信息
//@Component
public class ApiBeanDefinitionDemo {

    public static void main(String[] args) {
        //spring应用上下文
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        //注册当前Bean
        applicationContext.register(ApiBeanDefinitionDemo.class);

        //命名方式注册
        registerNamingBeanDefinition(applicationContext,"naming-context",User.class);
        //非命名方式注册
        registerUnNamingBeanDefinition(applicationContext,User.class);

        //刷新应用上下文
        applicationContext.refresh();

        //获取容器中注册的User实例
        System.out.println("所有user实例对象:"+applicationContext.getBeansOfType(User.class));

        //显示关闭应用上下文
        applicationContext.close();
    }

    /**
     * 通过命名的BeanDefinition注册Bean信息
     * @param beanDefinitionRegistry bean的定义实例注册器
     * @param beanName 自定义bean名称
     * @param clazz 需要注册的bean
     */
    public static void registerNamingBeanDefinition(BeanDefinitionRegistry beanDefinitionRegistry,
                                               String beanName, Class<?> clazz){
        //创建BeanDefinition构造器
        BeanDefinitionBuilder beanDefinitionBuilder =
                BeanDefinitionBuilder.genericBeanDefinition(clazz);
        //通过属性构造User Bean实例
        beanDefinitionBuilder
                .addPropertyValue("id",2L)
                .addPropertyValue("name","fufeng");
        //命名注册方式
        if(StringUtils.hasText(beanName)) {
            beanDefinitionRegistry.registerBeanDefinition(beanName,
                    beanDefinitionBuilder.getBeanDefinition());
        }else {
            //非命名注册方式
            beanDefinitionRegistry.registerBeanDefinition(
                    BeanDefinitionReaderUtils.generateBeanName(beanDefinitionBuilder.getBeanDefinition(), beanDefinitionRegistry),
                    beanDefinitionBuilder.getBeanDefinition());
        }
    }

    /**
     * 通过非命名的方式注册BeanDefinition
     * @param beanDefinitionRegistry BeanDefinition注册器
     * @param clazz 需要注册的类
     */
    public static void registerUnNamingBeanDefinition(BeanDefinitionRegistry beanDefinitionRegistry,
                                                       Class<?> clazz){
        registerNamingBeanDefinition(beanDefinitionRegistry,null,clazz);
    }


    @Component
    public static class UserConfiguration{
        @Bean(name = {"user","user-fufeng"})
        public User user(){
            User user = new User();
            user.setName("fufeng");
            user.setId(1L);
            return user;
        }
    }
}
