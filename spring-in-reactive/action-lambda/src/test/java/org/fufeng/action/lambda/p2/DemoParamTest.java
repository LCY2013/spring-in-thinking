package org.fufeng.action.lambda.p2;

public class DemoParamTest {
    public static void main(String[] args) {
        Demo0ParamInterf impl0 = () -> "0 param";

        Demo1ParamInterf impl1 = (a) -> System.out.println(a);

        Demo2ParamInterf impl2 = (a, b) -> {
            int c = a * b;
            int d = Math.floorMod(c, a - 1);
            return d;
        };
    }
}
