package org.fufeng.action.lambda.p2;

import java.math.BigDecimal;

public class PriceCalculationImpl implements PriceCalculation{
    @Override
    public BigDecimal increase(BigDecimal curr, BigDecimal inc) {
        return curr.add(inc);
    }

    @Override
    public void defaultMethod() {
        System.out.println("customized default");
    }

    public static void main(String[] args) {
        //static method
        staticMethod();
        //default method
        new PriceCalculationImpl().defaultMethod();
    }
}
