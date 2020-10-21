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

[关于Discovery抽象(EurekaDiscoveryClient)使用的项目示例位置](https://github.com/LCY2013/spring-in-thinking/tree/master/spring-cloud-projects/sc-project/user-service/src/test/java/org/fufeng/discovery/user/controller)


 









