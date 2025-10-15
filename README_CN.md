# Spring 全家桶学习项目

一个全面的Spring生态系统学习仓库，涵盖了Spring Framework、Spring Boot、Spring Cloud、Spring Data以及响应式编程的所有方面。本项目通过实践示例和真实世界的实现，为理解Spring技术提供了实用的指导。

## 📚 目录

- [项目概述](#项目概述)
- [项目结构](#项目结构)
- [模块介绍](#模块介绍)
- [技术栈](#技术栈)
- [快速开始](#快速开始)
- [功能特性](#功能特性)
- [学习路径](#学习路径)
- [贡献指南](#贡献指南)
- [许可证](#许可证)

## 🎯 项目概述

本仓库旨在为Spring生态系统提供全面的学习材料和实践示例。从基础的Spring Framework概念到使用Spring Cloud的高级微服务架构，从Spring WebFlux的响应式编程到现代Spring Boot特性，应有尽有。

项目被组织成多个模块，每个模块专注于特定的Spring技术和用例，使其成为所有技能水平开发者的优秀资源。

## 🏗️ 项目结构

```
spring-in-thinking/
├── spring-boot-projects/          # Spring Boot 综合示例
├── spring-cloud-projects/         # Spring Cloud 微服务
├── spring-cloud-alibaba-projects/ # Spring Cloud Alibaba 集成
├── spring-data-projects/          # Spring Data JPA 示例
├── spring-in-action/              # Spring Boot 实战示例
├── spring-in-reactive/            # Spring 响应式编程
├── spring-native/                 # Spring Native 示例
├── springcss-parent-pom/          # 微服务架构示例
├── thinking-in-spring/            # Spring Framework 核心概念
├── thinking-in-aop/               # 面向切面编程
├── thinking-in-spring-boot/       # Spring Boot 基础
├── thinking-on-spring/            # Spring 高级概念
└── mvnclean.sh                   # Maven 清理脚本
```

## 📦 模块介绍

### 1. Spring Boot 项目 (`spring-boot-projects/`)

Spring Boot应用程序的综合集合，展示了各种功能和集成：

- **spring-boot-with-web**: 基础Web应用设置
- **spring-boot-with-rest**: RESTful API开发
- **spring-boot-with-jsp**: JSP视图集成
- **spring-boot-with-datasource**: 数据库连接
- **spring-boot-with-mybatis**: MyBatis ORM集成
- **spring-boot-with-jpa**: JPA/Hibernate集成
- **spring-boot-with-es**: Elasticsearch集成
- **spring-boot-with-redis**: Redis缓存
- **spring-boot-with-kafka**: Apache Kafka消息传递
- **spring-boot-with-validator**: 数据验证
- **spring-boot-with-websocket**: WebSocket支持
- **spring-boot-with-web-async**: 异步处理
- **spring-boot-with-webservice**: SOAP Web服务
- **spring-boot-with-security**: Spring Security集成
- **spring-boot-with-logs**: 日志配置
- **spring-boot-with-actuator**: 应用监控
- **spring-boot-with-configuration**: 外部配置
- **spring-boot-with-starter**: 自定义启动器开发
- **spring-boot-with-mongodb**: MongoDB集成

**技术栈**: Spring Boot 3.2.0, Java 21, Maven

### 2. Spring Cloud 项目 (`spring-cloud-projects/`)

使用Spring Cloud的微服务架构示例：

- **spring-cloud-with-starter**: 自定义Spring Cloud启动器
- **spring-cloud-with-config**: 配置管理
- **spring-cloud-with-configuration**: 外部配置
- **spring-cloud-with-eureka**: 使用Eureka的服务发现
- **spring-cloud-with-consul**: 使用Consul的服务发现
- **spring-cloud-with-oauth2**: OAuth2认证
- **spring-cloud-with-docker**: Docker容器化
- **spring-cloud-with-monitor**: 应用监控
- **spring-cloud-with-stream**: 消息流
- **sc-project**: 完整的微服务示例，包含：
  - Eureka Server (高可用)
  - Gateway Server
  - User Service
  - Intervention Service
  - Zuul Gateway

**技术栈**: Spring Cloud Hoxton.SR8, Spring Boot 2.3.5, Java 21

### 3. Spring Cloud Alibaba 项目 (`spring-cloud-alibaba-projects/`)

与阿里巴巴云原生解决方案的集成：

- **spring-cloud-dubbo-nacos**: 使用Nacos的Dubbo RPC
- **sca-nacos-info**: Nacos配置和发现

**技术栈**: Spring Cloud Alibaba 2.2.2, Dubbo 2.7.8, Spring Boot 2.3.7

### 4. Spring Data 项目 (`spring-data-projects/`)

高级Spring Data JPA示例和模式：

- **spring-jpa-auditing**: JPA审计功能
- **spring-jpa-entity-manager**: EntityManager使用模式
- **spring-jpa-oprator**: JPA操作和查询
- **spring-jpa-specification**: 使用Specifications的动态查询构建

**技术栈**: Spring Data JPA, Gradle, H2数据库

### 5. Spring 实战 (`spring-in-action/`)

来自Spring Boot实战的实用示例：

- **spring-in-datasource**: 数据源配置
- **spring-in-orm**: 对象关系映射
- **spring-in-nosql**: NoSQL数据库集成
- **spring-in-cache**: 缓存策略
- **spring-in-aop**: 面向切面编程
- **spring-in-mvc**: Spring MVC模式
- **spring-in-resttemplate**: REST客户端实现
- **spring-in-hateoas**: HATEOAS实现
- **spring-in-autoconfigure**: 自动配置
- **spring-in-actuator**: 应用监控
- **spring-in-cloud**: 云原生特性
- **spring-in-circuit-break**: 断路器模式
- **spring-in-config**: 配置管理
- **spring-in-cloud-stream**: 消息流

**技术栈**: Spring Boot 2.5.0, Java 11

### 6. Spring 响应式编程 (`spring-in-reactive/`)

使用Spring WebFlux和Project Reactor的响应式编程：

- **reactor**: Project Reactor基础
- **action-springmvc**: 传统Spring MVC
- **action-springsecurity**: Spring Security集成
- **action-lambda**: Lambda表达式和函数式编程
- **action-reactor**: 响应式流
- **action-springsecurity-reactor**: 响应式Spring Security
- **action-springmvc-reactor**: 响应式MVC
- **action-mvc**: 模型-视图-控制器模式
- **action-webflux**: Spring WebFlux
- **action-webflux-tomcat**: 使用Tomcat的WebFlux
- **action-r2db**: 响应式数据库访问
- **action-websocket**: 响应式WebSocket
- **action-rsocket**: RSocket协议

**技术栈**: Spring Boot 3.5.3, Java 21, Project Reactor, Spring WebFlux

### 7. Spring Native (`spring-native/`)

使用Spring Native的原生镜像编译：

- GraalVM原生镜像编译
- Spring Boot原生应用
- 性能优化技术

### 8. SpringCSS 父POM (`springcss-parent-pom/`)

完整的微服务架构示例：

- **sbi-customer-service**: 客户管理服务
- **sbi-account-service**: 账户管理服务
- **sbi-order-service**: 订单处理服务
- **sbi-admin-server**: 管理界面
- **sbi-message**: 消息处理
- **spring-boot-features**: Spring Boot特性展示

**技术栈**: Spring Boot 3.2.0

### 9. Spring 核心思考 (`thinking-in-spring/`)

Spring Framework核心概念和模式：

- **ioc-container-overview**: 控制反转容器
- **spring-bean**: Bean管理和生命周期
- **dependency-lookup**: 依赖查找模式
- **dependency-injection**: 依赖注入机制
- **dependency-source**: 依赖源
- **bean-scope**: Bean作用域（单例、原型等）
- **bean-lifecycle**: Bean生命周期管理
- **configuration-metadata**: 配置元数据处理
- **resource**: 资源管理
- **i18n**: 国际化
- **validation**: 数据验证
- **data-binding**: 数据绑定机制
- **conversion**: 类型转换
- **generic**: 泛型处理
- **event**: 应用事件
- **annotation**: 注解处理
- **environment**: 环境抽象
- **application-context-lifecycle**: 应用上下文生命周期

**技术栈**: Spring Framework 5.3.2, Java 21

### 10. AOP 思考 (`thinking-in-aop/`)

使用Spring的面向切面编程：

- **aop-overview**: AOP概念和概述
- **aop-features**: 高级AOP特性

**技术栈**: Spring Framework 5.3.4, AspectJ 1.9.6, Java 21

### 11. Spring Boot 思考 (`thinking-in-spring-boot/`)

Spring Boot基础和最佳实践：

- Spring Boot核心概念
- 自动配置
- 启动器依赖
- 应用属性
- 配置文件和环境

### 12. Spring 高级思考 (`thinking-on-spring/`)

Spring高级概念和模式：

- Spring高级特性
- 性能优化
- 最佳实践和模式

## 🛠️ 技术栈

### 核心技术
- **Java**: 8, 11, 21
- **Spring Framework**: 5.3.x
- **Spring Boot**: 2.3.x, 2.5.x, 3.2.x, 3.5.x
- **Spring Cloud**: Hoxton.SR8, Hoxton.SR9
- **Spring Cloud Alibaba**: 2.2.2
- **Spring Data JPA**: 最新版本
- **Spring WebFlux**: 最新版本
- **Project Reactor**: 最新版本

### 构建工具
- **Maven**: 3.8.1
- **Gradle**: 最新版本

### 数据库
- **H2**: 内存数据库
- **MySQL**: 关系型数据库
- **MongoDB**: 文档数据库
- **Redis**: 内存数据存储
- **Elasticsearch**: 搜索引擎

### 消息代理
- **Apache Kafka**: 分布式流平台
- **RabbitMQ**: 消息代理

### 服务发现和配置
- **Eureka**: 服务发现
- **Consul**: 服务网格
- **Nacos**: 动态服务发现和配置

### 安全
- **Spring Security**: 认证和授权
- **OAuth2**: 授权框架

### 监控和可观测性
- **Spring Boot Actuator**: 应用监控
- **Micrometer**: 指标收集

### 容器化
- **Docker**: 容器化平台

## 🚀 快速开始

### 前置要求

- Java 8, 11, 或 21
- Maven 3.6+
- Docker（可选）
- IDE（IntelliJ IDEA, Eclipse, VS Code）

### 安装步骤

1. **克隆仓库**
   ```bash
   git clone https://github.com/LCY2013/spring-in-thinking.git
   cd spring-in-thinking
   ```

2. **选择一个模块进行探索**
   ```bash
   cd spring-boot-projects/spring-boot-with-web
   ```

3. **构建项目**
   ```bash
   mvn clean compile
   ```

4. **运行应用**
   ```bash
   mvn spring-boot:run
   ```

### 运行特定示例

每个模块都包含特定的示例。导航到所需的模块并按照README说明操作：

```bash
# Spring Boot Web应用
cd spring-boot-projects/spring-boot-with-web
mvn spring-boot:run

# Spring Cloud Eureka Server
cd spring-cloud-projects/spring-cloud-with-eureka/spring-cloud-with-eureka-server
mvn spring-boot:run

# 响应式WebFlux应用
cd spring-in-reactive/action-webflux
mvn spring-boot:run
```

## ✨ 功能特性

### 全面覆盖
- **完整Spring生态系统**: 从基础Spring Framework到高级微服务
- **真实世界示例**: 可在生产环境中使用的实用实现
- **多版本支持**: 支持不同的Spring Boot和Java版本
- **最佳实践**: 行业标准模式和实践

### 学习路径
- **初学者友好**: 从基础Spring概念开始
- **渐进式学习**: 逐步构建复杂性
- **实践练习**: 运行和修改示例
- **详细文档**: 详细的解释和注释

### 现代技术
- **响应式编程**: Spring WebFlux和Project Reactor
- **云原生**: Spring Cloud和微服务
- **原生编译**: 使用GraalVM的Spring Native
- **现代Java**: Java 21特性和模式

## 🔧 实用脚本

### Maven清理脚本 (`mvnclean.sh`)
用于清理仓库中所有Maven项目的实用脚本：

```bash
./mvnclean.sh
```

此脚本遍历所有目录，并为包含`pom.xml`文件的项目运行`mvn clean`。

## 📖 学习资源

### 推荐学习路径

1. **从Spring核心开始** (`thinking-in-spring/`)
   - 理解IoC容器
   - 学习依赖注入
   - 掌握Bean生命周期

2. **转向Spring Boot** (`spring-boot-projects/`)
   - Web应用
   - 数据访问
   - 安全

3. **探索微服务** (`spring-cloud-projects/`)
   - 服务发现
   - 配置管理
   - API网关

4. **学习响应式编程** (`spring-in-reactive/`)
   - Project Reactor
   - Spring WebFlux
   - 响应式流

5. **高级主题** (`thinking-in-aop/`, `spring-native/`)
   - 面向切面编程
   - 原生编译

### 额外资源

- [Spring Framework 文档](https://docs.spring.io/spring-framework/docs/current/reference/html/)
- [Spring Boot 参考指南](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Cloud 文档](https://spring.io/projects/spring-cloud)
- [Spring Data 文档](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)

## 🤝 贡献指南

欢迎贡献！请随时提交Pull Request。对于重大更改，请先打开一个issue来讨论您想要更改的内容。

### 贡献指南

1. Fork仓库
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开Pull Request

### 代码风格

- 遵循Java命名约定
- 使用有意义的变量和方法名
- 为复杂逻辑添加注释
- 在适当的地方包含单元测试

## 📄 许可证

本项目采用MIT许可证 - 查看[LICENSE](LICENSE)文件了解详情。

## 🙏 致谢

- Spring Framework团队提供了出色的框架
- Spring Boot团队简化了Spring开发
- Spring Cloud团队提供了微服务工具
- 所有帮助改进此项目的贡献者

## 📞 联系方式

如有问题、建议或贡献，请：

- 在GitHub上打开issue
- 联系维护者
- 加入Spring社区讨论

---

**与Spring一起快乐学习！🚀**

此仓库持续更新新的示例和最佳实践。给仓库点星⭐以了解最新的Spring技术和模式。
