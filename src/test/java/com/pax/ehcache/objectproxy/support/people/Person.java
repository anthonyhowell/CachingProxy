package com.pax.ehcache.objectproxy.support.people;

import com.sun.deploy.util.StringUtils;

import java.util.Arrays;
import java.util.List;

public class Person {

    private Long id;
    private String firstName;
    private String lastName;
    private int age;


    public Person(Long id, String firstName, String lastName, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        List<String> bits = Arrays.asList(
                String.valueOf(getId()),
                getFirstName(),
                getLastName(),
                String.valueOf(getAge())
        );

        return StringUtils.join(bits, ":");
    }
}
