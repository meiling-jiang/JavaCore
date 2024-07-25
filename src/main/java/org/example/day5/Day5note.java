package org.example.day5;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Day5note {
    /*
    1. CompletableFuture(class): implements Future and CompletionState
        sync and async

        - without any return
        System.out.println("main thread starts...");
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try{
                System.out.println("child thread starts working...");
                Thread.sleep(5000);
                System.out.println("child thread done.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        future.get();// use get method to retrieve the result
        System.out.println("main thread ends.");

        - with return
        System.out.println("main thread starts...");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try{
                System.out.println("child thread starts working...");
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "child thread done.";
        });
        System.out.println("task result: " + future.get());
        System.out.println("main thread ends.");

        - chain operations
        public static Integer num = 100;
        public static void main(String[] args) throws ExecutionException, InterruptedException {
            System.out.println("main thread starts...");
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                System.out.println("start adding 50 to num");
                num += 50;
                return num;
            }).thenApply(val -> {
                return val * 2;
            }).thenApplyAsync(val -> val + 100);
            System.out.println("task result: " + future.get()); // 400
            System.out.println("main thread ends.");
        }
        ----------------------------------------------------------------------------------------
        public static Integer num = 100;
        public static void main(String[] args) throws ExecutionException, InterruptedException {
            System.out.println("main thread starts...");
            CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
                System.out.println("start adding 50 to num");
                num += 50;
                return num;
            }).thenApply(val -> {
                return val * 2;
            }).thenApplyAsync(val -> val + 100).thenAccept(val -> {
                System.out.println("this is the last step from main thread");
                System.out.println("result: " + (val - 1)); // 399

            });
            System.out.println("task result: " + future.get()); // null
            System.out.println("main thread ends.");
        }

        - Exception
        public static Integer num = 100;
        public static void main(String[] args) throws ExecutionException, InterruptedException {
            System.out.println("main thread starts...");
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                int result = 5 / 0;
                System.out.println("adding 50 to num");
                num += 50;
                return num;
            }).exceptionally(e -> {
                System.out.println(e.getMessage()); // java.lang.ArithmeticException: / by zero
                return 404;
            });
            System.out.println("task result: " + future.get()); // 404
            System.out.println("main thread ends.");
        }

        - Handle Exception
        public static Integer num = 100;
        public static void main(String[] args) throws ExecutionException, InterruptedException {
            System.out.println("main thread starts...");
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
                int result = 5 / 0;
                System.out.println("adding 50 to num");
                num += 50;
                return num;
            }).handle((val, e) -> {
                System.out.println("get into the handle method");
                if (e == null) {
                    System.out.println("task is done");
                    return num;
                } else {
                    // Exception thrown: java.lang.ArithmeticException: / by zero
                    System.out.println("Exception thrown: " + e.getMessage());
                    return 400;
                }
            });
            System.out.println("task result: " + future.get()); // 400
            System.out.println("main thread ends.");
        }

        - thenCompose, thenCombine
        - allOf, anyOf

    2. Lock
        parallel (don't have conflict) vs concurrent (has conflict)
        Lock:
            synchronized(unfair lock) code block/ method/ static method/ class
            Lock interface: ReentrantLock (support fair lock): lock(), unlock(), tryLock(), newCondition()...
            ReadWriteLock interface: ReentrantReadWriteLock
                Lock readLock();
                Lock writeLock();

    3. Dead Lock
        two or more threads are waiting for each other
        how to prevent deadlock
            avoid nested locks
            avoid unnecessary lock
                Stack, Vector, HashTable
            Use Thread join()
    */

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int initialNum = 1;

        // CompletableFuture chain
        CompletableFuture<Void> future = CompletableFuture
                .supplyAsync(() -> {
                    // Async step 1: Add 10 to the initial number
                    int result = initialNum + 10;
                    System.out.println("Async Step 1: Added 10, result = " + result);
                    return result;
                })
                .thenApply(result -> {
                    // Sync step: Multiply the result by 4
                    int resultSync = result * 4;
                    System.out.println("Sync Step: Multiplied by 4, result = " + resultSync);
                    return resultSync;
                })
                .thenAccept(result -> {
                    // Async step 2: Consume the result and print it
                    System.out.println("Async Step 2: Final result = " + result);
                })
                .exceptionally(ex -> {
                    // Handle any exceptions that occurred during the process
                    System.out.println("Exception occurred: " + ex.getMessage());
                    return null;
                });

        // Wait for the completion of the future to see the results
        try {
            future.get(); // Block and wait for the future to complete
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}


class MyClass{ // synchronized code block/ method/ static method/ class
    public void method() {
        synchronized (MyClass.class) {
            // to do
        }
    }
    public synchronized void method1() { // lock regular object

    }
    public synchronized static void method2() { // lock whole class

    }
    public void method3() {
        synchronized (this) {

        }
    }
}
