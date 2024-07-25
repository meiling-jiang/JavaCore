package org.example.day5;

import java.util.concurrent.locks.ReentrantLock;

public class DeadLockDemo {
    private static final ReentrantLock lock1 = new ReentrantLock();
    private static final ReentrantLock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                lock1.lock();
                System.out.println("Thread 1: locked lock 1");
                Thread.sleep(1000);

                // Attempt to acquire lock2
                System.out.println("Thread 1: waiting for lock 2");
                lock2.lock();
                System.out.println("Thread 1: locked lock 2");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                if (lock2.isHeldByCurrentThread()) {
                    lock2.unlock();
                }
                lock1.unlock();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                lock2.lock();
                System.out.println("Thread 2: locked lock 2");
                Thread.sleep(1000);

                // Attempt to acquire lock1
                System.out.println("Thread 2: waiting for lock 1");
                lock1.lock();
                System.out.println("Thread 2: locked lock 1");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                if (lock1.isHeldByCurrentThread()) {
                    lock1.unlock();
                }
                lock2.unlock();
            }
        });

        t1.start();
        t2.start();
    }
    /*
    public static void main(String[] args) {
        final String resource1 = "resource1";
        final String resource2 = "resource2";

        Thread t1 = new Thread() {
            public void run() {
                synchronized (resource1) {
                    System.out.println("Thread 1: locked resource 1");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    // thread 1 is waiting for thread 2 release lock
                    synchronized (resource2) {
                        System.out.println("Thread 1: locked resource 2");
                    }
                }
            }
        };

        Thread t2 = new Thread() {
            public void run() {
                synchronized (resource2) {
                    System.out.println("Thread 2: locked resource 2");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    // thread 2 is waiting for thread 1 release lock
                    synchronized (resource1) {
                        System.out.println("Thread 2: locked resource 1");
                    }
                }
            }
        };

        t1.start();
        t2.start();

    }
    */
}
