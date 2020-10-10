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
通过EurekaClient的实现类DiscoveryClient 调用 Eureka Server Rest API 进行注册
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
作为工厂模式定义的EurekaHttpClientFactory接口用于创建EurekaHttpClient，其方法定义如下:
```
public interface EurekaHttpClientFactory {
    EurekaHttpClient newClient();
    void shutdown();
}
```
EurekaHttpClientFactory接口类继承信息如下:
```
com.netflix.discovery.shared.transport.EurekaHttpClientFactory
    -> com.netflix.discovery.shared.transport.decorator.RetryableEurekaHttpClient
    -> com.netflix.discovery.shared.transport.decorator.MetricsCollectingEurekaHttpClient
```
com.netflix.discovery.shared.transport.EurekaHttpClients EurekaHttpClient 工具类，它的主要实现方法如下:
```
// 用于构造一个SessionedEurekaHttpClient的工厂类EurekaHttpClientFactory
static EurekaHttpClientFactory canonicalClientFactory(final String name,
                                                          final EurekaTransportConfig transportConfig,
                                                          final ClusterResolver<EurekaEndpoint> clusterResolver,
                                                          final TransportClientFactory transportClientFactory) {
    return new EurekaHttpClientFactory() {
        @Override
        public EurekaHttpClient newClient() {
            return new SessionedEurekaHttpClient(
                    name,
                    RetryableEurekaHttpClient.createFactory(
                            name,
                            transportConfig,
                            clusterResolver,
                            RedirectingEurekaHttpClient.createFactory(transportClientFactory),
                            ServerStatusEvaluators.legacyEvaluator()),
                    transportConfig.getSessionedClientReconnectIntervalSeconds() * 1000
            );
        }
        @Override
        public void shutdown() {
            wrapClosable(clusterResolver).shutdown();
        }
    };
}
```
可以看到上面的方法有一个TransportClientFactory参数，该工厂就是用来创建原始调用的EurekaHttpClient，而该接口的定义如下:
```
public interface TransportClientFactory {
    EurekaHttpClient newClient(EurekaEndpoint serviceUrl);
    void shutdown();
}
```
TransportClientFactory接口的继承结构如下:
```
com.netflix.discovery.shared.transport.TransportClientFactory
    -> com.netflix.discovery.shared.transport.decorator.RedirectingEurekaHttpClient
    -> com.netflix.discovery.shared.transport.decorator.MetricsCollectingEurekaHttpClient
    -> com.netflix.discovery.shared.transport.jersey.Jersey1TransportClientFactories
    -> com.netflix.eureka.transport.EurekaServerHttpClients
    -> com.netflix.discovery.shared.transport.jersey.JerseyEurekaHttpClientFactory
    -> org.springframework.cloud.netflix.eureka.http.RestTemplateTransportClientFactory
    -> com.netflix.eureka.transport.JerseyRemoteRegionClientFactory
    -> org.springframework.cloud.netflix.eureka.http.WebClientTransportClientFactory
```
Eureka客户端调用API客户端生成流程如下:
```
EurekaHttpClient(自定义接口行为抽象)
       |
EurekaJerseyClient...(获取底层调用端)
       |
ApacheHttpClient4(底层HTTP调用) 
```

#### 服务消费者源码分析

众所周知，作为一个微服务的解决方案，服务消费者端对于注册中心的依赖是十分重要的，但是注册中心的不确定性是不可预期的，比如网络抖动，注册中心宕机等...
所以，注册中心的消费者客户端需要做好服务提供者缓存来应当这种情况减少服务大面积的崩溃情况，也可以加速服务获取速度，减少注册中心压力。

Eureka本身是一个基于AP(CAP)的分布式理论实践的一款中间件服务，而对于注册中心的主要功能来说就是服务注册和发现机制，Eureka的服务发现机制就是采用了客户端的定时轮训(pull模式)。

具体的代码位置:
```
com.netflix.discovery.DiscoveryClient.DiscoveryClient
    -> com.netflix.discovery.DiscoveryClient.initScheduledTasks 
// 初始化所有的定时任务
private void initScheduledTasks() {
    if (clientConfig.shouldFetchRegistry()) {
        // registry cache refresh timer
        int registryFetchIntervalSeconds = clientConfig.getRegistryFetchIntervalSeconds();
        int expBackOffBound = clientConfig.getCacheRefreshExecutorExponentialBackOffBound();
        cacheRefreshTask = new TimedSupervisorTask(
                "cacheRefresh",
                scheduler,
                cacheRefreshExecutor,
                registryFetchIntervalSeconds,
                TimeUnit.SECONDS,
                expBackOffBound,
                new CacheRefreshThread()
        );
        scheduler.schedule(
                cacheRefreshTask,
                registryFetchIntervalSeconds, TimeUnit.SECONDS);
    }

    if (clientConfig.shouldRegisterWithEureka()) {
        int renewalIntervalInSecs = instanceInfo.getLeaseInfo().getRenewalIntervalInSecs();
        int expBackOffBound = clientConfig.getHeartbeatExecutorExponentialBackOffBound();
        logger.info("Starting heartbeat executor: " + "renew interval is: {}", renewalIntervalInSecs);

        // Heartbeat timer
        heartbeatTask = new TimedSupervisorTask(
                "heartbeat",
                scheduler,
                heartbeatExecutor,
                renewalIntervalInSecs,
                TimeUnit.SECONDS,
                expBackOffBound,
                new HeartbeatThread()
        );
        scheduler.schedule(
                heartbeatTask,
                renewalIntervalInSecs, TimeUnit.SECONDS);

        // InstanceInfo replicator
        instanceInfoReplicator = new InstanceInfoReplicator(
                this,
                instanceInfo,
                clientConfig.getInstanceInfoReplicationIntervalSeconds(),
                2); // burstSize

        statusChangeListener = new ApplicationInfoManager.StatusChangeListener() {
            @Override
            public String getId() {
                return "statusChangeListener";
            }

            @Override
            public void notify(StatusChangeEvent statusChangeEvent) {
                if (InstanceStatus.DOWN == statusChangeEvent.getStatus() ||
                        InstanceStatus.DOWN == statusChangeEvent.getPreviousStatus()) {
                    // log at warn level if DOWN was involved
                    logger.warn("Saw local status change event {}", statusChangeEvent);
                } else {
                    logger.info("Saw local status change event {}", statusChangeEvent);
                }
                instanceInfoReplicator.onDemandUpdate();
            }
        };

        if (clientConfig.shouldOnDemandUpdateStatusChange()) {
            applicationInfoManager.registerStatusChangeListener(statusChangeListener);
        }

        instanceInfoReplicator.start(clientConfig.getInitialInstanceInfoReplicationIntervalSeconds());
    } else {
        logger.info("Not registering with Eureka server per configuration");
    }
}
```

消费者主要关注的点在于服务的上下线，源码流程如下:
```
定时任务 cacheRefreshTask
    -> com.netflix.discovery.DiscoveryClient.CacheRefreshThread
        -> com.netflix.discovery.DiscoveryClient.refreshRegistry
            -> com.netflix.discovery.DiscoveryClient.fetchRegistry
```
fetchRegistry 拉取注册信息源码分析:
```
private boolean fetchRegistry(boolean forceFullRegistryFetch) {
    Stopwatch tracer = FETCH_REGISTRY_TIMER.start();
    try {
        // 获取的应用信息
        Applications applications = getApplications();

        if (...) //Client application does not have latest library supporting delta
        {   // 如果获取增量(delta)是disabled或者是第一次获取就全量拉取
            getAndStoreFullRegistry();
        } else {
            // 增量拉取服务实例
            getAndUpdateDelta(applications);
        }
        // 计算设置一致性hash码
        applications.setAppsHashCode(applications.getReconcileHashCode());
    } catch (Throwable e) {
        return false;
    } finally {
        if (tracer != null) {
            tracer.stop();
        }
    }
    // 更新实例远程状态前刷新缓存
    onCacheRefreshed();

    // 更新远程服务实例状态
    updateInstanceRemoteStatus();

    // registry was fetched successfully, so return true
    return true;
}
```

com.netflix.discovery.DiscoveryClient.getAndUpdateDelta 增量更新实例服务方法源码分析:
```
private void getAndUpdateDelta(Applications applications) throws Throwable {
    long currentUpdateGeneration = fetchRegistryGeneration.get();

    Applications delta = null;
    // 获取增量信息
    EurekaHttpResponse<Applications> httpResponse = eurekaTransport.queryClient.getDelta(remoteRegionsRef.get());
    if (httpResponse.getStatusCode() == Status.OK.getStatusCode()) {
        delta = httpResponse.getEntity();
    }

    // 增量信息为空
    if (delta == null) {
        // 如果增量信息为空，就进行全量更新
        getAndStoreFullRegistry();
    } else if (fetchRegistryGeneration.compareAndSet(currentUpdateGeneration, currentUpdateGeneration + 1)) {
        String reconcileHashCode = "";
        if (fetchRegistryUpdateLock.tryLock()) {
            try {
                // 返回的增量信息与本地信息进行比较，合并增量信息
                updateDelta(delta);
                // 合并后生成新的一致性hash码
                reconcileHashCode = getReconcileHashCode(applications);
            } finally {
                fetchRegistryUpdateLock.unlock();
            }
        } else {
            //日志打印
        }
        // 比较本地数据中的hashcode和增量的hashcode是否一致
        if (!reconcileHashCode.equals(delta.getAppsHashCode()) || clientConfig.shouldLogDeltaDiff()) {
            // hashcode不一致就获取远程服务全量的注册信息
            reconcileAndLogDifference(delta, reconcileHashCode);  // this makes a remoteCall
        }
    } else {
        //日志打印
    }
}
```

Eureka 服务器端会保存一个服务注册列表的缓存，Eureka 官方文档中提到这个数据保留时间是三分钟，而 Eureka 客户端的定时调度机制会每隔 30 秒刷选本地缓存。原则上，只要 Eureka 客户端不停地获取服务器端的更新数据，就能保证自己的数据和 Eureka 服务器端的保持一致。但如果客户端在 3 分钟之内没有获取更新数据，就会导致自身与服务器端的数据不一致，这是这种更新机制所必须要考虑的问题，也是我们自己在设计类似场景时的一个注意点。

为了解决上面的这个问题，Eureka 采用了一致性 HashCode 方法来进行解决。Eureka 服务器端每次返回的增量数据中都会带有一个一致性 HashCode，这个 HashCode 会与 Eureka 客户端用本地服务列表数据算出的一致性 HashCode 进行比对，如果两者不一致就证明增量更新出了问题，这时候就需要执行一次全量更新。

Eureka一致性HashCode计算方法如下:
```
DiscoveryClient: 
private String getReconcileHashCode(Applications applications) {
    // TreeMap 进行排序，用于计算HashCode
    TreeMap<String, AtomicInteger> instanceCountMap = new TreeMap<String, AtomicInteger>();
    if (isFetchingRemoteRegionRegistries()) {
        for (Applications remoteApp : remoteRegionVsApps.values()) {
            remoteApp.populateInstanceCountMap(instanceCountMap);
        }
    }
    applications.populateInstanceCountMap(instanceCountMap);
    return Applications.getReconcileHashCode(instanceCountMap);
}

Applications:
public static String getReconcileHashCode(Map<String, AtomicInteger> instanceCountMap) {
    StringBuilder reconcileHashCode = new StringBuilder(75);
    for (Map.Entry<String, AtomicInteger> mapEntry : instanceCountMap.entrySet()) {
        reconcileHashCode.append(mapEntry.getKey()).append(STATUS_DELIMITER).append(mapEntry.getValue().get())
                .append(STATUS_DELIMITER);
    }
    return reconcileHashCode.toString();
}
```


