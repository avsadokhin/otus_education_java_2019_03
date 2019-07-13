package ru.otus.cache;

public interface CacheEngine<K, V> {
    V get(K key);

    void put(K key, V value);

    long getHitCount();

    long getMissCount();

    void dispose();

}
