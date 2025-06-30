package org.fufeng.action.lambda.p2;

import java.math.BigDecimal;

@FunctionalInterface
public interface PriceCalculation {
    BigDecimal increase(BigDecimal curr, BigDecimal inc);

    static void staticMethod() {
        System.out.println("static method");
    }

    default void defaultMethod() {
        System.out.println("default method");
    }
}
