package org.fufeng.webtestclient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.reactive.server.WebTestClient;

//另外写一个来方便进行springboottest配置
@SpringBootTest
public class Demo2 {
     private WebTestClient client;


     @BeforeEach
    public void setup(ApplicationContext applicationContext){
         this.client = WebTestClient.bindToApplicationContext(applicationContext).build();
     }

    @Test
    public void bindToAC() {
        this.client.get().uri("/api/prices").exchange()
                .expectBody()
                .consumeWith(System.out::println);
    }
}
