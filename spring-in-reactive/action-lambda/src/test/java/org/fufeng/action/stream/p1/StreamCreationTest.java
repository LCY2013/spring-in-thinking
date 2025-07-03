package org.fufeng.action.stream.p1;

import com.google.common.collect.Lists;
import org.fufeng.action.mvc.model.Stock;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class StreamCreationTest {
    @Test
    public void streamGenerate() {
        Stream<Double> stream = Stream.generate(() -> Math.random());
        stream.limit(5).forEach(System.out::println);
    }

    @Test
    public void streamOf() {
        Stream<String> stream1 = Stream.of("foo");
        Stream<String> stream2 = Stream.of("foo", "bar", "baz");
        //stream2 and stream3 are equivalent
        Stream<String> stream3 = Arrays.stream(new String[]{"foo", "bar", "baz"});
    }

    @Test
    public void streamFromCollections() {
        List<Stock> stocks = Lists.newArrayList(
                Stock.builder().name("APPLE").symbol("APPL").build(),
                Stock.builder().name("Amazon").symbol("AMZN").build(),
                Stock.builder().name("Starbucks").symbol("SBUX").build()
        );
        Stream<Stock> stream1 = stocks.stream();
        Map map = new HashMap();
        map.entrySet().stream();
    }

    @Test
    public void streamBuilder() {
        Stream.Builder<Stock> builder = Stream.builder();
        builder.accept(Stock.builder().name("APPLE").symbol("APPL").build());
        builder.accept(Stock.builder().name("Amazon").symbol("AMZN").build());
        builder.accept(Stock.builder().name("Starbucks").symbol("SBUX").build());
        Stream<Stock> stockStream = builder.build();
    }

}
