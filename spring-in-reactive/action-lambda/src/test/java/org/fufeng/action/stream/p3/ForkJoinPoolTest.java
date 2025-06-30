package org.fufeng.action.stream.p3;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

@Slf4j
public class ForkJoinPoolTest {
    @Test
    public void testCommonForkJoinPool(){
        log.info("Common Pool Parallelism: {}", ForkJoinPool.commonPool().getParallelism());
        getData().parallelStream().forEach(num -> {
            log.info("printing the num {}", num);
        });
    }

    @Test
    public void testSeperateForkJoinPool() {
        ForkJoinPool pool1 = new ForkJoinPool(2);
        pool1.submit(() -> {
            getData().parallelStream().forEach(num -> {
                log.info("printing the num {}", num);
            });
        });
    }

    public static List<Integer> getData() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }
        return list;
    }
}
