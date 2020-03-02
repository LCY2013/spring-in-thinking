package org.lcydream.bean.definition;

import org.lcydream.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Import(AnnotationBeanDefinitionDemo.BeanConfiguration.class)
public class AnnotationBeanDefinitionDemo {

    public static void main(String[] args) {
        //定义注解配置应用上下文
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext();
        //注册一个配置类configuration
        annotationConfigApplicationContext.register(AnnotationBeanDefinitionDemo.class);
        //1、通过@Bean定义
        //2、通过@Component定义
        //3、通过Import导入

        //刷新启动应用上下文
        annotationConfigApplicationContext.refresh();

        //查看同时标注Component和Import是否会重复注册同一种类型的Bean
        System.out.println("User 类型对象:"
                +annotationConfigApplicationContext.getBeansOfType(User.class));
        System.out.println("BeanConfiguration 类型对象:"
                +annotationConfigApplicationContext.getBeansOfType(BeanConfiguration.class));

        //关闭应用上下文
        annotationConfigApplicationContext.close();
    }

    @Component
    public class BeanConfiguration{

        @Bean(name = {"user","fufeng"})
        public User user(){
            User user = new User();
            user.setId(24L);
            user.setName("fufeng");
            return user;
        }
    }
}

