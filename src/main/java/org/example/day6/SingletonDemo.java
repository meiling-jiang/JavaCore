package org.example.day6;

public class SingletonDemo {
    public static void main(String[] args) {

    }

}
// eager loading
class Singleton1{
    private static final Singleton1 instance = new Singleton1();
    private Singleton1(){

    }
    public static Singleton1 getInstance(){
        return instance;
    }
}
// lazy loading: don't assign value directly,
// only assign when you need instance and call getInstance method
class Singleton2{
    // volatile to prevent instruction reordering
    private static volatile Singleton2 instance;


    // prevents instantiation from other classes
    private Singleton2() {}


    // double check locking:
    public static Singleton2 getInstance() {


        // first check without locking: when t1, t2 come in
        if (instance == null) {


            // ensures that only one thread can execute this block at a time
            synchronized(Singleton2.class) {


                // second check with locking: when t1 come in, check if t2 here
                if (instance == null) {
                    instance = new Singleton2();
                    // 1 create instance reference
                    // 2 new singleton();
                    // 3 instance reference points to instance object,
                    // 1 -> 2 -> 3 Steps must occur in this order to prevent issues.

                    // 1 -> 3 -> 2 this makes return issue, so we need to use volatile keyword.
                }
            }
        }
        return instance;
    }
}
