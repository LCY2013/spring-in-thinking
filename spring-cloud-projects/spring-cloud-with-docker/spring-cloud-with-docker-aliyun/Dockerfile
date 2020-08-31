FROM adoptopenjdk/openjdk11
#VOLUME /tmp
#COPY target/spring-cloud-docker-images-aliyun.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]
ENTRYPOINT ["/usr/bin/java", "-jar", "/app.jar"]
ARG JAR_FILE
ADD ${JAR_FILE} /app.jar
EXPOSE 8080
