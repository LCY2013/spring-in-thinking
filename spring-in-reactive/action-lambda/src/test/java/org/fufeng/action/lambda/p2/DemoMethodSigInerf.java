package org.fufeng.action.lambda.p2;

public class DemoMethodSigInerf {
    interface X {
        int m(Iterable<String> arg);
    }

    interface Y {
        int m(Iterable<String> arg);
    }

    @FunctionalInterface
    interface Z extends X, Y {
    }
}
