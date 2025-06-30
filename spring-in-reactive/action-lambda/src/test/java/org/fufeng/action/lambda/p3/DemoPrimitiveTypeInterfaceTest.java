package org.fufeng.action.lambda.p3;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.function.IntFunction;

@Slf4j
public class DemoPrimitiveTypeInterfaceTest {
    @Test
    public void testIntFunction() {
        IntFunction intFunction = x -> x + 1;

        Function<Integer, Integer> integerFunction = x -> x + 1;
    }
}
