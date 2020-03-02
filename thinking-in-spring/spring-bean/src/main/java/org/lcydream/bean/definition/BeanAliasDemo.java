package org.lcydream.bean.definition;

import org.lcydream.domain.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Bean 别名定义示例
 */
public class BeanAliasDemo {

    public static void main(String[] args) {
        //配置spring上下文，读取xml配置文件,启动容器
        ClassPathXmlApplicationContext classPathXmlApplicationContext =
                new ClassPathXmlApplicationContext("classpath:/META-INF/bean-definition-context.xml");
        //通过别名user-fufeng 获取名称为user 的bean实例
        User user = classPathXmlApplicationContext.getBean("user", User.class);
        User userAlias = classPathXmlApplicationContext.getBean("user-fufeng", User.class);
        //比较两个bean实例是否一致
        System.out.println("名称为user的实例与别名为user-fufeng是否为同一个实例:"+(user==userAlias));
    }

}
