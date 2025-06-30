package org.fufeng.action.lambda.p2;

@FunctionalInterface
public interface Demo3ParamInterf {
    int print();

    //equals is Object method
    boolean equals(Object a);

    //Object.clone is not public, so not functional
//    Object clone();
}
