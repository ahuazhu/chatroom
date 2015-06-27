package io.isharon.chat.pool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhengwenzhu on 15-6-27.
 */
public class Pool {
    private static ExecutorService es = Executors.newFixedThreadPool(100);

    public static void submit(Callable callable) {
        es.submit(callable);
    }
}
