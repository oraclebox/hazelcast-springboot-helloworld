package me.oraclebox

import com.hazelcast.cache.ICache
import com.hazelcast.config.Config
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling

import javax.cache.Cache
import javax.cache.CacheManager
import javax.cache.Caching
import javax.cache.configuration.MutableConfiguration
import javax.cache.expiry.AccessedExpiryPolicy
import javax.cache.expiry.Duration

@EnableScheduling
@SpringBootApplication
class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // Hazelcast config
    @Bean
    public Config config() {
        return new Config(); // Set up any non-default config here
    }

    @Bean(name = 'AccessedExpiryCache')
    ICache<String, Map> iCache() {
        CacheManager manager = Caching.getCachingProvider().getCacheManager();
        MutableConfiguration<String, Map> configuration = new MutableConfiguration<String, Map>();
        //configuration.setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));
        Cache<String, Map> myCache = manager.createCache("myCache", configuration);
        //ICache extends Cache interface, provides more functionality
        ICache<String, Map> icache = myCache.unwrap(ICache.class);
        return icache;
    }



}
