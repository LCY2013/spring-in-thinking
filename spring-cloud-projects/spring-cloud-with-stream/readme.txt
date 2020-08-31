spring-cloud stream 重要概念信息
    org.springframework.cloud.stream.messaging.Source 消息源
    org.springframework.cloud.stream.messaging.Sink 接收器
    org.springframework.cloud.stream.annotation.EnableBinding 绑定器

首先来认识一下 Spring Cloud Stream 中的几个重要概念。
Destination Binders：目标绑定器，目标指的是 kafka 还是 RabbitMQ，绑定器就是封装了目标中间件的包。
    如果操作的是 kafka 就使用 kafka binder ，如果操作的是 RabbitMQ 就使用 rabbitmq binder。
Destination Bindings：外部消息传递系统和应用程序之间的桥梁，提供消息的“生产者”和“消费者”（由目标绑定器创建）
Message：一种规范化的数据结构，生产者和消费者基于这个数据结构通过外部消息系统与目标绑定器和其他应用程序通信。

spring.cloud.stream.binders，上面提到了 stream 的 3 个重要概念的第一个 「Destination binders」。
    上面的配置文件中就配置了一个 binder，命名为 local_rabbit，指定 type 为 rabbit ，
    表示使用的是 rabbitmq 消息中间件，如果用的是 kafka ，则 type 设置为 kafka。
    environment 就是设置使用的消息中间件的配置信息，包括 host、port、用户名、密码等。
    可以设置多了个 binder，适配不同的场景。
spring.cloud.stream.bindings ，对应上面提到到 「Destination Bindings」。
    这里面可以配置多个 input 或者 output，分别表示消息的接收通道和发送通道，
    对应到 rabbitmq 上就是不同的 exchange。这个配置文件里定义了两个input 、两个output，
    名称分别为 input、log_input、output、log_output。这个名称不是乱起的，在我们的程序代码中会用到，
    用来标示某个方法接收哪个 exchange 或者发送到哪个 exchange 。
每个通道下的 destination 属性指 exchange 的名称，binder 指定在 binders 里设置的 binder，
    上面配置中指定了 local_rabbit 。
可以看到 input、output 对应的 destination 是相同的，log_input、log_output
    对应的 destination 也相同， 也就是对应相同的 exchange。一个表示消息来源，一个表示消息去向。
另外还可以设置 group 。因为服务很可能不止一个实例，如果启动多个实例，那么没必要每个实例都消费同一个消息，
    只要把功能相同的实例的 group 设置为同一个，那么就会只有一个实例来消费消息，避免重复消费的情况。
    如果设置了 group，那么 group 名称就会成为 queue 的名称，如果没有设置 group ，
    那么 queue 就会根据 destination + 随机字符串的方式命名。









