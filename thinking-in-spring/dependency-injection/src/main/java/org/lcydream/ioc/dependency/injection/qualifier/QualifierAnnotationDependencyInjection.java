package org.lcydream.ioc.dependency.injection.qualifier;

import org.lcydream.domain.User;
import org.lcydream.ioc.dependency.injection.qualifier.annotation.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Collection;

/**
 * 通过{@link org.springframework.beans.factory.annotation.Qualifier} 去区分同一类型的Bean
 *  结论: Qualifier注解具有区分某一种类型Bean的能力，可以作为其类型的逻辑分组，可以进行限定注入
 *
 * @see org.springframework.beans.factory.annotation.Qualifier
 */
public class QualifierAnnotationDependencyInjection {

    @Autowired
    private User user;

    @Autowired
    @Qualifier("user")
    private User namedUser;

    //上下文中存在4个User Bean
    //Super User
    //user
    //user1
    //user2

    @Autowired
    private Collection<User> allUsers;  //2个Bean -> Super User , user

    @Autowired
    @Qualifier
    private Collection<User> qualifierUsers; //2个Bean -> user1 , user2 -> 4个Bean user1,user2,user3,user4

    @Autowired
    @UserGroup
    private Collection<User> userGroupUsers; //2个Bean -> user3 , user4

    @Bean
    @Qualifier
    private User user1(){
        return createUser(7L);
    }

    @Bean
    @Qualifier
    private User user2(){
        return createUser(8L);
    }

    @Bean
    @UserGroup
    private User user3(){
        return createUser(9L);
    }

    @Bean
    @UserGroup
    private User user4(){
        return createUser(10L);
    }

    public static void main(String[] args) {
        //  创建Spring应用上下文
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        //注册配置Bean信息
        applicationContext.register(QualifierAnnotationDependencyInjection.class);
        //获取XML资源读取器
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(applicationContext);
        //定义XML资源文件的路径
        String xmlPath = "classpath:/META-INF/dependency-lookup-context.xml";
        //读取XML文件到上下文中
        reader.loadBeanDefinitions(xmlPath);
        //启动Spring应用上下文
        applicationContext.refresh();

        QualifierAnnotationDependencyInjection injection =
                applicationContext.getBean(QualifierAnnotationDependencyInjection.class);

        // 期待输出SuperUser信息
        System.out.println("user :"+injection.user);
        // 期待输出User信息
        System.out.println("namedUser :"+injection.namedUser);
        //期待输出Super User , user , user1 ,user2 ,实际输出Super user,user 两个Bean
        System.out.println("allUsers :"+injection.allUsers);
        //期待输出user1 , user2
        System.out.println("qualifierUsers :"+injection.qualifierUsers);
        //期待输出user3 , user4
        System.out.println("userGroup :"+injection.userGroupUsers);

        //关闭Spring应用上下文
        applicationContext.close();
    }

    private static User createUser(Long id){
        User user = new User();
        user.setId(id);
        return user;
    }

}
