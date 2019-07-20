package ru.otus.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

class CacheEngineTimerBasedTest {
    final int size = 1000;
    CacheEngine<Integer, String> cache;


    @Test
    void getEternal() {
        cache = new CacheEngineTimerBased<>(size, 0, 0);
        IntStream.range(1, size + 1).forEach(value -> cache.put(value, value + " element"));

        IntStream.range(1, size).forEach(value -> Assertions.assertEquals(cache.get(value), value + " element"));
    }

    @Test
    void putEternal() {
        cache = new CacheEngineTimerBased<>(size, 0, 0);
        IntStream.range(1, size + 1).forEach(value -> cache.put(value, value + " element"));

        IntStream.range(1, size).forEach(value -> Assertions.assertEquals(cache.get(value), value + " element"));
    }

    @Test
    void getHitCount() {

        cache = new CacheEngineTimerBased<>(size, 1000, 0);
        IntStream.range(1, size + 1).forEach(value -> cache.put(value, value + " element"));

        IntStream.range(1, size + 1).forEach(value -> cache.get(value));
        int hits = (int) cache.getHitCount();
        Assertions.assertTrue(hits != 0);


    }

    @Test
    void getMissCount() {
        cache = new CacheEngineTimerBased<>(size, 1000, 0);
        IntStream.range(1, size + 1).forEach(value -> cache.put(value, value + " element"));

        IntStream.range(1, size + 1).forEach(value -> cache.get(value));
        Assertions.assertEquals(0, cache.getMissCount());

        try {
            Thread.sleep(1000 + 1000);
            IntStream.range(1, size + 1).forEach(value -> cache.get(value));
            Assertions.assertTrue(cache.getMissCount() > 0);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


}