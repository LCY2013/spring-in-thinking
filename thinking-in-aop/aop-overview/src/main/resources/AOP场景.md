### 场景
日志场景  org.slf4j.MDC  

统计场景

安防场景

性能场景

### JAVA AOP判断模式(Predicate)
判断来源
```text
1、类型(Class)

2、方法(Method)

3、注解(Annotation)

4、参数(Parameter)

5、异常(Exception)
```

### Java AOP 拦截器模式(Interceptor)
拦截类型
```text
1、前置拦截(Before)

2、后置拦截(After)

3、异常拦截(Exception)
```

### Spring AOP功能描述
核心特征
```text
1、纯Java实现、无编译时特殊处理、不修改控制ClassLoader

2、仅支持方法级别的 Join Points

3、非完整 AOP 实现框架

4、Spring IOC 容器整合

5、AspectJ 注解驱动整合 (非竞争关系)
```

### Spring AOP编程模型
注解驱动
```text
1、实现：Enable模块驱动、@EnableAspectJAutoProxy
2、注解：
    1、激活AspectJ自动代理：@EnableAspectJAutoProxy
    2、Aspect：@Aspect
    3、Pointcut：@PointCut
    4、Advice：@Before 、 @AfterReturning 、 @AfterThrowing 、 @After 、 @Around
    5、Introduction：@DeclareParents    
```

XML配置驱动
```text
1、实现：Spring Extensble XML Authoring
2、XML元素
    1、激活AspectJ自动代理：<aop:aspectj-autoproxy/>
    2、配置：<aop:config/>
    3、Aspect：<aop:aspect/>
    4、Pointcut：<aop:pointcut/>
    5、Advice：<aop:before/>、<aop:around/>、<aop:after/>、<aop:after-returning/> 和 <aop:after-throwing/>
    6、Introduction：<aop:declare-parents/>
    7、代理Scope：<aop:scope-proxy/>
```

底层API
```text
1、实现：JDK动态代理、CGLIB以及AspectJ
2、API：
    1、代理：AutoProxy
    2、配置：ProxyConfig
    3、Join Point：JoinPoint
    4、Pointcut：PointCut
    5、Advice：Advice、BeforeAdvice、AfterAdvice、AfterReturningAdvice、ThrowsAdivice
```

### Spring AOP advice 类型
Advice 类型
```text
1、环绕（Around）

2、前置（Before）

3、后置（After）
    方法执行
    finally执行

4、异常（Exception）    
```

### Spring AOP 代理实现
```text
基础接口定义：org.springframework.aop.framework.AopProxy

1、JDK动态代理实现 - 基于接口代理
    org.springframework.aop.framework.JdkDynamicAopProxy

2、CGLIB 动态代理实现 - 基于类代理（字节码提升）
    org.springframework.aop.framework.CglibAopProxy

3、AspectJ 适配实现
    org.springframework.aop.aspectj.annotation.AspectJProxyFactory
```

### JDK 动态代理

为什么Proxy.newProxyInstance会生成新的字节码？
```text
具体字节码生成：java.lang.reflect.Proxy

instance 是生成的字节码的实例化 生成字节码如下：
class Proxy$num extend java.lang.reflect.Proxy implement Interface{
     public Proxy$num(InvocationHandler handler){
        super(handler);
     }
}
```










