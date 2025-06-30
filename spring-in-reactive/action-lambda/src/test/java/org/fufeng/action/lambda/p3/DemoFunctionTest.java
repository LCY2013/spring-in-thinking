package org.fufeng.action.lambda.p3;

import com.google.common.collect.Lists;
import com.viso.mvc.model.Stock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class DemoFunctionTest {
    @Test
    public void ArithmeticFunction() {
        Function<Integer, Integer> doubled = x -> x * 2;
        System.out.println(doubled.apply(3));

        Function<Integer, Integer> halved = x -> x / 2;
        System.out.println(halved.apply(10));

        Function<Integer, Integer> inched = x -> x + 2;
        System.out.println(inched.apply(20));
    }

    @Test
    public void TypeTransformationFunction() {
        Function<String, Integer> lengthFunction = str -> str.length();
        System.out.println(lengthFunction.apply("woyouhuile"));
    }

    @Test
    public void CompositeFunction() {
        Function<Integer, Integer> doubled = x -> x * 2;
        Function<Integer, Integer> inched = x -> x + 2;

        System.out.println(doubled.compose(inched).apply(10));
        System.out.println(doubled.andThen(inched).apply(10));
    }

    @Test
    public void IdentityFunction() {
        List<Stock> stocks = Lists.newArrayList(
                Stock.builder().name("APPLE").symbol("APPL").build(),
                Stock.builder().name("Amazon").symbol("AMZN").build(),
                Stock.builder().name("Starbucks").symbol("SBUX").build()
        );
        Map<String, Stock> map = stocks.stream()
                .collect(Collectors.toMap(Stock::getSymbol, Function.identity()));
        System.out.println(map);
    }
}
