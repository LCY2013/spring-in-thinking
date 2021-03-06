package org.fufeng.reactor.oprator;

import reactor.core.publisher.Flux;

/**
 * @author luocy
 * @description Flux 操作符
 * @program customer-service
 * @create 2021-04-11
 * @since 1.0
 */
public class FluxOperation {

    public static void main(String[] args) {
        //just();
        concat();
    }

    public static void just() {
        Flux.just("hello", "reactor").subscribe(System.out::println);
    }

    public static void fromArray() {
        Flux.fromArray(new Integer[]{1, 2, 3})
                .subscribe(System.out::println);
    }

    public static void concat() {
        Flux.concat(
                Flux.range(4, 2),
                Flux.range(1, 3),
                Flux.range(6, 5)
        ).subscribe(System.out::println);
    }


}
