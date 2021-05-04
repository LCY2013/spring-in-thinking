package org.lcydream.aop.feature.listener;

import org.aopalliance.aop.Advice;
import org.lcydream.aop.feature.aspectj.proxyfactory.interceptor.EchoServiceMethodInterceptor;
import org.lcydream.aop.staticproxy.DefaultEchoService;
import org.lcydream.aop.staticproxy.EchoService;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AdvisedSupportListener;
import org.springframework.aop.framework.ProxyFactory;

/**
 * @author luocy
 * @description {@link AdvisedSupportListener} 示例
 * @create 2021-05-04
 * @since 1.0
 */
public class AdvisedSupportListenerInfo {

    public static void main(String[] args) {
        // 构建需要被代理的对象
        final DefaultEchoService echoService = new DefaultEchoService();
        // 构建代理工厂
        final ProxyFactory proxyFactory = new ProxyFactory(echoService);
        //proxyFactory.setTargetClass(DefaultEchoService.class);
        //proxyFactory.setTargetClass(EchoService.class);
        // 添加代理的拦截器，也就是advice
        proxyFactory.addAdvice(new EchoServiceMethodInterceptor());

        // 添加Listener
        proxyFactory.addListener(new AdvisedSupportListener() {
            @Override
            public void activated(AdvisedSupport advised) {
                System.out.printf("AOP 配置 %s 已激活\n",advised);
            }

            @Override
            public void adviceChanged(AdvisedSupport advised) {
                System.out.printf("AOP 配置 %s 已更新\n",advised);
            }
        });

        // 获取代理对象
        // 激活事件 getProxy() -> createAopProxy()
        final EchoService echo = (EchoService)proxyFactory.getProxy();
        System.out.println((echo.cil("fufeng")));

        // 更新事件
        proxyFactory.addAdvice(new Advice() {});
    }

}
