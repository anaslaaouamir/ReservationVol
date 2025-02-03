package com.flight.reservationservice.web;

import com.flight.reservationservice.models.Client;
import com.flight.reservationservice.models.Vol;
import com.flight.reservationservice.security.FeignClientConfig;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.concurrent.TimeUnit;

@FeignClient(name = "CLIENT-SERVICE", configuration = FeignClientConfig.class)
public interface ClientOpenFeign {

    Cache<Long, Client> clientCache = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES) // Cache entries expire after 10 minutes
            .maximumSize(100) // Limit the cache size to 100 entries
            .build();

    @GetMapping("/clients")
    public List<Client> findAll();

    @GetMapping("/clients/{id}")
    @CircuitBreaker(name="reservationServiceCB", fallbackMethod = "getDefaultClient")
    default Client findById(@PathVariable Long id) {

        Client fetchedClient = fetchClientFromService(id);
        Client cachedClient = clientCache.getIfPresent(id);

        if (cachedClient == null) {
            clientCache.put(id, fetchedClient); // Store the client in cache
        }
        return fetchedClient;
    }

    // Direct call to the service
    @GetMapping("/clients/{id}")
    Client fetchClientFromService(@PathVariable Long id);

    // Fallback method for the circuit breaker
    default Client getDefaultClient(Long id, Throwable t) {

        System.out.println("**********************inside getDefaultClient*********");

        Client cachedClient = clientCache.getIfPresent(id);
        if (cachedClient != null) {
            return cachedClient;
        }
        // Return a default client object if no cache is available
        return Client.builder()
                .idClient(id)
                .nom("default")
                .email("default")
                .telephone("default")
                .build();
    }

}
