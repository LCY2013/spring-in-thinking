package org.fufeng;

import org.fufeng.mvc.controller.ApiController;
import org.fufeng.mvc.model.StockSubscription;
import org.fufeng.mvc.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(ApiController.class)
public class ApiControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void checkReturn() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/price"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.APPL").value("100"));
    }

    private List<StockSubscription> buildSubscriptionList() {
        return Arrays.asList(
                StockSubscription.builder().email("tester").symbol("APPL").build()
        );
    }

}
