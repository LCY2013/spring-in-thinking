package org.fufeng.action.lambda.p3;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Consumer;

@Slf4j
public class DemoConsumerTest {

    @Test
    public void LogConsumer() {
        Consumer<Object> logConsumer = new Consumer<Object>() {
            @Override
            public void accept(Object o) {
                log.info(o.toString());
            }
        };
        logConsumer.accept("Print something");
        logConsumer.accept(System.currentTimeMillis());
    }

    @Test
    public void AndThen() {
        Consumer<List<String>> upperCaseConsumer = strings -> {
            for (int i = 0; i < strings.size(); i++) {
                strings.set(i, strings.get(i).toUpperCase());
            }
        };
        Consumer<List<String>> logConsumer = strings -> strings.forEach(str -> System.out.println(str));

        List<String> list = Lists.newArrayList("foo", "bar", "baz");
        upperCaseConsumer.andThen(logConsumer).accept(list);
    }


}
