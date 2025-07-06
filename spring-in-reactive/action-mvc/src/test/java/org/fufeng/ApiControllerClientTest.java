package org.fufeng;

import org.fufeng.security.WebSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.DisabledIf;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
@ContextConfiguration
@SpringJUnitConfig(value = {AppConfig.class, WebSecurityConfig.class})
@SpringJUnitWebConfig(resourcePath = "src/main/webapp")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@EnabledIf
@DisabledIf
@ExtendWith({SpringExtension.class})
//@EnabledOnOs
//@EnabledOnJre
public class ApiControllerClientTest {
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testApiController() {
        this.webTestClient.get()
                .uri("/api/price")
                .exchange()
                .expectStatus().isOk();
    }
}
