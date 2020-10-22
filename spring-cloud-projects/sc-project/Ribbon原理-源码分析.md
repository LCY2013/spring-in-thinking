### Spring Cloud Ribbon 与 DiscoveryClient 

##### Ribbon 功能点

Ribbon 组件来自 Netflix，它的定位是一款用于提供客户端负载均衡的工具软件。Ribbon 会自动地基于某种内置的负载均衡算法去连接服务实例，我们也可以设计并实现自定义的负载均衡算法并嵌入 Ribbon 中。同时，Ribbon 客户端组件提供了一系列完善的辅助机制用来确保服务调用过程的可靠性和容错性，包括连接超时和重试等。Ribbon 是客户端负载均衡机制的典型实现方案，所以需要嵌入在服务消费者的内部进行使用。

因为 Netflix Ribbon 本质上只是一个工具，而不是一套完整的解决方案，所以 Spring Cloud Netflix Ribbon 对 Netflix Ribbon 做了封装和集成，使其可以融入以 Spring Boot 为构建基础的技术体系中。基于 Spring Cloud Netflix Ribbon，通过注解就能简单实现在面向服务的接口调用中，自动集成负载均衡功能，使用方式主要包括以下两种：

使用 @LoadBalanced 注解 

@LoadBalanced 注解用于修饰发起 HTTP 请求的 RestTemplate 工具类，并在该工具类中自动嵌入客户端负载均衡功能。开发人员不需要针对负载均衡做任何特殊的开发或配置。

使用 @RibbonClient 注解  

Ribbon 还允许你使用 @RibbonClient 注解来完全控制客户端负载均衡行为。这在需要定制化负载均衡算法等某些特定场景下非常有用，我们可以使用这个功能实现更细粒度的负载均衡配置。

##### DiscoveryClient获取服务实例

我们可以通过自定义的DiscoveryClient，去获取Eureka、consul、zookeeper、etcd...等服务注册信息

如何根据服务名称获取 Eureka 中的服务实例信息，通过 DiscoveryClient 可以很容易实现，流程如下:
```
// 获取全量服务名称
List<String> serviceNames = discoveryClient.getServices();

// 获取不同服务的对应实例
List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceName);

// 服务实例相关信息
public interface ServiceInstance {
 //服务实例的唯一性 Id
 String getServiceId();
 //主机
 String getHost();
 //端口
 int getPort();
 //URI
 URI getUri();
 //元数据
 Map<String, String> getMetadata();
	…
}
获取了一个 ServiceInstance 列表，我们就可以基于常见的随机、轮询等算法来实现客户端负载均衡，也可以基于服务的 URI 信息等实现各种定制化的路由机制。一旦确定负载均衡的最终目标服务，就可以使用 HTTP 工具类来根据服务的地址信息发起远程调用。
```

[关于Discovery抽象(EurekaDiscoveryClient)使用的项目示例位置](https://github.com/LCY2013/spring-in-thinking/tree/master/spring-cloud-projects/sc-project/user-service/src/test/java/org/fufeng/discovery/user/controller/UserControllerTest)

##### Ribbon 实现负载均衡 需要在RestTemple上面加上 @LoadBalanced注解

```java
@Configuration
public class CustomDiscoveryClient {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
```

[关于Ribbon 使用负载均衡示例代码位置](https://github.com/LCY2013/spring-in-thinking/tree/master/spring-cloud-projects/sc-project/user-service/src/test/java/org/fufeng/discovery/user/controller/RibbonLoadBalancedTest)
 

##### 通过实现@RibbonClient 实现自定义的负载均衡策略

Spring Cloud Netflix Ribbon 提供 @RibbonClient 注解的目的在于通过该注解声明自定义配置，从而来完全控制客户端负载均衡行为。

```
public @interface RibbonClient {
 //同下面的 name 属性
 String value() default "";
 //指定服务名称
 String name() default "";
 //指定负载均衡配置类
 Class<?>[] configuration() default {};
}

通常，我们需要指定这里的目标服务名称以及负载均衡配置类。
所以，为了使用 @RibbonClient 注解，我们需要创建一个独立的配置类，用来指定具体的负载均衡规则。

@Configuration
public class SpringHealthLoadBalanceConfig{

 @Autowired
 IClientConfig config;

 @Bean
 @ConditionalOnMissingBean
 public IRule springHealthRule(IClientConfig config) {
    return new RandomRule();
 }
}
该配置类的作用是使用 RandomRule 替换 Ribbon 中的默认负载均衡策略 RoundRobin，我们可以根据需要返回任何自定义的 IRule 接口的实现策略。

有了这个 SpringHealthLoadBalanceConfig 之后，我们就可以在调用特定服务时使用该配置类，从而对客户端负载均衡实现细粒度的控制。
@SpringBootApplication
@EnableDiscoveryClient  // 尽量使用这个注解，这是来自SpringCloudCommon，兼容所有的注册中心(eureka,zk,consul,etcd...)
@RibbonClient(name = "user-service",configuration = {SpringHealthLoadBalanceConfig.class})
public class InterventionApplication {
    public static void main(String[] args) {
        SpringApplication.run(InterventionApplication.class,args);
    }
}

注意到在 @RibbonClient 中设置了目标服务名称为 user-service，配置类为 SpringHealthLoadBalanceConfig。现在每次访问 user-service 时将使用 RandomRule 这一随机负载均衡策略。
对比 @LoadBalanced 注解和 @RibbonClient 注解，如果使用的是普通的负载均衡场景，那么通常只需要 @LoadBalanced 注解就能完成客户端负载均衡。而如果我们要对 Ribbon 运行时行为进行定制化处理时，就可以使用 @RibbonClient 注解。
```
[Spring Cloud netflix ribbon 如何细粒度控制负载均衡?](https://github.com/LCY2013/spring-in-thinking/tree/master/spring-cloud-projects/sc-project/intervention-service)


### Ribbon 原理和架构分析

```
Ribbon 需要解决的两件事情:
1、获取注册中心中的服务器列表
2、在所有的服务列表中选择一个服务进行调用

< Ribbon 核心类介绍 >

一、
com.netflix.loadbalancer.ILoadBalancer
public interface ILoadBalancer {
  //添加后端服务
  public void addServers(List<Server> newServers);
  //选择一个后端服务
  public Server chooseServer(Object key); 
  //标记一个服务不可用
  public void markServerDown(Server server);
  //获取当前可用的服务列表
  public List<Server> getReachableServers();
  //获取所有后端服务列表
  public List<Server> getAllServers();
}
该接口的类继承结果如下:
com.netflix.loadbalancer.ILoadBalancer
  -> com.netflix.loadbalancer.AbstractLoadBalancer
    -> com.netflix.loadbalancer.NoOpLoadBalancer
    -> com.netflix.loadbalancer.BaseLoadBalancer  重要类
      -> com.netflix.loadbalancer.DynamicServerListLoadBalancer
        -> com.netflix.loadbalancer.ZoneAwareLoadBalancer

AbstractLoadBalancer 是个抽象类，只定义了两个抽象方法，并不构成一种模板方法的结构。所以我们直接来看 ILoadBalancer 接口，该接口最基本的实现类是 BaseLoadBalancer，可以说负载均衡的核心功能都可以在这个类中得以实现。

BaseLoadBalancer 之 IRule 分析

public interface IRule{ // 负载均衡策略抽象
 // 负载均衡选择的具体实现
 public Server choose(Object key);
 public void setLoadBalancer(ILoadBalancer lb);
 public ILoadBalancer getLoadBalancer();
}

BaseLoadBalancer 之 IPing 分析

public interface IPing {
 // 判断服务是否存活
 public boolean isAlive(Server server);
}

BaseLoadBalancer 之 LoadBalancerStats 分析

LoadBalancerStats 类记录负载均衡的实时运行信息，用来作为负载均衡策略的运行时输入。

注意，在 BaseLoadBalancer 内部维护着 allServerList 和 upServerList 这两个线程的安全列表，所以对于 ILoadBalancer 接口定义的 addServers、getReachableServers、getAllServers 这几个方法而言，主要就是对这些列表的维护和管理工作。
分析 com.netflix.loadbalancer.LoadBalancerStats.addServer 方法

BaseLoadBalancer 之 com.netflix.loadbalancer.BaseLoadBalancer.chooseServer方法
public Server chooseServer(Object key) {
    if (counter == null) {
        counter = createCounter();
    }
    counter.increment();
    if (rule == null) {
        return null;
    } else {
        try {
            return rule.choose(key);
        } catch (Exception e) {
            return null;
        }
    }
}
可以看出最后是使用到了具体的某一个规则(IRule)

Netflix Ribbon 提供了哪些负载均衡的规则/策略(IRule)
    负载均衡算法可以分成两大类，即静态负载均衡算法和动态负载均衡算法。

    静态负载均衡算法比较容易理解和实现，典型的包括随机（Random）、轮询（Round Robin）和加权轮询（Weighted Round Robin）算法等。
    所有涉及权重的静态算法都可以转变为动态算法，因为权重可以在运行过程中动态更新，动态轮询算法中权重值基于对各个服务器的持续监控并不断更新。另外，基于服务器的实时性能分析分配连接是常见的动态策略。典型动态算法包括源 IP 哈希算法、最少连接数算法、服务调用时延算法等。

    IRule 继承结构图
    com.netflix.loadbalancer.IRule
      -> com.netflix.loadbalancer.AbstractLoadBalancerRule
        -> com.netflix.loadbalancer.RoundRobinRule   轮训
          -> com.netflix.loadbalancer.WeightedResponseTimeRule  权重响应时间
          -> com.netflix.loadbalancer.ResponseTimeWeightedRule
        -> com.netflix.loadbalancer.ClientConfigEnabledRoundRobinRule
          -> com.netflix.loadbalancer.BestAvailableRule  
          -> com.netflix.loadbalancer.PredicateBasedRule
            -> com.netflix.loadbalancer.ZoneAvoidanceRule
            -> com.netflix.loadbalancer.AvailabilityFilteringRule 可用性
        -> com.netflix.loadbalancer.RandomRule 随机
        -> com.netflix.loadbalancer.RetryRule 重试 - 服务容错
        -> com.alibaba.cloud.nacos.ribbon.NacosRule  阿里Nacos对ribbon的支持

BestAvailableRule 策略
    选择一个并发请求量最小的服务器，逐个考察服务器然后选择其中活跃请求数最小的服务器

WeightedResponseTimeRule 策略
    该策略与请求的响应时间有关，显然，如果响应时间越长，就代表这个服务的响应能力越有限，那么分配给该服务的权重就应该越小。而响应时间的计算就依赖于 ILoadBalancer 接口中的 LoadBalancerStats。WeightedResponseTimeRule 会定时从 LoadBalancerStats 读取平均响应时间，为每个服务更新权重。权重的计算也比较简单，即每次请求的响应时间减去每个服务自己平均的响应时间就是该服务的权重。

AvailabilityFilteringRule 策略
    通过检查 LoadBalancerStats 中记录的各个服务器的运行状态，过滤掉那些处于一直连接失败或处于高并发状态的后端服务器。

Spring Cloud Netflix Ribbon
    对于 Netflix Ribbon 组件而言，我们首先需要明确它提供的只是一个辅助工具，这个辅助工具的目的是让你去集成它，而不是说它自己完成所有的工作。而 Spring Cloud 中的 Spring Cloud Netflix Ribbon 就是就专门针对 Netflix Ribbon 提供了一个独立的集成实现。

Spring Cloud Netflix Ribbon 相当于 Netflix Ribbon 的客户端。而对于 Spring Cloud Netflix Ribbon 而言，我们的应用服务相当于它的客户端。
Netflix Ribbon、Spring Cloud Netflix Ribbon、应用服务这三者之间的关系以及核心入口如下所示：
    应用服务 (@LoadBalanced @RibbonClient)
                ->      Spring Cloud Netflix Ribbon (LoadBalancedClient)
                                                        ->   Netflix Ribbon (ILoadBalancer (IRule))
```

##### 为啥经过@LoadBalanced 注解的RestTemplate就具备了负载均衡能力了？
```
在 Spring Cloud Netflix Ribbon 中存在一个自动配置类——LoadBalancerAutoConfiguration 类
而在该类中，维护着一个被 @LoadBalanced 修饰的 RestTemplate 对象的列表
在初始化的过程中，对于所有被 @LoadBalanced 注解修饰的 RestTemplate，调用 RestTemplateCustomizer 的 customize 方法进行定制化，该定制化的过程就是对目标 RestTemplate 增加拦截器 LoadBalancerInterceptor。

org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration.LoadBalancerInterceptorConfig
@Configuration(proxyBeanMethods = false)
@ConditionalOnMissingClass("org.springframework.retry.support.RetryTemplate")
static class LoadBalancerInterceptorConfig {
    @Bean
    public LoadBalancerInterceptor ribbonInterceptor(
            LoadBalancerClient loadBalancerClient,
            LoadBalancerRequestFactory requestFactory) {
        return new LoadBalancerInterceptor(loadBalancerClient, requestFactory);
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplateCustomizer restTemplateCustomizer(
            final LoadBalancerInterceptor loadBalancerInterceptor) {
        return restTemplate -> {
            List<ClientHttpRequestInterceptor> list = new ArrayList<>(
                    restTemplate.getInterceptors());
            list.add(loadBalancerInterceptor);
            restTemplate.setInterceptors(list);
        };
    }
}

org.springframework.cloud.client.loadbalancer.LoadBalancerInterceptor - 实时拦截，它的构造函数中传入了一个对象 LoadBalancerClient，而在它的拦截方法本质上就是使用 LoadBalanceClient 来执行真正的负载均衡。
public class LoadBalancerInterceptor implements ClientHttpRequestInterceptor {
 private LoadBalancerClient loadBalancer;
 private LoadBalancerRequestFactory requestFactory;
 public LoadBalancerInterceptor(LoadBalancerClient loadBalancer, LoadBalancerRequestFactory requestFactory) {
     this.loadBalancer = loadBalancer;
     this.requestFactory = requestFactory;
 }
 public LoadBalancerInterceptor(LoadBalancerClient loadBalancer) {
     this(loadBalancer, new LoadBalancerRequestFactory(loadBalancer));
 }
 @Override
 public ClientHttpResponse intercept(final HttpRequest request, final byte[] body,
     final ClientHttpRequestExecution execution) throws IOException {
     final URI originalUri = request.getURI();
     String serviceName = originalUri.getHost();
     Assert.state(serviceName != null, "Request URI does not contain a valid hostname: " + originalUri);
     // 真正执行方法
     return this.loadBalancer.execute(serviceName, requestFactory.createRequest(request, body, execution));
 }
}

LoadBalancerClient 接口定义如下:

public interface LoadBalancerClient extends ServiceInstanceChooser {
 <T> T execute(String serviceId, LoadBalancerRequest<T> request) throws IOException;
 <T> T execute(String serviceId, ServiceInstance serviceInstance,LoadBalancerRequest<T> request) throws IOException;
 URI reconstructURI(ServiceInstance instance, URI original);
}
这里有两个 execute 重载方法，用于根据负载均衡器所确定的服务实例来执行服务调用。
而 reconstructURI 方法则用于构建服务 URI，使用负载均衡所选择的 ServiceInstance 信息重新构造访问 URI，也就是用服务实例的 host 和 port 再加上服务的端点路径来构造一个真正可供访问的服务。

ServiceInstanceChooser 接口定义如下:

public interface ServiceInstanceChooser {
 ServiceInstance choose(String serviceId);
}
从负载均衡角度讲，我们应该重点关注实际上是这个 choose 方法的实现，而提供具体实现的是实现了 LoadBalancerClient 接口的 RibbonLoadBalancerClient，而 RibbonLoadBalancerClient 位于 spring-cloud-netflix-ribbon 工程中。
这样我们的代码流程就从应用程序转入到了 Spring Cloud Netflix Ribbon 中。

在 LoadBalancerClient 接口的实现类 RibbonLoadBalancerClient 中，choose 方法最终调用了如下所示的 getServer 方法
protected Server getServer(ILoadBalancer loadBalancer, Object hint) {
    if (loadBalancer == null) {
        return null;
    }
    // Use 'default' on a null hint, or just pass it on?
    return loadBalancer.chooseServer(hint != null ? hint : "default");
}



```







