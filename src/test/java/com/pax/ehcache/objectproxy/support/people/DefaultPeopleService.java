package com.pax.ehcache.objectproxy.support.people;

import net.sf.ehcache.config.CacheConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class DefaultPeopleService implements PeopleService, UserService {

    private static final Logger LOG = LoggerFactory.getLogger(CacheConfiguration.class.getName());

    private SamplePeopleStore store;


    public DefaultPeopleService(SamplePeopleStore store) {
        this.store = store;
    }

    @Override
    public Optional<Person> findById(Long id) {
        logCall(Arrays.asList(id));
        simulateProcessingTime();
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<List<Person>> findByFirstName(String firstName) {
        logCall(Arrays.asList(firstName));
        simulateProcessingTime();

        List<Person> people = store.get().entrySet().stream()
                .filter(p -> p.getValue().getFirstName().equals(firstName))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        return Optional.of(people);
    }

    @Override
    public Optional<List<Person>> findByLastName(String lastName) {
        logCall(Arrays.asList(lastName));

        simulateProcessingTime();

        List<Person> people = store.get().entrySet().stream()
                .filter(p -> p.getValue().getLastName().equals(lastName))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        return Optional.of(people);
    }

    @Override
    public Optional<List<Person>> findWhereOlderThan(int age) {
        logCall(Arrays.asList(age));
        simulateProcessingTime();

        List<Person> people = store.get().entrySet().stream()
                .filter(p -> p.getValue().getAge() > age)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        return Optional.of(people);
    }

    @Override
    public Optional<List<Person>> findWhereYoungerThan(int age) {
        logCall(Arrays.asList(age));
        simulateProcessingTime();

        List<Person> people = store.get().entrySet().stream()
                .filter(p -> p.getValue().getAge() < age)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        return Optional.of(people);
    }


    @Override
    public Optional<Person> findByUsername(String username) {
        return Optional.ofNullable(null);
    }

    @Override
    public Optional<Person> findByEmail(String email) {
        return Optional.ofNullable(null);
    }

    private void simulateProcessingTime() {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    private void logCall(List<Object> args) {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        LOG.debug("{} : {} : {}", this.getClass().getSimpleName(), stack[2].getMethodName(), args);
    }

}
