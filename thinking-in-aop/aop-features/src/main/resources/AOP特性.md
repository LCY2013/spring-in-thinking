### @AspectJ 注解驱动
```text
1、激活@AspectJ模块
    1、注解激活 - @EnableAspectJAutoProxy
    2、XML配置 - <aop:aspectj-autoproxy/>
2、声明Aspectj
    @Aspectj    
```

### 编程方式创建@AspectJ代理
实现类
```text
org.springframework.aop.aspectj.annotation.AspectJProxyFactory
```

### AdvisorAdapter、Advice、Advisor、MethodInterceptor

扩展Advice逻辑：

> 自定义Advice

> 自定义Advisor

> 自定义继承自Advice 的 MethodInterceptor

> 自定义实现AdvisorAdapter

> 通过DefaultAdvisorAdapterRegistry 注册AdvisorAdapter

如何通过 DefaultAdvisorAdapterRegistry 注册 AdvisorAdapter  ?

> AdvisorAdapterRegistrationManager


### AopProxy - JdkDynamicAopProxy、CglibAopProxy

### AopProxyFactory -> DefaultAopProxyFactory
> org.springframework.aop.framework.JdkDynamicAopProxy

> org.springframework.aop.framework.CglibAopProxy(CglibMethodInvocation) -> org.springframework.aop.framework.ObjenesisCglibAopProxy

### 命名策略

> NamingPolicy -> DefaultNamingPolicy -> SpringNamingPolicy

### AopProxyFactory配置管理器 - AdvisedSupport ProxyConfig -> AdvisedSupport -> AdvisorChainFactory(DefaultAdvisorChainFactory)

### Advisor链工厂接口与实现 - AdvisorChainFactory 

- org.springframework.aop.framework.DefaultAdvisorChainFactory

- org.springframework.aop.framework.adapter.GlobalAdvisorAdapterRegistry

- org.springframework.aop.framework.adapter.AdvisorAdapterRegistry ->org.springframework.aop.framework.adapter.DefaultAdvisorAdapterRegistry

### 目标对象来源接口与实现 - TargetSource

- org.springframework.aop.target.HotSwappableTargetSource

- org.springframework.aop.target.SingletonTargetSource

- org.springframework.aop.target.PrototypeTargetSource

- org.springframework.aop.target.CommonsPool2TargetSource

### 代理对象创建基础类 - ProxyCreatorSupport


### AdvisedSupport事件监听器 - AdvisedSupportListener

### ProxyCreatorSupport标准实现 - ProxyFactory

### ProxyCreatorSupport IoC容器实现 - ProxyFactoryBean

### ProxyCreatorSupport AspectJ实现 - AspectJProxyFactory

### IoC容器自动代理抽象 - AbstractAutoProxyCreator

### IoC容器自动代理标准实现
- org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator

- (默认实现)org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator

- (Bean名称匹配)org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator

- (Infrastructure)org.springframework.aop.framework.autoproxy.InfrastructureAdvisorAutoProxyCreator

### IoC容器自动代理 AspectJ 实现 - AspectJAwareAdvisorAutoProxyCreator

### AOP Infrastructure Bean接口 - AopInfrastructureBean 底层基础设施标记接口，用于筛选AOP相关基础设施，不用做代理

- org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator#isInfrastructureClass 断言是否是基础设施类

### AOP上下文辅助类 - AopContext

### 代理工厂工具类 - AopProxyUtils

### AOP工具类 - AopUtils 

- AOP 在对目标对象代理的时候会进行接口 SpringProxy 的添加，用于处理这里的判断isXXXProxy()

### AspectJ Enable模块驱动实现 - @EnableAspectJAutoProxy






