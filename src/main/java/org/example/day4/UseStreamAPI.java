package org.example.day4;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class Student{
    private String name;
    private int age;
    private double score;

    public Student(String name, int age, double score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getScore() {
        return score;
    }
    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age + ", Score: " + score;
    }
}

public class UseStreamAPI {
    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();
        list.add(new Student("John", 20, 69.5));
        list.add(new Student("Amy", 18, 93.5));
        list.add(new Student("Lily", 21, 78.0));
        list.add(new Student("Don", 18, 59.0));
        list.add(new Student("Anna", 20, 84.5));

//        use stream api to find all the students’ name starting with ‘A’
        List<String> namesStartWithA = list.stream()
                .map(Student::getName)
                .filter(name -> name.startsWith("A"))
                .collect(Collectors.toList());
        System.out.println("Students' names starting with 'A': " + namesStartWithA);

//        use stream api to get the sum of all the students score
        double totalScore = list.stream()
                .mapToDouble(Student::getScore)
                .sum();
        System.out.println("Total score of all students: " + totalScore);

//        use stream api to find all the students whose sore >= 60
        List<Student> scoreAbove60 = list.stream()
                .filter(student -> student.getScore() >= 60)
                .collect(Collectors.toList());
        System.out.println("Students with score >= 60: " + scoreAbove60);

//        use stream api to retrieve all students name
        List<String> studentNames = list.stream()
                .map(Student::getName)
                .collect(Collectors.toList());
        System.out.println("Students' names: " + studentNames);

//        use stream api to count the frequency of each age
        Map<Integer, Long> ageFrequency = list.stream()
                .map(Student::getAge)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println("Frequency of each age: " + ageFrequency);
    }
}

