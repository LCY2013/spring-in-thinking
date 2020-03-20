package org.lcydream.beans.scope.custom;

import org.lcydream.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * 自定义 Scope {@link ThreadLocalScope} 示例
 */
public class ThreadLocalScopeTester {

    @Bean
    @Scope(ThreadLocalScope.THREAD_LOCAL)
    private User createUser(){
        User user = new User();
        user.setId(System.nanoTime());
        return user;
    }

    public static void main(String[] args) {
        //定义容器上下文
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        //注册配置启动类
        applicationContext.register(ThreadLocalScopeTester.class);
        //注册BeanPostProcessor
        applicationContext.addBeanFactoryPostProcessor(beanFactory -> {
            //注册自定义的Scope
            beanFactory.registerScope(ThreadLocalScope.THREAD_LOCAL,new ThreadLocalScope());
        });
        //启动容器
        applicationContext.refresh();

        //依赖查找
        threadLocalLookup(applicationContext);

        //停止容器
        applicationContext.stop();
    }

    private static void threadLocalLookup(AnnotationConfigApplicationContext applicationContext) {
        for (int i=0;i<5;i++){
            Thread thread = new Thread(()->{
                //同一线程,线程初始化后,就是共享同一个Bean对象
                User user = applicationContext.getBean(User.class);
                System.out.println(user);
            });
            //启动线程
            thread.start();
            try {
                //阻塞线程等待线程执行完成,相当于线程顺序执行
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
