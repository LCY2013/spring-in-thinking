package org.lcydream.beans.scope;

import org.lcydream.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.Collection;
import java.util.Map;

/**
 * Bean Scope 示例
 */
public class BeansScope implements DisposableBean {


    @Bean //默认是单例,Bean的名称就是方法名称
    public User singletonUser(){
        return createdUser();
    }

    @Bean //设置为多例模式
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public User protoTypeUser(){
        return createdUser();
    }

    private User createdUser() {
        User user = new User();
        user.setId(System.nanoTime());
        return user;
    }

    @Autowired
    @Qualifier("singletonUser")
    private User singletonUser;

    @Autowired
    @Qualifier("singletonUser")
    private User singletonUser1;

    @Autowired
    @Qualifier("protoTypeUser")
    private User protoTypeUser;

    @Autowired
    @Qualifier("protoTypeUser")
    private User protoTypeUser1;

    @Autowired
    @Qualifier("protoTypeUser")
    private User protoTypeUser2;

    @Autowired
    private Collection<User> users;

    @Autowired
    private Map<String,User> userMap;

    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    public static void main(String[] args) {
        //创建应用上下文
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        //注册配置Bean
        applicationContext.register(BeansScope.class);

        //注册一个BeanPostProcessor
        applicationContext.addBeanFactoryPostProcessor(beansFactory -> {
            beansFactory.addBeanPostProcessor(new BeanPostProcessor(){
                //Bean初始化后调用
                @Override
                public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                    if(bean instanceof User) {
                        System.out.printf("%s 初始化之后调用,%s\n", bean.getClass().getName(), beanName);
                    }
                    return bean;
                }
            });
        });

        //启动Spring容器
        applicationContext.refresh();
        /*
            结论一:
                singleton作用域的对象注入和查找都是同一个对象
                protoType作用域的对象注入和查找都是不同的对象
            结论二:
                注册集合对象时,上下文中定义了多少的@Bean就会注入多少个实例,
                单例注入还是同一个,多例的则是重新生成新对象
            结论三:
                 singleton作用域的对象是会参与Bean的完整生命流程,
                    包括@PostConstruct(初始化)、@PreDestroy(销毁)
                 protoType作用域的对象只会参与生命流程中的@PostConstruct(初始化)
                    可以使用BeanPostProcessor做清理手段

         */
        scopeBeansByLookup(applicationContext);
        scopeBeansByInject(applicationContext);

        //关闭应用上下文
        applicationContext.close();
    }

    //通过依赖查找查看两种作用域不同的区别
    private static void scopeBeansByLookup(AnnotationConfigApplicationContext applicationContext){
        //打印两次创建的两种Bean,单例Bean,多例Bean
        for (int i=0;i<2;i++){
            //期待两次都是同一个Bean实例
            System.out.println("singletonUser : "+applicationContext.getBean("singletonUser"));
            //期待每次都不同的Bean实例
            System.out.println("protoTypeUser : "+applicationContext.getBean("protoTypeUser"));
        }
        System.out.println("-----------------------");
    }

    //通过依赖注入查看两种作用域不同的区别
    private static void scopeBeansByInject(AnnotationConfigApplicationContext applicationContext){
        //获取配置Bean对象
        BeansScope beansScope = applicationContext.getBean(BeansScope.class);
        System.out.println("singletonUser : "+beansScope.singletonUser);
        System.out.println("singletonUser1 : "+beansScope.singletonUser1);
        System.out.println("protoTypeUser : "+beansScope.protoTypeUser);
        System.out.println("protoTypeUser1 : "+beansScope.protoTypeUser1);
        System.out.println("protoTypeUser2 : "+beansScope.protoTypeUser2);
        System.out.println("users : "+beansScope.users);
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("----------开始销毁-----------");
        this.protoTypeUser.destroy();
        this.protoTypeUser1.destroy();
        this.protoTypeUser2.destroy();
        for (Map.Entry<String,User> entry : userMap.entrySet()){
            if(beanFactory.isPrototype(entry.getKey())){
                entry.getValue().destroy();
            }
        }
        System.out.println("----------结束销毁-----------");
    }
}
