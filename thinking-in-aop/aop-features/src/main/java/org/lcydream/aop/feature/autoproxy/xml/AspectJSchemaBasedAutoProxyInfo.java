package org.lcydream.aop.feature.autoproxy.xml;

import org.lcydream.aop.staticproxy.EchoService;
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
 * @description 基于xml的自动代理 示例
 * @create 2021-02-28
 */
public class AspectJSchemaBasedAutoProxyInfo {

    public static void main(String[] args) {
        final ClassPathXmlApplicationContext configApplicationContext =
                new ClassPathXmlApplicationContext("classpath:/META-INF/spring-aop-auto-proxy-context.xml");
        configApplicationContext.refresh();

        final EchoService echoService = configApplicationContext.getBean("echoService",EchoService.class);
        System.out.println((echoService.cil("fufeng")));

        configApplicationContext.close();
    }

}

