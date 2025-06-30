package org.fufeng.action.lambda.p1;

import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;

@Slf4j
public class DemoPart4Test {
      public static void main(String[] args) {
          Runnable runnable = () -> System.out.println("Do something!");

          Comparator<String> stringComparator = (o1, o2) -> 0;
    }
}
