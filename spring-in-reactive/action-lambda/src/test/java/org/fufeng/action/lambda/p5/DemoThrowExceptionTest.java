package org.fufeng.action.lambda.p5;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.security.GeneralSecurityException;
import java.util.List;
import java.util.function.Consumer;

public class DemoThrowExceptionTest {

    public ComplexComputer complexComputer = new ComplexComputer();

    @Test
    public void forLoopTest() throws GeneralSecurityException, InterruptedException {
        List<String> items = Lists.newArrayList("foo", "bar", "baz");
        for (String item : items) {
            complexComputer.encrypt(item);
        }
    }

    @Test
    public void throwIOExceptionTest() {
        List<String> items = Lists.newArrayList("foo", "bar", "baz");
        items.stream().forEach(new Consumer<String>() {
            @SneakyThrows
            @Override
            public void accept(String item) {
                    complexComputer.encrypt(item);
            }
        }
      );
    }
}
