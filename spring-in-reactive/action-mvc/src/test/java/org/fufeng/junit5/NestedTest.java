package org.fufeng.junit5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Outer Test")
public class NestedTest {
    @BeforeEach
    public void init(){
        System.out.println("Prepare for each test");
    }

    @Nested
    @DisplayName("1st inner test")
    class FirstTest {
        @Test
        void test(){
            System.out.println("First inner test run");
        }
    }

    @Nested
    @DisplayName("2nd inner test")
    class Second {
        @Test
        void test(){
            System.out.println("Second inner test run");
        }
    }
}
