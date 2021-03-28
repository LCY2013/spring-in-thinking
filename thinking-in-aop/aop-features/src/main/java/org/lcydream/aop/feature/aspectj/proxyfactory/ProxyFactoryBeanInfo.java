package org.lcydream.aop.feature.aspectj.proxyfactory;

import org.aspectj.lang.annotation.Aspect;
import org.lcydream.aop.staticproxy.EchoService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author luocy
 * @description
 * @program customer-service
 * @create 2021-03-28
 * @since 1.0
 */
//声明为切面
@Aspect
// 声明为配置类
@Configuration
public class ProxyFactoryBeanInfo {

    public static void main(String[] args) {
        // 定义xml上下文
        final ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:/META-INF/spring-aop-context.xml");

        // 获取对应的echoServiceFactory
        final EchoService echoService = context.getBean("echoServiceProxyFactoryBean", EchoService.class);
        System.out.println((echoService.cil("fufeng")));

        // 关闭上下文
        context.close();
    }

}
