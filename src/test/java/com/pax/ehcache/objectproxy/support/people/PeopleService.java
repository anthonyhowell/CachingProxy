package com.pax.ehcache.objectproxy.support.people;

import java.util.List;
import java.util.Optional;

public interface PeopleService {

    Optional<Person> findById(Long id);

    Optional<List<Person>> findByFirstName(String firstName);

    Optional<List<Person>> findByLastName(String lastName);

    Optional<List<Person>> findWhereOlderThan(int age);

    Optional<List<Person>> findWhereYoungerThan(int age);

}
