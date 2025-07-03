package org.fufeng.reactor.test.tester.rxjava;

import io.reactivex.rxjava3.core.*;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ObservableTest {
    @Test
    public void creatObservableTest() {
        Mono<String> mono = Mono.just("foo");
        mono.subscribe(System.out::println);

        Observable<String> observable = Observable.just("foo");
        observable.subscribe(System.out::println);
    }

    @Test
    public void operateObservableTest() {
        Flux<Integer> ints = Flux.range(1, 4)
                .map(i -> {
                    if (i <= 3) return i;
                    throw new RuntimeException("i>3");
                });
        ints.subscribe(
                System.out::println,
                err -> System.out.println(err)
        );

        Observable<Integer> integerObservable = Observable.range(1, 4)
                .map(i -> {
                    if (i <= 3) return i;
                    throw new RuntimeException("i>3");
                });
        integerObservable.subscribe(
                System.out::println,
                err -> System.out.println(err)
        );
    }

    @Test
    public void exceptionHandling(){
        Flux.just("foo","bar")
                .map(str -> {
                    try {
                        return Class.forName(str);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        return "empty";
                    }
                });
        Flowable.just("foo","bar")
                .map(str -> Class.forName(str));
    }

    @Test
    public void javaStreamSupport(){
        Flux.just("foo","bar").toStream();
        Flux.just("foo", "bar").toIterable();
        Mono.just("foo").blockOptional();
        Observable.just("foo","bar").toFuture();
    }

}
