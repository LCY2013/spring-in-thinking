plugins {
    java
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.graalvm.buildtools.native") version "0.9.28"
}

group = "org.fufeng.native"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    /*implementation("org.springframework.boot:spring-boot-starter-graphql")*/
    implementation("org.springframework.boot:spring-boot-starter-web")
    /*implementation("org.springframework.boot:spring-boot-starter-webflux")*/
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    testImplementation("junit:junit:4.13.1")
    runtimeOnly("com.h2database:h2")
    compileOnly("org.projectlombok:lombok")
    /*developmentOnly("org.springframework.boot:spring-boot-devtools")*/
    /*developmentOnly("org.springframework.boot:spring-boot-docker-compose")*/
    /*annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")*/
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    /*testImplementation("org.springframework.graphql:spring-graphql-test")*/
}

tasks.withType<Test> {
    useJUnitPlatform()
}
