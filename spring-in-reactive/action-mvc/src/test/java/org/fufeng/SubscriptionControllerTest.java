package org.fufeng;

import org.fufeng.mvc.controller.SubscriptionController;
import org.fufeng.mvc.model.StockSubscription;
import org.fufeng.mvc.service.SubscriptionService;
import org.fufeng.mvc.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebMvcTest(SubscriptionController.class)
public class SubscriptionControllerTest {
    @MockBean
    private SubscriptionService subscriptionService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "tester@qq.com", roles = {"ADMIN"})
    public void shouldReturnViewSubs() throws Exception {
        when(subscriptionService.findByEmail(anyString())).thenReturn(buildSubscriptionList());
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/subscriptions")
                        .with(SecurityMockMvcRequestPostProcessors.user("tester2").roles("OPS")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("subscription"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("email"))
                .andExpect(MockMvcResultMatchers.model().attribute("email", "tester@qq.com"))
                .andExpect(MockMvcResultMatchers.model().attribute("subscriptions",
                        Matchers.containsInAnyOrder(
                                StockSubscription.builder().email("tester@qq.com").symbol("APPL").build()
                        )));
    }

    private List<StockSubscription> buildSubscriptionList() {
        return Arrays.asList(
                StockSubscription.builder().email("tester@qq.com").symbol("APPL").build()
        );
    }
}
