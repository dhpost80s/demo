package org.example.basic;

public class Person {
    String name;
    int age;

    public Person() {
        System.out.println("default constructor");
    }


    public Person(String name) {
        this(name, 1);
        System.out.println("name constructor");
        name = name;
    }

    public Person(String name, int age) {
        System.out.println("name & age constructor");
        name = name;
        age = age;
        test();
    }

    public  void test() {

    }


}
