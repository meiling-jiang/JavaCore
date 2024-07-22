package org.example.day2;

import java.util.*;

public class Day2note {
    /*
    1. Java, compile once, run anywhere
    .java file  -> .class file (byteCode) -> machine code

    2. JVM Architecture

    CLass loader
                           CLass loader
        +------------------------------------------------+
        |  ----------      ------------       ---------  |
        |   Loading            Link              Init    |
        |  ----------      ------------       ---------  |
        +------------------------------------------------+
                                |
                                V
        Bootstrap class Loader,   Extension class loader,       application class loader
        java.lang.*;              $JAVA_HOME/jre/lib/ext,    load files present on the classPath
        java.util.*;

        parents delegation model
        app -> ext -> boot
            <-      <-

    Runtime data area
                                      Runtime data area
        +-------------------------------------------------------------------------------------+
        |  ------------     -----------       ----------   -----------     ----------------   |
        |    Method            Heap             Stack        PC             Native Method     |
        |    area              area             area         Register          Stack          |
        |  ------------     -----------       ----------   -----------     ----------------   |
        +-------------------------------------------------------------------------------------+
                                            |
                                            V
        Method area: all class level data, run-time constant pool, field, and method data, ...
        Heap area: when using new keyword to create object, this object will be in heap area
        Stack area: stack frame, (call stack)
        PC Register: multiple threads, pc register -> jvm instruction
        Native method stack: method written in other language

    Execution Engine
                                Execution Engine
        +--------------------------------------------------------------+
        |  ------------      --------------       -------------------- |
        |   Interpreter       JIT Compiler          Garbage Collector  |
        |  ------------      --------------       -------------------- |
        +--------------------------------------------------------------+
                                    |
                                    V
        Interpreter: interpret class file line by line, EX: for loop
        JIT compiler: hotspot
        Garbage Collector:
            phrases: mark, sweep, (compact)
            types:
                serial GC,       one thread, -> "stop the world"
                parallel GC,     multiple threads
                G1GC, (big memory space ( >= 4GB))chunks
                    eden region, survivor region, tenured region, humongous region, available region
                CMS: deprecated at java 9, removed at java 14 in favor of G1GC

      JNI (native method interface): bridge
                +-------------------------+
                | Native Method Interface |
                +-------------------------+
                            |
                            V

      Native Method Libraries
                +----------------------+
                |   Native Method      |
                |   Libraries          |
                +----------------------+

    3. reserved words (53)
    reserved literals (3)
        true/false/null
    keywords(50)
        unused keywords (2)
            goto/ const
        used keywords (48)

    reserved words for data types: byte, short, int, long, float, double, char, boolean
    flow control: if, else, switch, case, default, for, do, while, break, continue, return
    modifiers: public, private, protected, static, final, abstract, synchronized, native, strictfp, transient, volatile
    exception handling: try, catch, finally, throw, throws, assert
    class: class, package, import, extends, implements, interface
    object: new, instanceof, super, this

    'final'
         final variable: can't modify it, initialize
         final int a = 1;
         int b = 1;
         a = b; // error
         System.out.println(a);

         can't reassign reference:
         final List<Integer> list = new ArrayList<>();
//        list = new ArrayList<>(); // error because final keyword can't reassign reference
         list.add(3);
         System.out.println(list); // [3] it works because just add some values

         final method: can't override
         class A{
            final public void methodA() {
                System.out.println("hello!");
            }
          }
        class B extends A{
            // 'methodA()' cannot override 'methodA()' because overridden method is final
            @Override
            public void methodA() {
                super.methodA();
            }
        }

         final class: can't extend
         final class A{
            public void methodA() {
                System.out.println("hello!");
            }
        }
        // compile error
        class B extends A{
            @Override
            public void methodA() {
                super.methodA();
            }
        }

        how to create immutable class (EX code: MyImmutableClass see below)
        - final class
        - private field
        - no setter, only getter
        - deep copy of collection

        final: is a keyword, use to modify method, variable, class....
        finally: is try/catch block, cleanup code, such as closing resources
        finalize: is a method, invoke GC, used to perform cleanup operations before an object is garbage collected.

        static
            static => belong to class, class level, can't access non-static method
            non-static => belong to object, can access static method
            - blocks: only run once when JVM start, initialize variable
            - variable: can only access by class name
            - method: can only access by class name
            - classes:

        static class: Map.Entry<Integer, Integer>

        implements: one class can implement multiple interface, EX: LinkedList implements List and Deque
        extends: only one

    4, OOP
        abstraction, encapsulation, inheritance, polymorphism
        abstraction
            abstract class, interface
            abstract class: concrete methods which can implements the methods,
                            abstract methods
		                    final and non-final, static and non-static
            interface: only abstract methods (after Java8 may contain concrete methods)
	                   only have static and final variable
	                   when define an ability object, use interface

        encapsulation
            declare variable as private, setter and getter

        inheritance
            extends, implements

        polymorphism
            overload, compile time polymorphism
            override, runtime polymorphism, static method can't override
    */

    static int a = 10;
    static int instance;
    static {
        System.out.println("static block");
        instance = 10;
    }

    public static void method2() {
        System.out.println("hello method2");
    }
    public static void main(String[] args) {
        Day2note.method2();
        method2(); // in Day2note class
//        method3(); Non-static method 'method3()' cannot be referenced from a static context
        Day2note obj = new Day2note();
        obj.method3();

        Day2note.Node node = new Day2note.Node();
        Map<Integer, Integer> map = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {

        }

//        A ob = new C(); // I'm in class B
//        B ob = new C(); // I'm in class B
//        A ob = new A(); // I'm in class A
//        C ob = new A(); // error, child class can convert parent class, parent class can never convert to child class
//        ob.method();
    }

    final class StudentImmutable{
        private final int id;
        private final String firstName;
        private final String lastName;
        private final List<Course> course;
        StudentImmutable(int id, String firstName, String lastName, List<Course> course) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.course = deepCopy(course);
        }
        public int getId() {
            return id;
        }
        public String getFirstName() {
            return firstName;
        }
        public String getLastName() {
            return lastName;
        }
        public List<Course> getCourse() {
            List<Course> newCourse = deepCopy(this.course);
            return newCourse;
        }
        private List<Course> deepCopy(List<Course> course) {
            List<Course> newCourse = new ArrayList<>();
            for(Course i : course) {
                newCourse.add(i);
            }
            return newCourse;
        }
    }
    class Course{
        String courseName;
    }

    static class Node{

    }
    public void method3() {
        System.out.println("hi, method3");
    }

    final class MyImmutableClass{
        private final int id;
        private final List<Integer> list;
        MyImmutableClass(int id, List<Integer> list) {
            this.id = id;
            this.list = deepCopy(list);
        }
        public int getId() {
            return id;
        }
        public List<Integer> getList() {
            List<Integer> newList = deepCopy(this.list);
            return newList;
        }

        private List<Integer> deepCopy(List<Integer> list) {
            List<Integer> newList = new ArrayList<>();
            for(Integer i : list) {
                newList.add(i);
            }
            return newList;
        }
    }

}

class A{
    public void method(){
        System.out.println("I'm in class A");
    }

    public void method(int a){ // overload
        System.out.println(a);
    }
}
class B extends A{
    @Override
    public void method() {
        System.out.println("I'm in class B");
    }
}

class C extends B{

}

//class A {
//    public void method() {
//        System.out.println(Day2Note.instance);
//        Day2Note.method2();
//    }
//}


