#### Eureka 客户端原理

对于 Eureka 而言，微服务的提供者和消费者都是它的客户端，其中服务提供者关注服务注册、服务续约和服务下线等功能，而服务消费者关注于服务信息的获取。

同时，对于服务消费者而言，为了提高服务获取的性能以及在注册中心不可用的情况下继续使用服务，一般都还会具有缓存机制。

在 Netflix Eureka 中，专门提供了一个客户端包，并抽象了一个客户端接口 EurekaClient，EurekaClient 接口继承自 LookupService 接口，EurekaClient 在 LookupService 接口的基础上提供了一系列扩展方法。

类继承结构关系如下:
```
com.netflix.discovery.shared.LookupService
    com.netflix.discovery.EurekaClient
        com.netflix.discovery.DiscoveryClient
            构造方法中调用了initScheduledTasks()，用于初始化所有的定时任务，具体包含缓存刷新 cacheRefresh、心跳 heartbeat、服务实例复制 InstanceInfoReplicator 等
            org.springframework.cloud.netflix.eureka.CloudEurekaClient

```

#### 服务提供者源码分析

服务提供者关注服务注册、服务续约和服务下线等功能，它使用 Eureka 服务器提供的 Restful API 进行操作。

注册流程如下:
```
通过EurekaClient 调用 Eureka Server Rest API 进行注册
boolean register() throws Throwable {
    logger.info(PREFIX + "{}: registering service...", appPathIdentifier);
    EurekaHttpResponse<Void> httpResponse;
    try {
        // 通过EurekaClient调用Eureka Server Rest API
        httpResponse = eurekaTransport.registrationClient.register(instanceInfo);
    } catch (Exception e) {
        logger.warn(PREFIX + "{} - registration failed {}", appPathIdentifier, e.getMessage(), e);
        throw e;
    }
    if (logger.isInfoEnabled()) {
        logger.info(PREFIX + "{} - registration status: {}", appPathIdentifier, httpResponse.getStatusCode());
    }
    // 通过响应状态判断是否注册成功
    return httpResponse.getStatusCode() == Status.NO_CONTENT.getStatusCode();
}
```
EurekaTransport类是DiscoveryClient类的匿名内部类，它其中持有一个EurekaHttpClient的接口实现对象，该接口定义的方法如下:
```
定义了Eureka所能提供的关于服务注册、发现等一系列操作
public interface EurekaHttpClient {
     EurekaHttpResponse<Void> register(InstanceInfo info);
     EurekaHttpResponse<Void> cancel(String appName, String id);
     EurekaHttpResponse<InstanceInfo> sendHeartBeat(String appName, String id, InstanceInfo info, InstanceStatus overriddenStatus);
     EurekaHttpResponse<Void> statusUpdate(String appName, String id, InstanceStatus newStatus, InstanceInfo info);
     EurekaHttpResponse<Void> deleteStatusOverride(String appName, String id, InstanceInfo info);
     EurekaHttpResponse<Applications> getApplications(String... regions);
     EurekaHttpResponse<Applications> getDelta(String... regions);
     EurekaHttpResponse<Applications> getVip(String vipAddress, String... regions);
     EurekaHttpResponse<Applications> getSecureVip(String secureVipAddress, String... regions);
     EurekaHttpResponse<Application> getApplication(String appName);
     EurekaHttpResponse<InstanceInfo> getInstance(String appName, String id);
     EurekaHttpResponse<InstanceInfo> getInstance(String id);
     void shutdown();
}
```
EurekaHttpClient相关类继承信息如下:
```
com.netflix.discovery.shared.transport.EurekaHttpClient
    -> com.netflix.discovery.shared.transport.decorator.EurekaHttpClientDecorator 这里是一个装饰器的实现，具体所有的实现都来自execute()方法，而该方法是一个抽象方法，由其子类实现。
    
```
EurekaHttpClientFactory接口用于创建EurekaHttpClient，其方法定义如下:
```
public interface EurekaHttpClientFactory {
    EurekaHttpClient newClient();
    void shutdown();
}
```






