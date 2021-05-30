package org.fufeng.project.simple;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author luocy
 * @description ThreadLocal 示例
 * @create 2021-05-30
 * @since 1.0
 */
public class ThreadTest {

    private final static InheritableThreadLocal<String> THREAD_LOCAL = new InheritableThreadLocal<>();

    private final static InheritableThreadLocal<String> THREAD_LOCAL2 = new InheritableThreadLocal<>();

    private final static ExecutorService EXE = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        THREAD_LOCAL.set("magic");

        new Thread(() -> {
            System.out.println("1-" + THREAD_LOCAL.get());
            THREAD_LOCAL.remove();
            THREAD_LOCAL2.set("lcy");
        }).start();

        EXE.execute(() -> {
            System.out.println("1-exe: " + THREAD_LOCAL.get());
            System.out.println("2-exe: " + THREAD_LOCAL2.get());
        });

        EXE.shutdown();

        System.out.println("1-main: " + THREAD_LOCAL.get());
        System.out.println("2-main: " + THREAD_LOCAL2.get());

        THREAD_LOCAL.remove();
        THREAD_LOCAL2.remove();
    }

}
