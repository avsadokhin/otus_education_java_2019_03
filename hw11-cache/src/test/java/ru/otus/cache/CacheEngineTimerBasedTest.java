package ru.otus.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheEngineTimerBasedTest {
    final int size = 10;
    final int lifeTimeMs = 1000;
    final int idleTimeMs = 10;
    CacheEngine<Long, CacheElement<Long, String>> cache;

    @BeforeEach
    void iniBefore() {
        cache = new CacheEngineTimerBased<>(size, lifeTimeMs, idleTimeMs);
    }

    @Test
    void get() {
    }

    @Test
    void put() {
    }

    @Test
    void getHitCount() {
    }

    @Test
    void getMissCount() {
    }

    @Test
    void dispose() {
    }
}