package org.fufeng.action.lambda.p4;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DemoObjectInstanceMethodReferenceTest {
    @Test
    public void stringObjectInstanceMethodTest() {
        List<String> stringList = Lists.newArrayList("foo", "bar", "baz");
        stringList.forEach(str -> str.toUpperCase());
        stringList.forEach(String::toUpperCase);
    }
}
