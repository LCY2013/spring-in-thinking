#### [Eureka 简介](https://github.com/Netflix/eureka)

```
Eureka is a REST (Representational State Transfer) based service that is primarily used in the AWS cloud for locating services for the purpose of load balancing and failover of middle-tier servers.

At Netflix, Eureka is used for the following purposes apart from playing a critical part in mid-tier load balancing.

For aiding Netflix Asgard - an open source service which makes cloud deployments easier, in

Fast rollback of versions in case of problems avoiding the re-launch of 100's of instances which could take a long time.
In rolling pushes, for avoiding propagation of a new version to all instances in case of problems.
For our cassandra deployments to take instances out of traffic for maintenance.

For our memcached caching services to identify the list of nodes in the ring.

For carrying other additional application specific metadata about services for various other reasons.
```

上面是Eureka开源地址对于Eureka的简介，虽然Eureka2.0项目停止，但是在spring cloud 中作为AP理论实践的Eureka1.0使用还是比较广泛，它的设计思路和理念还是值得借鉴。

#### Eureka 单点搭建 (StandLone)

POM 依赖如下: 
```
<!-- Eureka Server 依赖 -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

配置文件如下:
```
# 定义Eureka服务端口
server:
  port: 9999
# 定义Eureka相关配置信息
eureka:
  client:
    register-with-eureka: false # 单点Eureka取消注册自身
    fetch-registry: false # 不拉取注册信息
    service-url:
      defaultZone: http://localhost:9999
```

启动类如下:

```
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class,args);
    }
}
```

#### Eureka 集群搭建

配置文件如下:
```
# 命令行参数 --spring.profiles.active=eureka1 or eureka2

# 定义eureka 第一个节点
spring:
  profiles: eureka1
  application:
    name: eureka-server
server:
  port: 9998
eureka:
  instance:
    hostname: localhost
  client:
    service-url:
      defaultZone: http://localhost:9999/eureka

---
# 定义eureka 第二个节点
spring:
  profiles: eureka2
  application:
    name: eureka-server
server:
  port: 9999
eureka:
  instance:
    hostname: localhost
  client:
    service-url:
      defaultZone: http://localhost:9998/eureka
```    

Eureka集群相较于单点的唯一需要配置的点在于设置其他的Eureka服务地址用于注册自身的服务到其他Eureka中。

#### Eureka 源码分析

##### 1、Eureka 设计理念、实现原理流程

服务注册（Register）是服务治理的最基本概念，Eureka 客户端的各个微服务通过向 Eureka 服务器提供 IP 地址、端点等各项与服务发现相关的基本信息完成服务注册操作。

Eureka 客户端与服务器端通过短连接（HTTP REST）完成交互，所以在服务续约（Renew）中，Eureka 客户端需要每隔一段时间（定时任务）主动上报自己的运行时状态，从而进行服务续约。

服务实例取消（Cancel）就是 Eureka 客户端主动告知 Eureka 服务器自己不想再注册到 Eureka 中，当Eureka客户端连续一段时间没有向 Eureka 服务器发送服务续约信息时，Eureka 服务器就会认为该服务实例已经不再运行，从而将其从服务列表中进行剔除（Evict）。

##### 2、Eureka 服务注册信息的存储和管理机制

(1) 服务实例存储查询服务相关源码

Interface com.netflix.eureka.registry.InstanceRegistry 两个父类接口

com.netflix.eureka.lease.LeaseManager
```
// 服务注册
void register(T r, int leaseDuration, boolean isReplication);
// 服务取消
boolean cancel(String appName, String id, boolean isReplication);
// 服务续约
boolean renew(String appName, String id, boolean isReplication);
// 服务剔除
void evict();
```

com.netflix.discovery.shared.LookupService
```
主要是关于应用和实例相关查询方法
Application getApplication(String appName);
Applications getApplications();
List<InstanceInfo> getInstancesById(String id);
InstanceInfo getNextServerFromEureka(String virtualHostname, boolean secure);
```

依赖关系如下: 

Interface com.netflix.eureka.registry.InstanceRegistry

-->Abstract com.netflix.eureka.registry.AbstractInstanceRegistry
 
下面这个双层 HashMap 第一层的 ConcurrentHashMap 的 Key 为 spring.application.name，也就是服务名， Value 为一个 ConcurrentHashMap；
而第二层的 ConcurrentHashMap 的 Key 为 instanceId，也就是服务的唯一实例 ID，Value 为 Lease 对象。
Eureka 采用 Lease（租约）这个词来表示对服务注册信息的抽象，Lease 对象保存了服务实例信息以及一些实例服务注册相关的时间，如注册时间 registrationTimestamp、最新的续约时间 lastUpdateTimestamp 等。
   
private final ConcurrentHashMap<String, Map<String, Lease<InstanceInfo>>> registry
                = new ConcurrentHashMap<String, Map<String, Lease<InstanceInfo>>>();
    
-----> com.netflix.eureka.registry.PeerAwareInstanceRegistryImpl   
  
--------> org.springframework.cloud.netflix.eureka.server.InstanceRegistry
    
-->Interface com.netflix.eureka.registry.PeerAwareInstanceRegistry
  
-----> com.netflix.eureka.registry.PeerAwareInstanceRegistryImpl
  
--------> org.springframework.cloud.netflix.eureka.server.InstanceRegistry
  
  
com.netflix.eureka.registry.AbstractInstanceRegistry.register 注册大致流程源码分析如下:
```
public void register(InstanceInfo registrant, int leaseDuration, boolean isReplication) {
    try {
        // 先从已有的注册服务中查询是否已经存在应用相关的实例相关信息
        Map<String, Lease<InstanceInfo>> gMap = registry.get(registrant.getAppName());
        REGISTER.increment(isReplication);
        if (gMap == null) {
            // 这里初始化一个Map结构，用于存储实例ID和实例相关信息
        }
        // 根据实例ID获取实例相关信息
        Lease<InstanceInfo> existingLease = gMap.get(registrant.getId());
        // Retain the last dirty timestamp without overwriting it, if there is already a lease
        if (existingLease != null && (existingLease.getHolder() != null)) {

            // this is a > instead of a >= because if the timestamps are equal, we still take the remote transmitted
            // InstanceInfo instead of the server local copy.
            
            // 如果本地已经存在一个lease，并且本地的lease存在holder对象，就获取本地server 的hodler对象的最后更新时间与正在注册的实例的最后更新时间比较
            // 如果已经存在的lease的hodler对象的最后更新时间是大于正在注册实例的最后更新时间，就用已经存在lease的hodler对象覆盖正在注册的实例

        } else {
            // The lease does not exist and hence it is a new registration
            // 如果lease不存在，就创建一个新的lease
        }
        // 创建新的lease对象，加入到实例ID为key的map结构中
        Lease<InstanceInfo> lease = new Lease<InstanceInfo>(registrant, leaseDuration);
        if (existingLease != null) {
            lease.setServiceUpTimestamp(existingLease.getServiceUpTimestamp());
        }
        gMap.put(registrant.getId(), lease);
        // This is where the initial state transfer of overridden status happens
        if (!InstanceStatus.UNKNOWN.equals(registrant.getOverriddenStatus())) {
            logger.debug("Found overridden status {} for instance {}. Checking to see if needs to be add to the "
                            + "overrides", registrant.getOverriddenStatus(), registrant.getId());
            if (!overriddenInstanceStatusMap.containsKey(registrant.getId())) {
                logger.info("Not found overridden id {} and hence adding it", registrant.getId());
                overriddenInstanceStatusMap.put(registrant.getId(), registrant.getOverriddenStatus());
            }
        }
        InstanceStatus overriddenStatusFromMap = overriddenInstanceStatusMap.get(registrant.getId());
        if (overriddenStatusFromMap != null) {
            registrant.setOverriddenStatus(overriddenStatusFromMap);
        }

        // Set the status based on the overridden status rules
        InstanceStatus overriddenInstanceStatus = getOverriddenInstanceStatus(registrant, existingLease, isReplication);
        registrant.setStatusWithoutDirty(overriddenInstanceStatus);

        // If the lease is registered with UP status, set lease service up timestamp
        if (InstanceStatus.UP.equals(registrant.getStatus())) {
            lease.serviceUp();
        }
        registrant.setActionType(ActionType.ADDED);
        recentlyChangedQueue.add(new RecentlyChangedItem(lease));
        // 刷新最后的更新时间
        registrant.setLastUpdatedTimestamp();
        // 刷新缓存信息
        invalidateCache(registrant.getAppName(), registrant.getVIPAddress(), registrant.getSecureVipAddress());
    } finally {...}
}
```

com.netflix.eureka.registry.AbstractInstanceRegistry.cancel 取消服务大致流程

com.netflix.eureka.registry.AbstractInstanceRegistry.renew 续约服务大致流程

(2) 服务实例缓存相关源码

Eureka 服务器端组件的核心功能是提供服务列表，为了提高性能，Eureka 服务器会缓存一份所有已注册的服务列表，并通过一定的定时机制对缓存数据进行更新。

com.netflix.eureka.resources.ApplicationResource 获取注册信息

com.netflix.eureka.resources.ApplicationResource.getApplication 获取应用相关信息
```
Key cacheKey = new Key(
        Key.EntityType.Application,
        appName,
        keyType,
        CurrentRequestVersion.get(),
        EurekaAccept.fromString(eurekaAccept)
);

String payLoad = responseCache.get(cacheKey);
CurrentRequestVersion.remove();

if (payLoad != null) {
    logger.debug("Found: {}", appName);
    return Response.ok(payLoad).build();
} else {
    logger.debug("Not Found: {}", appName);
    return Response.status(Status.NOT_FOUND).build();
}

这里用到的ResponseCache com.netflix.eureka.registry.ResponseCache 主要方法如下:
void invalidate(String appName, @Nullable String vipAddress, @Nullable String secureVipAddress);
AtomicLong getVersionDelta();
AtomicLong getVersionDeltaWithRegions();
String get(Key key);
byte[] getGZIP(Key key);
void stop();

ResponseCache 唯一的实现类 com.netflix.eureka.registry.ResponseCacheImpl
    Value getValue(final Key key, boolean useReadOnlyCache) {
        Value payload = null;
        try {
            if (useReadOnlyCache) {
                final Value currentPayload = readOnlyCacheMap.get(key);
                if (currentPayload != null) {
                    payload = currentPayload;
                } else {
                    payload = readWriteCacheMap.get(key);
                    readOnlyCacheMap.put(key, payload);
                }
            } else {
                payload = readWriteCacheMap.get(key);
            }
        } catch (Throwable t) {
            logger.error("Cannot get value for key : {}", key, t);
        }
        return payload;
    }
分析: readOnlyCacheMap 与 readWriteCacheMap 为啥存在两个CacheMap呢？
readOnlyCacheMap java.util.ConcurrentHashMap
readWriteCacheMap guava com.google.common.cache.LoadingCache
好处是读写分离，readWriteCacheMap用于写数据，readOnlyCacheMap用于读数据，两个CacheMap中保存的是同一份数据，通过一个定时任务去同步两个CacheMap中的数据
com.netflix.eureka.registry.ResponseCacheImpl.getCacheUpdateTask  将readWriteCacheMap中的数据同步到readOnlyCacheMap中。
```

(2) Eureka高可用相关源码

Eureka 的高可用部署方式被称为Peer Awareness 模式。

com.netflix.eureka.registry.PeerAwareInstanceRegistry

com.netflix.eureka.registry.PeerAwareInstanceRegistryImpl 
```
// 注册方法实现
public void register(final InstanceInfo info, final boolean isReplication) {
    int leaseDuration = Lease.DEFAULT_DURATION_IN_SECS;
    if (info.getLeaseInfo() != null && info.getLeaseInfo().getDurationInSecs() > 0) {
        leaseDuration = info.getLeaseInfo().getDurationInSecs();
    }
    super.register(info, leaseDuration, isReplication);
    // 通过这里向其他节点同步注册相关信息 
    replicateToPeers(Action.Register, info.getAppName(), info.getId(), info, null, isReplication);
}

replicateToPeers 方法核心代码，这里向其他的所有Eureka节点一个一个注册该服务
for (final PeerEurekaNode node : peerEurekaNodes.getPeerEurekaNodes()) {
    // If the url represents this host, do not replicate to yourself.
    if (peerEurekaNodes.isThisMyUrl(node.getServiceUrl())) {
        continue;
    }
    replicateInstanceActionsToPeers(action, appName, id, info, newStatus, node);
}
```

com.netflix.eureka.cluster.PeerEurekaNode 具体的Eureka节点操作实现类
```
所有集群内的操作都是利用到了com.netflix.eureka.cluster.HttpReplicationClient接口的实现进行。
HttpReplicationClient接口的唯一实现是com.netflix.eureka.transport.JerseyReplicationClient。
网络通信使用的是 com.sun.jersey.client.apache4.ApacheHttpClient4。
```

com.netflix.eureka.cluster.PeerEurekaNodes 管理所有的PeerEurekaNode 生命周期的工具类











