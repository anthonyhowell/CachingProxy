package com.pax.ehcache.objectproxy.support.people;

import java.util.Optional;

public interface UserService {

    Optional<Person> findByUsername(String username);

    Optional<Person> findByEmail(String email);

}
