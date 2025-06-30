package org.fufeng.action.lambda.p4;

import org.junit.jupiter.api.Test;

public class DemoStaticMethodReferenceTest {
    @Test
    public void printStringTest() {
        Thread t = new Thread(() -> printString());
        Thread t1 = new Thread(DemoStaticMethodReferenceTest::printString);
        t.start();
    }

    public static void printString() {
        System.out.println("string");
    }

    //ClassName::staticMethodName
}
