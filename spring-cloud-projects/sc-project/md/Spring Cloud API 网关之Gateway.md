### [Spring Cloud Gateway 简介](https://docs.spring.io/spring-cloud-gateway/docs/2.2.5.RELEASE/reference/html/)
Spring Cloud Gateway 是 Spring 官方自己开发的一款 API 网关。

Spring Cloud Gateway 和 Netflix Zuul 做一个对比：

1、Zuul 的实现原理是对 Servlet 的一层封装，通信模式上采用的是阻塞式 I/O，而在技术体系上，Spring Cloud Gateway 基于最新的 Spring 5 和 Spring Boot 2，以及用于响应式编程的 Project Reactor 框架，提供的是响应式、非阻塞式 I/O 模型，所以较之 Netflix Zuul，性能上Spring Cloud Gateway 要更胜一筹。

2、从功能上，Spring Cloud Gateway 也比 Zuul 更为丰富，除了通用的服务路由机制之外，Spring Cloud Gateway 还支持请求限流等面向服务容错方面的功能，同样也能与 Hystrix 等框架进行良好的集成。

### 简单使用
要想在微服务架构中引入 Spring Cloud Gateway，我们同样需要构建一个独立的 Spring Boot 应用程序，并在 Maven 中添加如下依赖项：
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```

启动类如下：
```
@SpringBootApplication
@EnableDiscoveryClient
public class GateWayServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(GateWayServerApplication.class,args);
    }
}
```

### Spring Cloud Gateway 与服务路由

在引入 Spring Cloud Gateway 之后，先看作为 API 网关的核心功能，即服务路由，Spring Cloud Gateway 的基本架构进行一个初步的了解。

### Spring Cloud Gateway 基本架构

Spring Cloud Gateway 中的核心概念有两个，一个是过滤器（Filter），一个是谓词（Predicate）。

Spring Cloud Gateway 的整体架构图如下图所示：

![spring_cloud_gateway_diagram](image/spring_cloud_gateway_diagram.png)

Spring Cloud Gateway 中的过滤器和 Zuul 中的过滤器是同一个概念。它们都可以用于在处理 HTTP 请求之前或之后修改请求本身，及对应响应结果，区别在于两者的类型和实现方式不同，Spring Cloud Gateway 的种类非常丰富。

谓词，本质上是一种判断条件，用于将 HTTP 请求与路由进行匹配，Spring Cloud Gateway 内置了大量的谓词组件，可以分别对 HTTP 请求的消息头、请求路径等常见的路由媒介进行自动匹配以便决定路由结果。

Spring Cloud Gateway 除了指定服务的名称和目标服务地址之外，最主要的开发工作就是配置谓词和过滤器规则。

### Spring Cloud Gateway 实现路由

通过配置项来设置 Spring Cloud Gateway 对 HTTP 请求的路由行为，但与 Zuul 不同，默认情况下，Spring Cloud Gateway 并不支持与服务发现机制之间的自动集成，为了启用该功能，需要在配置文件中添加如下配置项：
```
#启用spring gateway 与注册中心的服务发现
spring:
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
```

一个完整案例如下：
```
#启用spring gateway 与注册中心的服务发现
spring:
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
      routes:
        - id: route-id
          uri: lb://service
          predicates:
            - Path=/test/**
          filters:
            - PrefixPath=/prefix

在上述配置中，首先我们使用 id 配置项指定了这条路由信息的编号，这个例子中的“route-id”就起了这个作用。
而 uri 配置项中的“lb”代表负载均衡LoadBalance，也就是说在访问 url 指定的服务名称时需要集成负载均衡机制,请注意“lb”配置项中所指定的服务名称同样需要与保存在 Eureka 中的服务名称完全一致。
然后我们使用了谓词来对请求路径进行匹配，这里的“Path=/test/”代表所有以“/test”开头的请求都将被路由到这条路径中。
最后我们还定义了一个过滤器**，这个过滤器的作用是为路径添加前缀（Prefix），这样当请求“/test/”时，最后转发到目标服务的路径将会变为“/prefix/test/”。
```

案例配置[sc-project](https://github.com/LCY2013/spring-in-thinking/tree/master/spring-cloud-projects/sc-project/gateway-server)
```
#定义网关服务启动端口号
server:
  port: 9991

eureka:
  instance:
    preferIpAddress: true
  client:
    registerWithEureka: true
    fetchRegistry: true
    service-url:
      defaultZone: http://localhost:9999/eureka

#启用spring gateway 与注册中心的服务发现
spring:
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
      routes:
        - id: user-route
          uri: lb://user-service
          predicates:
            - Path=/user/**
          filters:
            - RewritePath=/user/(?<path>.*), /$\{path}
        - id: device-route
          uri: lb://device-service
          predicates:
            - Path=/device/**
          filters:
            - RewritePath=/device/(?<path>.*), /$\{path}
        - id: intervention-route
          uri: lb://intervention-service
          predicates:
            - Path=/intervention/**
          filters:
            - RewritePath=/intervention/(?<path>.*), /$\{path}
```

在上述配置中，我们设置了 Eureka 服务的地址并启用了服务发现机制，然后根据 Eureka 保存的服务名称和地址定义了三条路由规则：user-route、device-route 和 intervention-route 分别对应 user-service、device-service 和 intervention-service 这三个微服，通过在各个服务名称前面加上“lb://”来实现客户端负载均衡。

同时对请求路径设置了谓词，并添加了一个对请求路径进行重写（Rewrite）的过滤器，通常，每个微服务自身通过根路径“/”来暴露服务。基于以上配置，通过 Spring Cloud Gateway 暴露它们时，则分别在路径上添加了“/user”“/device”和“/intervention”前缀。

### Spring Cloud Gateway 过滤器

Spring Cloud Gateway 提供了一个全局过滤器（GlobalFilter）的概念，这个概念的应用对象是路由本身，如果过滤器只针对某一个路由生效，那它就是一个普通的过滤器，而那些对所有路由都生效的过滤器就是全局过滤器，Spring Cloud Gateway 内置了一大批过滤器。

首先可以使用全局过滤器来对所有 HTTP 请求进行拦截，具体做法是实现 GlobalFilter 接口，示例代码如下所示：
```
@Configuration
public class JWTAuthFilter implements GlobalFilter {
 @Override
 public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
     ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
     builder.header("Authorization","JWTToken");
     chain.filter(exchange.mutate().request(builder.build()).build());
     return chain.filter(exchange.mutate().request(builder.build()).build());
 }
}
```

以上代码利用全局过滤器在所有的请求中添加 Header 的实现方法，给所有经过 API 网关的 HTTP 请求添加了一个消息头，用来设置与 JWT Token 相关的安全认证信息。

注意到这里的 filter 方法返回了一个 Mono 对象。你可能会问这个 Mono 对象究竟是什么呢？这是在响应式编程框架 Project Reactor 中代表单个返回值的流式对象。

Zuul网关有 pre、route、post 和 error 这四种类型的过滤器，分别对应一个 HTTP 请求的不同生命周期。在这点上，Spring Cloud Gateway 与 Zuul 在设计思想上是一致的，它也提供了可用于 pre 和 post 两种阶段的过滤器。

以下代码展示了一个 PostGatewayFilter 的实现方式，首先继承一个 AbstractGatewayFilterFactory 类，然后覆写 apply 方法来提供针对 ServerHttpResponse 对象的任何操作：
```
public class PostGatewayFilterFactory extends AbstractGatewayFilterFactory {
 public PostGatewayFilterFactory() {
    super(Config.class);
 }

 public GatewayFilter apply() {
    return apply(o -> {});
 }

 @Override
 public GatewayFilter apply(Config config) {
     return (exchange, chain) -> {
         return chain.filter(exchange).then(Mono.fromRunnable(() -> {
         ServerHttpResponse response = exchange.getResponse();
         //针对Response的各种处理
        }));
     };
 }

 public static class Config {
 }
}
```

PreGatewayFilter 的实现方式也类似，只不过处理的目标一般是 ServerHttpRequest 对象。

相比 Zuul，请求限流是 Spring Cloud Gateway 的一项特色功能，Spring Cloud Gateway 中专门存在一个请求限流过滤器 RequestRateLimiter。

限流，一般的做法是衡量请求处理的速率并对其进行控制，RequestRateLimiter 抽象了两个参数来完成这一目标，其中第一个参数是 replenishRate，该参数用于指定允许用户每秒处理的请求数。而第二个参数是 burstCapacity，它被用来设置一秒钟内允许的最大请求数，如果我们把请求看成是往一个桶里倒水，那么 replenishRate 参数用于控制水流的速度，而 burstCapacity 用于控制桶的大小，请求限流过滤器的完整配置示例如下所示：
```
spring:
  cloud:
    gateway:
	routes:
	 - id: intervention-route
       uri: lb://intervention-service
       predicates:
         - Path=/intervention/**
       filters:
         - name: RequestRateLimiter
           args:
             redis-rate-limiter.replenishRate: 50
             redis-rate-limiter.burstCapacity: 100
```

请求限流过滤器在实现上依赖 Redis，所以需要引入 spring-boot-starter-data-redis-reactive 这个支持响应式 Redis 的依赖，然后针对访问 intervention-service 的场景，基于 Redis 分别设置 replenishRate 和 burstCapacity 值为 50 和 100。





