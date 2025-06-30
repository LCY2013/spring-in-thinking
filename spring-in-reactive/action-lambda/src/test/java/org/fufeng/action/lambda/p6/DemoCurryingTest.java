package org.fufeng.action.lambda.p6;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DemoCurryingTest {

    @Test
    public void testSimpleStockModelCreation() {
        BiFunction<String, String, StockModelA> StockModelCreator = (symbol, name) -> new StockModelA(symbol, name);

        Function<String, Function<String, StockModelA>> CurryingStockModelInnerClassCreator =
                symbol -> name -> new StockModelA(symbol, name);

    }

    @Test
    public void testMoreAttrStockModelCreation() {
        Function<String, Function<String, Function<String, Function<BigDecimal, Function<BigDecimal, StockModelB>>>>>
                curryingStockModelCreator =
                symbol -> name -> desc -> currPrice -> prevPrice -> new StockModelB(symbol, name, desc, currPrice, prevPrice);

        StockModelB demoStockModel = curryingStockModelCreator
                .apply("SYMBOL")
                .apply("NAME")
                .apply("DESCRIPTION")
                .apply(new BigDecimal("1.00"))
                .apply(new BigDecimal("0.99"));
    }

    @Test
    public void testPartialModelCreation() {
        Function<String, Function<String, Function<String, Function<BigDecimal, Function<BigDecimal, StockModelB>>>>>
                curryingStockModelCreator =
                symbol -> name -> desc -> currPrice -> prevPrice -> new StockModelB(symbol, name, desc, currPrice, prevPrice);

        Function<BigDecimal, Function<BigDecimal, StockModelB>>
                partialStockModelCreator =
                currPrice -> prevPrice -> curryingStockModelCreator
                        .apply("SYMBOL")
                        .apply("NAME")
                        .apply("DESCRIPTION")
                        .apply(currPrice)
                        .apply(prevPrice);
        StockModelB fromPartialCreated = partialStockModelCreator
                .apply(new BigDecimal("1.00"))
                .apply(new BigDecimal("0.99"));
        System.out.println(fromPartialCreated);
    }
}
