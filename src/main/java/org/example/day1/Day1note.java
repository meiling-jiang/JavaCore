package org.example.day1;
import java.util.*;

public class Day1note {
    public static void main(String[] args) {
        /*
    1. maven
        folder structure
        types of repository: local(.m2), central(online search), remote(ask for password to get)
        maven build lifecycle: clean, validate, compile ...
        command line: mvn clean, mvn install, mvn verify ...

    2. git: distribute version control

    3. eight basic data type:
         primitive type: short(16), int(32), long(64), float(32), double(64), boolean(1), byte(8), char(16)
         wrapper type: Short, Integer, Long, Float, Double, Boolean, Byte, Character
         auto boxing: primitive => wrapper
         unboxing: wrapper => primitive

        int a = 1;
        Integer b = 2;
        System.out.println(a + b); // wrapper => primitive
        List<Integer> list = new ArrayList<>();
        list.add(a); // primitive => wrapper
        list.add(b);
        System.out.println(list);

    4. String/ StringBuilder/ StringBuffer
        * String immutable class

        String s = "abc";
        appendStr(s);
        System.out.println(s); //abc

        * StringBuilder mutable class, Not synchronized, not thread-safe.
          Faster, no synchronization overhead.
          For better performance in single-threaded environments

        StringBuilder builder = new StringBuilder("abc");
        appendSb(builder);
        System.out.println(builder.toString()); //abchi

        * StringBuffer mutable class, Synchronized, thread-safe.
          Slower, due to synchronization overhead.
          When thread safety is necessary in multithreaded environments.

    5. String Intern Pool
        The intern() method checks if the string "abc" is already present in the string pool.
        Since the string literal "abc" is already in the string pool (referred to by c and d),
        e will point to this interned string in the string pool.

        String a = new String("abc"); // new object is in heap area
        String b = new String("abc"); b points to another new String object in the heap
        String c = "abc"; // creates a string literal "abc" and stores it in the string pool. The reference c points to this string in the pool.
        String d = "abc"; // checks the string pool and finds that the literal "abc" already exists. It then points the reference d to the same string in the pool that c points to.
        String e = b.intern(); // e points to the interned string in the string pool (same as c and d)

        System.out.println(a == b); // F because c and d point to different objects
        System.out.println(a == c); // F
        System.out.println(c == d); // T because c and d point to the same object
        System.out.println(d == e); // T because d and e point to the same interned string in the string pool

       Integer Pool

        Integer a = 10;
        Integer b = 10;
        Integer c = new Integer(10);
        Integer d = Integer.valueOf(10);

        System.out.println(a == b); // T
        System.out.println(a == c); // F
        System.out.println(a == d); // T

        // Integer Pool Range: -128 => 127
        Integer x = 128;
        Integer y = 128;
        System.out.println(x == y); // F

     6. equals and hashcode

        Node n1 = new Node(1, 1);
        Node n2 = new Node(1, 1);
        System.out.println(n1 == n2); // F '==' means to compare the address

        // F if not override equals method in Node class, will trigger/call Object equals method
        // T if override equals and hashcode methods
        System.out.println(n1.equals(n2));

     7. data structures

        Collection (interface) vs Collections (class)
        Collection: Queue, Set, List
        LinkedList: each node can be located anywhere in the heap memory, and nodes are linked together via pointers.
        ArrayList: all elements are stored in adjacent memory locations in the heap memory.

        Map
            HashMap: not thread safe, allow one null key
            When a HashMap is created, it initializes an array of default capacity (usually 16).
             Key --------> hashcode --------> %, index = size of Map(16)
             "abc"           33                 33 % 16 = 1   so "abc" stores in index 1
             "xyz"           49                 49 % 16 = 1   so "xyz" stores in index 1
             |0|node1|2|3|...|14|15|
                  |
                  V
                node2

              use ArrayList to save each bucket, then each bucket use LinkedList to store each node
              after Java8 if len of LinkedList > 8, => RedBlack tree

            HashTable: thread safe, doesn't allow null key
            ConcurrentHashMap: thread safe, bucket level lock

         Stack FILO (thread safe) vs Queue FIFO

     8. Comparator/ Comparable
*/
//        Set<Node> set = new TreeSet<>(new MyComparator());
        Set<Node> set = new TreeSet<>((a, b) -> Integer.compare(a.x, b.x)); // Lambda expression
        set.add(new Node(1, 1));
        set.add(new Node(2, 2));
        set.add(new Node(3, 3));
        set.add(new Node(4, 4));

        System.out.println(set.stream().findFirst().get().x);
    }

    static class MyComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            return Integer.compare(o1.x, o2.x); // ascending order
        }
    }
    static class Node implements Comparable<Node>{
        int x;
        int y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return x == node.x && y == node.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(o.x, this.x); //descending order
        }
    }
    public static void appendStr(String s) {
        s = s + "hello";
    }

    public static void appendSb(StringBuilder sb) {
        sb.append("hi");
    }
}
