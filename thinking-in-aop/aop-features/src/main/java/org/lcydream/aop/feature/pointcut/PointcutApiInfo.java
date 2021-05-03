package org.lcydream.aop.feature.pointcut;

import org.lcydream.aop.feature.aspect.api.EchoServicePointcut;
import org.lcydream.aop.feature.aspectj.proxyfactory.interceptor.EchoServiceMethodInterceptor;
import org.lcydream.aop.staticproxy.DefaultEchoService;
import org.lcydream.aop.staticproxy.EchoService;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

/**
 * @author luocy
 * @description api 的方式实现Pointcut
 * @program customer-service
 * @create 2021-03-28
 * @since 1.0
 */
public class PointcutApiInfo {

    public static void main(String[] args) {
        // 构建自定义的Pointcut
        final EchoServiceMethodPointcut echoServicePointcut =
                EchoServiceMethodPointcut.INSTANCE;

        // 将Pointcut与Advisor绑定
        final DefaultPointcutAdvisor advisor =
                new DefaultPointcutAdvisor(echoServicePointcut,new EchoServiceMethodInterceptor());

        // 创建需要被代理的对象
        final DefaultEchoService echoService = new DefaultEchoService();

        // 构建代理工厂
        final ProxyFactory proxyFactory = new ProxyFactory(echoService);
        // 代理工厂添加
        proxyFactory.addAdvisor(advisor);

        // 获取被代理的对象
        final EchoService echo = (EchoService)proxyFactory.getProxy();
        System.out.println((echo.cil("fufeng")));
    }

}
