package org.example.day3;

import org.example.exception.UserNotFoundException;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Day3note {
    /*
    1. Exception

        Exception: represent some conditions that you might want to catch and handle.
            check exception: compile time exception (means you want to handle)
		                     EX: ClassNoteFoundException, IOException, SQLException,FileNotFoundException

	                         - try catch: want to handle in current method
	                                      try{} block always come with another block, can be catch(){}, or finally{}
	                         - throws: declares that a method can throw exception, require caller
                                    to handle or further declare them.

            uncheck exception: runtime exception
                               EX: ArithmeticException (3/0), ArrayIndexOutOfBoundsException, ClassCastException, NPE

        Error: represent some problems that a reasonable application can not handle.


        throws vs throw
          throws: declare that a method can throw exceptions, require caller to handle
          throw: used to actually throw an instance of an exception

        Customized Exception:

            public class UserNotFoundException extends RuntimeException{
                public UserNotFoundException() {
                    super();
                }

                public UserNotFoundException(String msg) {
                    super(msg + "this is a customized exception.");
                }
            }

         finally:
            finally block always execute.

         multiple catch: (it's a new feature after Java7)
            try {
                FileInputStream input = new FileInputStream("/");
            } catch(UserNotFoundException | NullPointerException | ArithmeticException | FileNotFoundException ex) {
                System.out.println(ex);
            }

          try with resources: try(){} catch(){}
                //  AutoCloseable, close() => this is close the resource
                //  do not need to write finally block to close the resource
                try (
                    BufferedReader br = new BufferedReader(new FileReader("/"))
                )
                {
                    br.readLine();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

    2. Generics
        - parameterized type
        - doesn't work with primitive types
        - allow corresponding wrapper classes to make it work
            Data type erase:
                => Compile java code => class file
                => generic parameter type Cast to object, if any data type is not inherited
                   from the object which means cannot cast to object class.
        class: (see below code)

        method: (see below code)

        <?>, <A extends B>, <B super A>


    3. Java8 new feature
        Lambda Expression: (argument) -> {body}
            lambda expression implements the abstract method of the functional interface.

        Functional Interface: exactly one abstract method, annotation '@FunctionalInterface' is optional
                              many default concrete method, static method

        Predefined Functional Interface
            Predicate
                public Boolean test(T t);
            Function
               public R apply(T t);
            Consumer
                public void accept(T t);
            Supplier
                public R get();
            BiPredicate
                public boolean test(T t, U u);
            BiFunction<R, T, U>
                public R apply(T t, U u);

     4. Optional
            NullPointerException (NPE)
            empty(), of(), ofNullable(), isPresent(), isEmpty(), orElse(), orElseThrow()
    */
    public static void main(String[] args) throws Throwable {

        // Optional NPE
//        String[] arr = new String[3];
//        String[] array = new String[]{"abc", "aa", "dd"};
//
//        if (arr[0] == null) {
//            System.out.println("this is null");
//            throw new IllegalArgumentException("this is null");
//        } else {
//            System.out.println(arr[0]);
//        }

//        Optional op = Optional.ofNullable(arr[0]);
//        System.out.println(op.orElse("this is null"));
//        op.orElseThrow(() -> {
//            return new IllegalArgumentException("this is null");
//        });

        // lambda expression and predefined functional interface
        BiFunction<Integer, Integer, Integer> myAdd = (a, b) -> {return a + b;}; // or 'a + b;'
        System.out.println(myAdd.apply(11, 22)); // 33
        Function<Integer, Integer> addMore = a -> a + 2;
        System.out.println(addMore.apply(33)); // 35

        // Generics method
        Integer[] intArr = new Integer[]{11, 22, 33, 44, 55};
        String[] strArr = new String[]{"aa", "bb", "cc", "dd"};
        System.out.println(getFirst(intArr)); // 11
        System.out.println(getFirst(strArr)); // aa

        // int is not inherited from object
        // int[] is inherited from object
        int[][] arr2D = new int[][]{{1,2},{3,4,5},{6,7}};
        System.out.println(Arrays.toString(getFirst(arr2D))); // [1, 2]

        // Generics class
        Node<Integer, String> node1 = new Node<>(11, "blue");
        Node<String, Character> node2 = new Node<>("Meiling", 'M');

        // Exception => try catch block
        try{
            fileReader("/");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e); // throw an instance of the exception
        } finally {
            System.out.println("this is finally block"); // finally block
        }

        // customized exception
        throw new UserNotFoundException("something ...");
    }

    // Functional Interface
    @FunctionalInterface // annotation is optional
    interface MyFunctionalInterface{
        public void draw(); // abstract method

        public default void drawLine() { // default concrete method
            System.out.println("draw line");
        }

        public default int drawCircle() { // default concrete method
            return 0;
        }

        public static void drawSquare() {
            System.out.println("draw square"); // static method
        }
    }


    // Generics method
    public static <E> E getFirst(E[] arr) {
        return arr[0];
    }

    // Exception => throws
    public static void fileReader(String path) throws FileNotFoundException { // declare that a method can throw exception

    }
}
// Generics class
class Node<K, V>{
    K key;
    V value;
    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
