# Spring in Thinking

A comprehensive Spring ecosystem learning repository that covers all aspects of Spring Framework, Spring Boot, Spring Cloud, Spring Data, and reactive programming. This project serves as a practical guide for understanding Spring technologies through hands-on examples and real-world implementations.

## 📚 Table of Contents

- [Overview](#overview)
- [Project Structure](#project-structure)
- [Modules](#modules)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Features](#features)
- [Contributing](#contributing)
- [License](#license)

## 🎯 Overview

This repository is designed to provide comprehensive learning materials and practical examples for the Spring ecosystem. It covers everything from basic Spring Framework concepts to advanced microservices architecture using Spring Cloud, reactive programming with Spring WebFlux, and modern Spring Boot features.

The project is organized into multiple modules, each focusing on specific Spring technologies and use cases, making it an excellent resource for developers at all skill levels.

## 🏗️ Project Structure

```
spring-in-thinking/
├── spring-boot-projects/          # Spring Boot comprehensive examples
├── spring-cloud-projects/         # Spring Cloud microservices
├── spring-cloud-alibaba-projects/ # Spring Cloud Alibaba integration
├── spring-data-projects/          # Spring Data JPA examples
├── spring-in-action/              # Spring Boot in Action examples
├── spring-in-reactive/            # Reactive programming with Spring
├── spring-native/                 # Spring Native examples
├── springcss-parent-pom/          # Microservices architecture example
├── thinking-in-spring/            # Core Spring Framework concepts
├── thinking-in-aop/               # Aspect-Oriented Programming
├── thinking-in-spring-boot/       # Spring Boot fundamentals
├── thinking-on-spring/            # Advanced Spring concepts
└── mvnclean.sh                   # Maven cleanup script
```

## 📦 Modules

### 1. Spring Boot Projects (`spring-boot-projects/`)

A comprehensive collection of Spring Boot applications demonstrating various features and integrations:

- **spring-boot-with-web**: Basic web application setup
- **spring-boot-with-rest**: RESTful API development
- **spring-boot-with-jsp**: JSP view integration
- **spring-boot-with-datasource**: Database connectivity
- **spring-boot-with-mybatis**: MyBatis ORM integration
- **spring-boot-with-jpa**: JPA/Hibernate integration
- **spring-boot-with-es**: Elasticsearch integration
- **spring-boot-with-redis**: Redis caching
- **spring-boot-with-kafka**: Apache Kafka messaging
- **spring-boot-with-validator**: Data validation
- **spring-boot-with-websocket**: WebSocket support
- **spring-boot-with-web-async**: Asynchronous processing
- **spring-boot-with-webservice**: SOAP web services
- **spring-boot-with-security**: Spring Security integration
- **spring-boot-with-logs**: Logging configuration
- **spring-boot-with-actuator**: Application monitoring
- **spring-boot-with-configuration**: External configuration
- **spring-boot-with-starter**: Custom starter development
- **spring-boot-with-mongodb**: MongoDB integration

**Technologies**: Spring Boot 3.2.0, Java 21, Maven

### 2. Spring Cloud Projects (`spring-cloud-projects/`)

Microservices architecture examples using Spring Cloud:

- **spring-cloud-with-starter**: Custom Spring Cloud starter
- **spring-cloud-with-config**: Configuration management
- **spring-cloud-with-configuration**: External configuration
- **spring-cloud-with-eureka**: Service discovery with Eureka
- **spring-cloud-with-consul**: Service discovery with Consul
- **spring-cloud-with-oauth2**: OAuth2 authentication
- **spring-cloud-with-docker**: Docker containerization
- **spring-cloud-with-monitor**: Application monitoring
- **spring-cloud-with-stream**: Message streaming
- **sc-project**: Complete microservices example with:
  - Eureka Server (HA)
  - Gateway Server
  - User Service
  - Intervention Service
  - Zuul Gateway

**Technologies**: Spring Cloud Hoxton.SR8, Spring Boot 2.3.5, Java 21

### 3. Spring Cloud Alibaba Projects (`spring-cloud-alibaba-projects/`)

Integration with Alibaba's cloud-native solutions:

- **spring-cloud-dubbo-nacos**: Dubbo RPC with Nacos
- **sca-nacos-info**: Nacos configuration and discovery

**Technologies**: Spring Cloud Alibaba 2.2.2, Dubbo 2.7.8, Spring Boot 2.3.7

### 4. Spring Data Projects (`spring-data-projects/`)

Advanced Spring Data JPA examples and patterns:

- **spring-jpa-auditing**: JPA auditing functionality
- **spring-jpa-entity-manager**: EntityManager usage patterns
- **spring-jpa-oprator**: JPA operations and queries
- **spring-jpa-specification**: Dynamic query building with Specifications

**Technologies**: Spring Data JPA, Gradle, H2 Database

### 5. Spring in Action (`spring-in-action/`)

Practical examples from Spring Boot in Action:

- **spring-in-datasource**: Data source configuration
- **spring-in-orm**: Object-relational mapping
- **spring-in-nosql**: NoSQL database integration
- **spring-in-cache**: Caching strategies
- **spring-in-aop**: Aspect-oriented programming
- **spring-in-mvc**: Spring MVC patterns
- **spring-in-resttemplate**: REST client implementation
- **spring-in-hateoas**: HATEOAS implementation
- **spring-in-autoconfigure**: Auto-configuration
- **spring-in-actuator**: Application monitoring
- **spring-in-cloud**: Cloud-native features
- **spring-in-circuit-break**: Circuit breaker pattern
- **spring-in-config**: Configuration management
- **spring-in-cloud-stream**: Message streaming

**Technologies**: Spring Boot 2.5.0, Java 11

### 6. Spring in Reactive (`spring-in-reactive/`)

Reactive programming with Spring WebFlux and Project Reactor:

- **reactor**: Project Reactor fundamentals
- **action-springmvc**: Traditional Spring MVC
- **action-springsecurity**: Spring Security integration
- **action-lambda**: Lambda expressions and functional programming
- **action-reactor**: Reactive streams
- **action-springsecurity-reactor**: Reactive Spring Security
- **action-springmvc-reactor**: Reactive MVC
- **action-mvc**: Model-View-Controller patterns
- **action-webflux**: Spring WebFlux
- **action-webflux-tomcat**: WebFlux with Tomcat
- **action-r2db**: Reactive database access
- **action-websocket**: Reactive WebSocket
- **action-rsocket**: RSocket protocol

**Technologies**: Spring Boot 3.5.3, Java 21, Project Reactor, Spring WebFlux

### 7. Spring Native (`spring-native/`)

Native image compilation with Spring Native:

- GraalVM native image compilation
- Spring Boot native applications
- Performance optimization techniques

### 8. SpringCSS Parent POM (`springcss-parent-pom/`)

Complete microservices architecture example:

- **sbi-customer-service**: Customer management service
- **sbi-account-service**: Account management service
- **sbi-order-service**: Order processing service
- **sbi-admin-server**: Administrative interface
- **sbi-message**: Message handling
- **spring-boot-features**: Spring Boot features showcase

**Technologies**: Spring Boot 3.2.0

### 9. Thinking in Spring (`thinking-in-spring/`)

Core Spring Framework concepts and patterns:

- **ioc-container-overview**: Inversion of Control container
- **spring-bean**: Bean management and lifecycle
- **dependency-lookup**: Dependency lookup patterns
- **dependency-injection**: Dependency injection mechanisms
- **dependency-source**: Dependency sources
- **bean-scope**: Bean scopes (singleton, prototype, etc.)
- **bean-lifecycle**: Bean lifecycle management
- **configuration-metadata**: Configuration metadata handling
- **resource**: Resource management
- **i18n**: Internationalization
- **validation**: Data validation
- **data-binding**: Data binding mechanisms
- **conversion**: Type conversion
- **generic**: Generic type handling
- **event**: Application events
- **annotation**: Annotation processing
- **environment**: Environment abstraction
- **application-context-lifecycle**: Application context lifecycle

**Technologies**: Spring Framework 5.3.2, Java 21

### 10. Thinking in AOP (`thinking-in-aop/`)

Aspect-Oriented Programming with Spring:

- **aop-overview**: AOP concepts and overview
- **aop-features**: Advanced AOP features

**Technologies**: Spring Framework 5.3.4, AspectJ 1.9.6, Java 21

### 11. Thinking in Spring Boot (`thinking-in-spring-boot/`)

Spring Boot fundamentals and best practices:

- Core Spring Boot concepts
- Auto-configuration
- Starter dependencies
- Application properties
- Profiles and environments

### 12. Thinking on Spring (`thinking-on-spring/`)

Advanced Spring concepts and patterns:

- Advanced Spring features
- Performance optimization
- Best practices and patterns

## 🛠️ Technologies Used

### Core Technologies
- **Java**: 8, 11, 21
- **Spring Framework**: 5.3.x
- **Spring Boot**: 2.3.x, 2.5.x, 3.2.x, 3.5.x
- **Spring Cloud**: Hoxton.SR8, Hoxton.SR9
- **Spring Cloud Alibaba**: 2.2.2
- **Spring Data JPA**: Latest
- **Spring WebFlux**: Latest
- **Project Reactor**: Latest

### Build Tools
- **Maven**: 3.8.1
- **Gradle**: Latest

### Databases
- **H2**: In-memory database
- **MySQL**: Relational database
- **MongoDB**: Document database
- **Redis**: In-memory data store
- **Elasticsearch**: Search engine

### Message Brokers
- **Apache Kafka**: Distributed streaming platform
- **RabbitMQ**: Message broker

### Service Discovery & Configuration
- **Eureka**: Service discovery
- **Consul**: Service mesh
- **Nacos**: Dynamic service discovery and configuration

### Security
- **Spring Security**: Authentication and authorization
- **OAuth2**: Authorization framework

### Monitoring & Observability
- **Spring Boot Actuator**: Application monitoring
- **Micrometer**: Metrics collection

### Containerization
- **Docker**: Containerization platform

## 🚀 Getting Started

### Prerequisites

- Java 8, 11, or 21
- Maven 3.6+
- Docker (optional)
- IDE (IntelliJ IDEA, Eclipse, VS Code)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/LCY2013/spring-in-thinking.git
   cd spring-in-thinking
   ```

2. **Choose a module to explore**
   ```bash
   cd spring-boot-projects/spring-boot-with-web
   ```

3. **Build the project**
   ```bash
   mvn clean compile
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

### Running Specific Examples

Each module contains specific examples. Navigate to the desired module and follow the README instructions:

```bash
# Spring Boot Web Application
cd spring-boot-projects/spring-boot-with-web
mvn spring-boot:run

# Spring Cloud Eureka Server
cd spring-cloud-projects/spring-cloud-with-eureka/spring-cloud-with-eureka-server
mvn spring-boot:run

# Reactive WebFlux Application
cd spring-in-reactive/action-webflux
mvn spring-boot:run
```

## ✨ Features

### Comprehensive Coverage
- **Complete Spring Ecosystem**: From basic Spring Framework to advanced microservices
- **Real-world Examples**: Practical implementations you can use in production
- **Multiple Versions**: Support for different Spring Boot and Java versions
- **Best Practices**: Industry-standard patterns and practices

### Learning Path
- **Beginner Friendly**: Start with basic Spring concepts
- **Progressive Learning**: Build complexity gradually
- **Hands-on Practice**: Run and modify examples
- **Documentation**: Detailed explanations and comments

### Modern Technologies
- **Reactive Programming**: Spring WebFlux and Project Reactor
- **Cloud Native**: Spring Cloud and microservices
- **Native Compilation**: Spring Native with GraalVM
- **Modern Java**: Java 21 features and patterns

## 🔧 Utility Scripts

### Maven Clean Script (`mvnclean.sh`)
A utility script to clean all Maven projects in the repository:

```bash
./mvnclean.sh
```

This script iterates through all directories and runs `mvn clean` for projects with `pom.xml` files.

## 📖 Learning Resources

### Recommended Learning Path

1. **Start with Core Spring** (`thinking-in-spring/`)
   - Understand IoC container
   - Learn dependency injection
   - Master bean lifecycle

2. **Move to Spring Boot** (`spring-boot-projects/`)
   - Web applications
   - Data access
   - Security

3. **Explore Microservices** (`spring-cloud-projects/`)
   - Service discovery
   - Configuration management
   - API gateways

4. **Learn Reactive Programming** (`spring-in-reactive/`)
   - Project Reactor
   - Spring WebFlux
   - Reactive streams

5. **Advanced Topics** (`thinking-in-aop/`, `spring-native/`)
   - Aspect-oriented programming
   - Native compilation

### Additional Resources

- [Spring Framework Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/)
- [Spring Boot Reference Guide](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Cloud Documentation](https://spring.io/projects/spring-cloud)
- [Spring Data Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

### Contribution Guidelines

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Code Style

- Follow Java naming conventions
- Use meaningful variable and method names
- Add comments for complex logic
- Include unit tests where appropriate

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Spring Framework team for the amazing framework
- Spring Boot team for simplifying Spring development
- Spring Cloud team for microservices tools
- All contributors who have helped improve this project

## 📞 Contact

For questions, suggestions, or contributions, please:

- Open an issue on GitHub
- Contact the maintainers
- Join the Spring community discussions

---

**Happy Learning with Spring! 🚀**

This repository is continuously updated with new examples and best practices. Star ⭐ the repository to stay updated with the latest Spring technologies and patterns.
