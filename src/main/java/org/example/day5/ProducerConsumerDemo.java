package org.example.day5;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerDemo {
    public static void main(String[] args) {
        ProducerConsumerModel sharedObject = new ProducerConsumerModel();
        List<Producer> producers = new ArrayList<>();
        List<Consumer> consumers = new ArrayList<>();
        for (int i=1; i<=5; i++) producers.add(new Producer("producer-" + i, sharedObject));
        for (int i=1; i<=5; i++) consumers.add(new Consumer("consumer-" + i, sharedObject));

        for (Producer p: producers) p.start();
        for (Consumer c: consumers) c.start();

        /* print out
        producer-1 put 57
        consumer-2 take 57
        producer-3 put 60
        producer-5 put 32
        consumer-1 take 57
        producer-4 wait... queue is full
        consumer-4 take 60
        producer-2 wait... queue is full
        consumer-5 take 32
        consumer-3 take 57
        producer-4 wait... queue is full
        producer-2 wait... queue is full
        */

    }
}
class ProducerConsumerModel{
    private Queue<Integer> queue = new LinkedList<>();
    private final int capacity = 3;
    private Random myRandom = new Random();
//    Lock lock = new ReentrantLock();
//    Condition queueNotFull = lock.newCondition();
//    Condition queueNotEmpty = lock.newCondition();
//
//    public void put() {
//        lock.lock();
//        try{
//            while (queue.size() == capacity) {
//                System.out.println(Thread.currentThread().getName() + " wait... queue is full");
//                queueNotFull.await();
//            }
//            int tempValue = myRandom.nextInt(100);
//            queue.offer(tempValue);
//            System.out.println(Thread.currentThread().getName() + " put " + tempValue);
//            queueNotEmpty.signalAll();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } finally {
//            lock.unlock();
//        }
//    }
//
//    public void take() {
//        lock.lock();
//        try{
//            while (queue.isEmpty()) {
//                System.out.println(Thread.currentThread().getName() + "wait... queue is empty");
//                queueNotEmpty.await();
//            }
//            int tempValue = queue.poll();
//            queue.offer(tempValue);
//            System.out.println(Thread.currentThread().getName() + " take " + tempValue);
//            queueNotFull.signalAll();
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } finally {
//            lock.unlock();
//        }
//    }

    public synchronized void put() {
        while (queue.size() == capacity) {
            try {
                System.out.println(Thread.currentThread().getName() + " wait... queue is full");
                wait(); // Wait until there is space in the queue
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
        int tempValue = myRandom.nextInt(100);
        queue.offer(tempValue);
        System.out.println(Thread.currentThread().getName() + " put " + tempValue);
        notifyAll(); // Notify waiting consumers
    }

    public synchronized void take() {
        while (queue.isEmpty()) {
            try {
                System.out.println(Thread.currentThread().getName() + " wait... queue is empty");
                wait(); // Wait until there is an item in the queue
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
        int tempValue = queue.poll();
        System.out.println(Thread.currentThread().getName() + " take " + tempValue);
        notifyAll(); // Notify waiting producers
    }
}

class Producer extends Thread{
    private String name;
    ProducerConsumerModel pc;
    public Producer(String name, ProducerConsumerModel sharedObject) {
        super(name);
        pc = sharedObject;
    }
    @Override
    public void run() {
        pc.put();
    }
}

class Consumer extends Thread{
    private String name;
    ProducerConsumerModel pc;
    public Consumer(String name, ProducerConsumerModel sharedObject) {
        super(name);
        pc = sharedObject;
    }
    @Override
    public void run() {
        pc.take();
    }
}
