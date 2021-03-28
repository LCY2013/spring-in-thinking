package org.lcydream.aop.feature.aspect.xml;

import org.lcydream.aop.feature.aspect.AspectConfiguration;
import org.lcydream.aop.staticproxy.EchoService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author luocy
 * @description 切点 {@link Pointcut}
 * @program customer-service
 * @create 2021-03-28
 * @since 1.0
 */
/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program spring-cloud-alibaba-projects
 * @description AspectJ 示例
 * @create 2021-02-28
 */
public class AspectSchemaBasedPointcutInfo {

    public static void main(String[] args) {
        final ClassPathXmlApplicationContext configApplicationContext =
                new ClassPathXmlApplicationContext("classpath:/META-INF/spring-aop-context.xml");
        configApplicationContext.refresh();

        final EchoService echoService = configApplicationContext.getBean("echoService",EchoService.class);
        System.out.println((echoService.cil("fufeng")));

        configApplicationContext.close();
    }

}

