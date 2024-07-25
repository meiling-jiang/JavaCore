package org.example.day6;

public class Day6note {
    Department dep = Department.builder().setName("Java").setId(1).build();

    PhoneFactory factory = new PhoneFactory();
    Phone iphone = factory.getPhone("iphone");
    Phone sony = factory.getPhone("sony");
}
