package org.fufeng.action.lambda.p4;

import com.google.common.collect.Lists;
import org.fufeng.action.mvc.model.Stock;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

public class DemoInstanceMethodReferenceTest {
    @Test
    public void orderStockTest() {
        List<Stock> stocks = Lists.newArrayList(
                Stock.builder().name("APPLE").symbol("APPL").build(),
                Stock.builder().name("Amazon").symbol("AMZN").build(),
                Stock.builder().name("Starbucks").symbol("SBUX").build()
        );
        StockComparator stockComparator = new StockComparator();
        stocks.stream()
                .sorted((o1, o2) -> o1.getSymbol().compareTo(o2.getSymbol()))//
                .sorted(stockComparator::compare)
                .collect(Collectors.toList());
        stocks.stream()
//                .forEach(stock -> System.out.println(stock));
                .forEach(System.out::println);
    }
    //instanceReference::methodName

}
