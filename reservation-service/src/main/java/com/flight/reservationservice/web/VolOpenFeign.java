package com.flight.reservationservice.web;

import com.flight.reservationservice.models.Client;
import com.flight.reservationservice.models.Vol;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@FeignClient(name = "OFFREVOL-SERVICE")

public interface VolOpenFeign {

    Cache<Long, Vol> voltCache = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES) // Cache entries expire after 10 minutes
            .maximumSize(100) // Limit the cache size to 100 entries
            .build();

    @GetMapping("/vols")
    public List<Vol> findAll();

    @GetMapping("/vols/{id}")
    @CircuitBreaker(name="reservationServiceCB", fallbackMethod = "getDefaultVol")
    public default Vol findById(@PathVariable Long id){
        Vol fetchedVol = fetchVolFromService(id);
        Vol cachedVol = voltCache.getIfPresent(id);

        if (cachedVol == null) {
            voltCache.put(id, fetchedVol); // Store the client in cache
        }
        return fetchedVol;
    }

    @GetMapping("/vols/{id}")
    public Vol fetchVolFromService(@PathVariable Long id);

    @PutMapping("/{id}/decrement")
    @CircuitBreaker(name="reservationServiceCB" , fallbackMethod = "defaultDecrement")
    public void decrement(@PathVariable Long id);

    @PutMapping("/{id}/increment")
    @CircuitBreaker(name="reservationServiceCB" , fallbackMethod = "defaultIncrement")
    public void increment(@PathVariable Long id);

    @GetMapping("/AdminChercherVol")
    @CircuitBreaker(name="reservationServiceCB", fallbackMethod = "getDefaultVols")
    public List<Vol> chercherVolFlexible(@RequestParam(required = false) String villeDepart,
                                         @RequestParam(required = false) String villeDestination,
                                         @RequestParam(required = false) Integer day,
                                         @RequestParam(required = false) Integer month,
                                         @RequestParam(required = false) Integer year);

    public default Vol getDefaultVol(Long id, Throwable t) {

        System.out.println("**********************inside getDefaultVol*********");


        if(voltCache.getIfPresent(id) != null) {
            return voltCache.getIfPresent(id);
        }

        return Vol.builder().idVol(id).placesDisponibles(0).statut("provesoir").build();
    }

    public default List<Vol> getDefaultVols(String villeDepart,String villeDestination,Integer day,Integer month,Integer year,Throwable t){
        List<Vol> vols = new ArrayList<>();
        return vols;
    }

    public default void defaultDecrement(Long id, Throwable t) {
        System.out.println("*************************************************" +
                "Fallback triggered for decrement. Error: " + t.getMessage());
    }

    public default void defaultIncrement(Long id, Throwable t) {
        System.out.println("*************************************************" +
                "Fallback triggered for increment. Error: " + t.getMessage());
        throw new RuntimeException("Fallback triggered for increment. Original error: " + t.getMessage());

    }
}
