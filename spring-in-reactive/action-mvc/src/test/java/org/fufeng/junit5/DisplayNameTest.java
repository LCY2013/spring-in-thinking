package org.fufeng.junit5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Subscription Unit Test")
public class DisplayNameTest {

    @Test
    @DisplayName("Test subscription call, expected ok")
    void testGetSubscriptionSuccess() {
    }

    @DisplayName("Test subscription call, expected failed")
    @Test
    void testGetSubscriptionFail() {
    }
}


