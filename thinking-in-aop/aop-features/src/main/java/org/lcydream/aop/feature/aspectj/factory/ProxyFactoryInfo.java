package org.lcydream.aop.feature.aspectj.factory;

import org.lcydream.aop.feature.aspectj.proxyfactory.interceptor.EchoServiceMethodInterceptor;
import org.lcydream.aop.staticproxy.DefaultEchoService;
import org.lcydream.aop.staticproxy.EchoService;
import org.springframework.aop.framework.ProxyFactory;

/**
 * @author luocy
 * @description ProxyFactory 信息
 * @program customer-service
 * @create 2021-03-28
 * @since 1.0
 */
public class ProxyFactoryInfo {

    public static void main(String[] args) {
        // 构建需要被代理的对象
        final DefaultEchoService echoService = new DefaultEchoService();
        // 构建代理工厂
        final ProxyFactory proxyFactory = new ProxyFactory(echoService);
        //proxyFactory.setTargetClass(DefaultEchoService.class);
        //proxyFactory.setTargetClass(EchoService.class);
        // 添加代理的拦截器，也就是advice
        proxyFactory.addAdvice(new EchoServiceMethodInterceptor());
        // 获取代理对象
        final EchoService echo = (EchoService)proxyFactory.getProxy();
        System.out.println((echo.cil("fufeng")));
    }

}
