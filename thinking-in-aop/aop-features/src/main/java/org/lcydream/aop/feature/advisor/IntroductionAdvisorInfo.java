package org.lcydream.aop.feature.advisor;

import org.lcydream.aop.staticproxy.EchoService;
import org.springframework.aop.IntroductionAdvisor;
import org.springframework.aop.IntroductionInfo;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultIntroductionAdvisor;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author luocy
 * @description {@link IntroductionAdvisor} 示例
 * @create 2021-05-04
 * @since 1.0
 */
public class IntroductionAdvisorInfo implements EchoService, Comparable<IntroductionAdvisorInfo> {

    public static void main(String[] args) {
        // EchoService and Comparable
        final IntroductionAdvisorInfo introductionAdvisorInfo =
                new IntroductionAdvisorInfo();
        // create ProxyFactory
        // 使用构造器，后面的接口筛选就会失效，因为这里 ClassUtils.getAllInterfaces(target) 设置了所有的接口
        final ProxyFactory proxyFactory = new ProxyFactory(introductionAdvisorInfo);
        /*final ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(introductionAdvisorInfo);*/

        // add IntroductionAdvisor
        proxyFactory.addAdvisor(new DefaultIntroductionAdvisor(
                (MethodBeforeAdvice) (method, args1, target) -> System.out.printf("BeforeAdvice : %s \n", method),
                //() -> new Class[]{EchoService.class,Comparable.class}));
                () -> new Class[]{EchoService.class, Map.class}));
                //() -> new Class[]{EchoService.class}));

        // get proxy Object
        final EchoService echoService = (EchoService) proxyFactory.getProxy();
        System.out.println((echoService.cil("fufeng")));

        final Map echoServiceMap = (Map) proxyFactory.getProxy();
        echoServiceMap.put("A","1");

        //final Comparable echoServiceComparable = (Comparable) proxyFactory.getProxy();
        //echoServiceComparable.compareTo(null);
    }

    @Override
    public int compareTo(IntroductionAdvisorInfo o) {
        return 0;
    }

    @Override
    public void echo(String message) throws NullPointerException {

    }

    @Override
    public void print(String message) {

    }

    @Override
    public String cil(String message) {
        return String.format("IntroductionAdvisorInfo - %s", message);
    }
}
