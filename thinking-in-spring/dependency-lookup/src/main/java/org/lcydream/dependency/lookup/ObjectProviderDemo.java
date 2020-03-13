package org.lcydream.dependency.lookup;

import org.lcydream.domain.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * 通过{@link org.springframework.beans.factory.ObjectProvider} 依赖查找
 */
//@Configuration
public class ObjectProviderDemo { //@Configuration是非必须注解

    public static void main(String[] args) {
        //创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        //注入容器的配置类信息(Configuration)
        applicationContext.register(ObjectProviderDemo.class);
        //启动容器
        applicationContext.refresh();

        lookupByObjectProvider(applicationContext);

        //如果不存在就创建一个
        lookupIfAvailable(applicationContext);

        lookupByStreamOps(applicationContext);

        //停止容器
        applicationContext.close();
    }

    private static void lookupByStreamOps(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<String> beanProvider = applicationContext.getBeanProvider(String.class);
//        Iterable<String> iterable = beanProvider;
//        for (String str : iterable){
//            System.out.println(str);
//        }
        beanProvider.stream().forEach(System.out::println);
    }

    private static void lookupIfAvailable(AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<User> beanProvider
                = applicationContext.getBeanProvider(User.class);
        //不存在就创建一个
        User user = beanProvider.getIfAvailable(User::createUser);
        System.out.println("当前 User 对象:"+user);
    }

    @Bean
    @Primary
    public String helloWorld(){ //方法名就是 helloWorld 的Bean
        return "hello,world";
    }

    @Bean
    public String message(){
        return "message";
    }

    private static void lookupByObjectProvider(
            AnnotationConfigApplicationContext applicationContext) {
        ObjectProvider<String> beanProvider
                = applicationContext.getBeanProvider(String.class);
        System.out.println(beanProvider.getObject());
    }

}
