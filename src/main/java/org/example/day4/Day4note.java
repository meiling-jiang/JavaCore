package org.example.day4;

import java.util.concurrent.*;

public class Day4note {
    /*
    1. Stream API (Java8 feature)
        java.util.stream package
        lazy evaluation/computation: means they are not executed until a terminal operation is invoked

        intermediate operation: any operation return stream
            filter, map, flatmap, distinct, limit
        terminal operation: any operation return data type
            collect, forEach, reduce, min, max.....

        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 2, 4, 5));  // 2 3 4 5 3 5 6 -> even 2 4 6
        List<Integer> res = list.stream().map(e -> e + 1).filter(e -> e % 2 == 0).collect(Collectors.toList());
        System.out.println(res);

        Character[] array = new Character[] {'a', 'a', 'b', 'c'};
        Map<Character, Integer> map = Arrays.stream(array).collect(Collectors.toMap(c -> c, c-> 1, (a, b)-> a+b));
        Map<Character, Integer> map = Arrays.stream(array).collect(Collectors.groupingBy(c-> c, Collectors.summingInt(e -> 1)));
        System.out.println(map);

    2. Multi-threading
        Thread vs Process
            Thread: share memory space, heap, each thread has independent stack, PC register
            Process: contains threads, independent memory space, stack, heap, os resources

        thread state (thread lifecycle)
            new, runnable, blocked, waiting, timed_waiting, terminated

            state transition:
            New
             |
             v
            Runnable <--> Blocked
             |
             v
            Waiting <---> Timed Waiting
             |
             v
            Terminated

        thread creation
            extends Thread
            implements Runnable interface
            implements Callable interface

        Runnable vs Callable
            runnable: run, no return, no exception
            callable: call, has return, has exception


    3. Thread Pool
        Customized ThreadPool params:
            ThreadPoolExecutor: corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler

        Predefined Thread pool (do not recommend because OOM: out of memory error)
                - newFixedThreadPool
                - newSingleThreadPool
                - newCachedThreadPool
                - newScheduleThreadPool
    */

    public static void main(String[] args) {
        // Predefined Thread pool
        ExecutorService tp1 = Executors.newFixedThreadPool(2);
        ExecutorService tp2 = Executors.newSingleThreadExecutor();
        ExecutorService tp3 = Executors.newCachedThreadPool();
        Executors.newScheduledThreadPool(3);

        // Thread Pool
        ExecutorService threadpool = new ThreadPoolExecutor(
                2,
                5,
                2L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(4),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy()
        );

        for (int i = 1; i <= 10 ; i++) {
            int finalI = i;
            threadpool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " this is working on " + finalI);
            });
        }
        threadpool.shutdown();

        // Thread
//        Thread t1 = new MyThread();
//        t1.start();
//        Thread t2 = new Thread(new RunnableThread());
//        t2.start();
//
//        FutureTask task = new FutureTask(new CallableThread());
//        Thread t3 = new Thread(task);
//        t3.start();
//        System.out.println(t3.getName());
    }
}
class MyThread extends Thread{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " this is created by extending thread class");
    }
}

class RunnableThread implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " this is created by implementing Runnable interface");
    }
}

class CallableThread implements Callable{
    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " this is created by implementing Callable interface");
        return 1;
    }
}
