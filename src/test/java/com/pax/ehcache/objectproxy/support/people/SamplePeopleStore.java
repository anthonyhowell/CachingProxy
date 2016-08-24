package com.pax.ehcache.objectproxy.support.people;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Sample person store.
 * Generates a sizable set of data for test usage.
 */
public class SamplePeopleStore {

    private List<String> firstNames = Arrays.asList(
            "Brad", "Robert", "George", "Johnny", "Matt", "Paul", "Ben", "Tom", "Bradley", "Mark", "Jonah",
            "Jennifer", "Margot", "Scarlett", "Emma", "Gal", "Charlize", "Anna", "Daisey", "Kristen", "Felicity"
    );

    private List<String> lastNames = Arrays.asList(
            "Pitt", "Downy", "Clooney", "Depp", "Damon", "Rudd", "Affleck", "Hardy", "Cooper", "Wahlberg", "Hill",
            "Lawrence", "Robbie", "Johansson", "Stone", "Gadot", "Theron", "Kendrick", "Ridley", "Wiig", "Jones"
    );

    private Map<Long, Person> store = new HashMap<>();

    public SamplePeopleStore() {
        for (int i = 1; i < 100; i++) {
            generatePerson(Long.valueOf(i));
        }
    }

    public Map<Long, Person> get() {
        return store;
    }

    public Person get(Long id) {
        return store.get(id);
    }

    private void generatePerson(Long id) {
        Random random = new Random();
        int minAge = 25;
        int maxAge = 65;

        String firstName = firstNames.get(random.nextInt(firstNames.size()));
        String lastName  = lastNames.get(random.nextInt(lastNames.size()));
        int age = random.nextInt((maxAge - minAge) + 1) + minAge;

        Person person = new Person(id, firstName, lastName, age);

        if (!personExists(person)) {
            store.put(id, person);
        }
    }

    private boolean personExists(Person person) {
        for (Map.Entry<Long, Person> p : store.entrySet()) {
            if (p.getValue().getFirstName().equals(person.getFirstName())
                    && p.getValue().getLastName().equals(person.getLastName())) {
                return true;
            }
        }
        return false;
    }


}
