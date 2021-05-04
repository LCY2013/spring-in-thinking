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





