package com.pax.ehcache.objectproxy;

import com.pax.ehcache.objectproxy.support.people.SamplePeopleStore;
import org.junit.Test;

public class CacheTest {

    @Test
    public void run() throws NoSuchMethodException {
        SamplePeopleStore peopleStore = new SamplePeopleStore();
//        CacheProxyFactory proxyFactory = new CacheProxyFactory();

//        CacheProxyFactory.getInstance()
//                .addCacheConfiguration(
//                        PeopleService.class,
//                        new CacheConfiguration("people", 10).maxEntriesInCache(10));

//        PeopleService peopleService = new DefaultPeopleService(peopleStore);

        // Create a cache proxy for every method, for every interface declared by DefaultPeopleService

//        ProxyConfig proxyConfig = new ProxyConfig()
//                .target(new DefaultPeopleService(peopleStore))
//                .proxyAllInterfaces(true);
//
//        PeopleService peopleService =  proxyFactory.create(proxyConfig);
//


//        PeopleService peopleService = CacheProxyFactory.create(new DefaultPeopleService(peopleStore));
//
//        // Create a cache proxy for every method, on the specified interface(s), for DefaultPeopleService
//        PeopleService peopleService1 = CacheProxyFactory.create(new Class<?>[] { PeopleService.class }, new DefaultPeopleService(peopleStore));
//
//        // Create a cache proxy for the specified method(s), on the specified interface(s), for DefaultPeopleService
//        PeopleService peopleService3 = CacheProxyFactory.create(
//            new Class<?>[] { PeopleService.class },
//            new Method[] {
//                PeopleService.class.getMethod("findById", Long.class),
//                PeopleService.class.getMethod("findByFirstName", String.class)
//            },
//            new DefaultPeopleService(peopleStore),
//            new CacheConfiguration("{CACHE_NAME}", 5000)
//                    .timeToLiveSeconds(120)
//                    .timeToIdleSeconds(60)
//        );

//        Optional<List<Person>> result = peopleService.findWhereOlderThan(5);
//        Optional<List<Person>> result2 = peopleService.findWhereYoungerThan(5);
//        Optional<Person> result3 = peopleService.findById(5L);
//        Optional<List<Person>> result4 = peopleService.findByFirstName("Brad");
//        Optional<List<Person>> result5 = peopleService.findByLastName("Pitt");

        return;
    }

}
