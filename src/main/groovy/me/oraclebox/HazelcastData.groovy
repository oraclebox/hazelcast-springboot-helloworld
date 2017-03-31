package me.oraclebox

import com.hazelcast.cache.ICache
import com.hazelcast.core.HazelcastInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

import javax.annotation.Resource
import javax.cache.expiry.AccessedExpiryPolicy
import javax.cache.expiry.CreatedExpiryPolicy
import javax.cache.expiry.Duration
import javax.cache.expiry.ExpiryPolicy

@Component
class HazelcastData {

    @Autowired
    HazelcastInstance hazelcastInstance;
    @Autowired
    @Qualifier("AccessedExpiryCache")
    ICache iCache;

    @Scheduled(fixedDelay=10000L)
    void generateData(){

        String key = UUID.randomUUID();


        // Put a value
        if(! iCache.containsKey(key)) {
            Map map = ['id':key, 'value':"value ${iCache.size()}".toString(), 'date':new Date()];
            iCache.put(key, map, CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE).create());
            println "--------- list ---------- "
            Iterator iterator = iCache.iterator();
            while (iterator.hasNext()) {
                def next = iterator.next();
                println "${next.key} ${next.value}";
            }

        }
        println "Get ${iCache.get(key)}";
        println "----------------------"

    }
}
