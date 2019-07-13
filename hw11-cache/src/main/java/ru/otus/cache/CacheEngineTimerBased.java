package ru.otus.cache;

public class CacheEngineTimerBased <K, V> implements CacheEngine<K,V>{
    public CacheEngineTimerBased() {
    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public void put(K key, V value) {

    }

    @Override
    public long getHitCount() {
        return 0;
    }

    @Override
    public long getMissCount() {
        return 0;
    }

    @Override
    public void dispose() {

    }
}
