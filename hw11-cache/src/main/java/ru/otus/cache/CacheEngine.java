package ru.otus.cache;

import java.util.function.Function;

public interface CacheEngine<K, V> {
    V get(K key);

    V getAndPutNotExisted(K key, Function<K, V> valueFunction);

    void put(K key, V value);

    long getHitCount();

    long getMissCount();

    void dispose();

}
