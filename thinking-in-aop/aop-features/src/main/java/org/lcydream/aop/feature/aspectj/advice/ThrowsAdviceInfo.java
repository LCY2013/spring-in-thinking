package org.lcydream.aop.feature.aspectj.advice;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.aop.aspectj.AbstractAspectJAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.adapter.ThrowsAdviceInterceptor;

import java.util.Random;

/**
 * @author luocy
 * @description ThrowsAdvice 示例
 * @create 2021-05-03
* @see ThrowsAdvice
 * @see ThrowsAdviceInterceptor
 * @see AbstractAspectJAdvice
 * @since 1.0
 */
public class ThrowsAdviceInfo {

    public static void main(String[] args) {
        final ThrowsAdviceInfo throwsAdviceInfo = new ThrowsAdviceInfo();
        final ProxyFactory proxyFactory = new ProxyFactory(throwsAdviceInfo);
        proxyFactory.addAdvice(new ThrowsAdviceHandler());

        // 获取代理后的对象
        final ThrowsAdviceInfo throwsAdviceInfoProxy = (ThrowsAdviceInfo) proxyFactory.getProxy();
        throwsAdviceInfoProxy.execute();
        throwsAdviceInfoProxy.execute();
        throwsAdviceInfoProxy.execute();
    }

    public void execute() {
        final Random random = new Random();
        if (random.nextBoolean()) {
            throw new RuntimeException("ThrowsAdviceInfo For Purpose. ");
        }
        System.out.println("Executing...");
    }

}
