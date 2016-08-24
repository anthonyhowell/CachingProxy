package com.pax.ehcache.objectproxy;

import com.pax.ehcache.objectproxy.exception.ObjectProxyException;
import com.pax.ehcache.objectproxy.support.people.DefaultPeopleService;
import com.pax.ehcache.objectproxy.support.people.PeopleService;
import com.pax.ehcache.objectproxy.support.people.Person;
import com.pax.ehcache.objectproxy.support.people.SamplePeopleStore;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

public class ProxyAllInterfacesTest {

    @Test
    public void run() throws ObjectProxyException, NoSuchMethodException {

        // Default cache configuration
        CacheConfiguration defaultCacheConfig = new CacheConfiguration("my_default", 5000)
                .timeToLiveSeconds(120)
                .timeToIdleSeconds(60)
                .eternal(false);

        CacheProxyFactory proxyFactory = new CacheProxyFactory(CacheManager.create(), defaultCacheConfig);
        SamplePeopleStore peopleStore = new SamplePeopleStore();

        // Cache configuration for a single method
        ProxyConfig proxyConfig = new ProxyConfig()
                .target(new DefaultPeopleService(peopleStore))
                .proxyAllInterfaces()
                .cacheConfiguration(new CacheConfiguration("cache_01", 5000).timeToLiveSeconds(120).timeToIdleSeconds(60));

        PeopleService peopleService = proxyFactory.create(new DefaultPeopleService(peopleStore), proxyConfig);

        // Since we used proxyAllInterfaces(), every method will attempt to return a cached response

        Optional<Person> result = peopleService.findById(10L);          // Fall through
        Optional<Person> result2 = peopleService.findById(10L);         // Cached response
        Optional<Person> result3 = peopleService.findById(10L);         // Cached response

        Optional<List<Person>> result4 = peopleService.findByLastName("Pitt");  // Fall through
        Optional<List<Person>> result5 = peopleService.findByLastName("Pitt");  // Cached Response
        Optional<List<Person>> result6 = peopleService.findByLastName("Pitt");  // Cached response
    }

}
