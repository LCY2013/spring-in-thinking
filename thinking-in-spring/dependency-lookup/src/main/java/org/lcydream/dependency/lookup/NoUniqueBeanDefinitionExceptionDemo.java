package org.lcydream.dependency.lookup;

import org.lcydream.domain.User;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * {@link org.springframework.beans.factory.NoUniqueBeanDefinitionException} 异常演示
 */
public class NoUniqueBeanDefinitionExceptionDemo {

    public static void main(String[] args) {
        //创建应用上下文
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext();
        //注册配置Bean信息
        annotationConfigApplicationContext.register(NoUniqueBeanDefinitionExceptionDemo.class);
        //启动应用上下文
        annotationConfigApplicationContext.refresh();

        try {
            User bean = annotationConfigApplicationContext.getBean(User.class);
            System.out.println(bean);
        }catch (NoUniqueBeanDefinitionException e){
            //e.printStackTrace();
            System.out.printf("Spring 应用上下文中存在%d个%s类型的对象,具体异常原因是:%s\n",
                    e.getNumberOfBeansFound(),
                    e.getBeanType(),
                    e.getMessage());
        }

        //关闭应用上下文
        annotationConfigApplicationContext.close();
    }

    @Bean
    public User userOne(){
        return new User();
    }

    @Bean
    public User userTwo(){
        return new User();
    }

    @Bean
    public User userThree(){
        return new User();
    }

}
