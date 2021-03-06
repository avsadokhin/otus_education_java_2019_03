package ru.otus.cache;

import java.lang.ref.SoftReference;
import java.util.*;
import java.util.function.Function;

public class CacheEngineTimerBased<K, V> implements CacheEngine<K, V> {

    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;

    private final Map<K, SoftReference<CacheElement<K, V>>> softReferenceMap = new LinkedHashMap<>();
    private final Timer timer = new Timer();

    private long hitCount;
    private long missCount;

    public CacheEngineTimerBased(int maxElements, long lifeTimeMs, long idleTimeMs) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.idleTimeMs = lifeTimeMs > 0 ? 0 : idleTimeMs > 0 ? idleTimeMs : 0;
        this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0;
        this.hitCount = 0;
        this.missCount = 0;
    }

    @Override
    public V get(K key) {
        SoftReference<CacheElement<K, V>> elementSoftReference = softReferenceMap.get(key);
        CacheElement<K, V> element = null;

        if (elementSoftReference != null && (elementSoftReference.get() != null)) {
            element = elementSoftReference.get();

            element.setAccessed();
            hitCount++;
        } else {

            softReferenceMap.remove(key);
            missCount++;
            return null;
        }

        return element.getValue();

    }


    @Override
    public void put(K key, V value) {
        CacheElement<K, V> element = new CacheElement<>(key, value);
        if (softReferenceMap.size() == maxElements) {
            K firstKey = softReferenceMap.keySet().iterator().next();
            softReferenceMap.remove(firstKey);
        }
        softReferenceMap.put(key, new SoftReference<>(element));

        if (!isEternal) {
            if (lifeTimeMs != 0) {
                TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
                timer.schedule(lifeTimerTask, lifeTimeMs);
            }
            if (idleTimeMs != 0) {
                TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
                timer.schedule(idleTimerTask, idleTimeMs, idleTimeMs);
            }
        }
    }

    @Override
    public long getHitCount() {
        return this.hitCount;
    }

    @Override
    public long getMissCount() {
        return this.missCount;
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    private TimerTask getTimerTask(final K key, Function<CacheElement<K, V>, Long> timeFunction) {
        return new TimerTask() {

            @Override
            public void run() {
                SoftReference<CacheElement<K, V>> softReferenceElement = softReferenceMap.get(key);

                CacheElement<K, V> element = softReferenceMap.get(key).get();

                if (softReferenceElement == null ||
                        (element == null || (timeFunction.apply(element) < System.currentTimeMillis()))
                ) {
                    softReferenceMap.remove(key);

                    this.cancel();
                }
            }
        };
    }

    @Override
    public V getAndPutNotExisted(K key, Function<K, V> valueFunction) {
        V value = null;
        if ((value = get(key)) == null) {
            value = valueFunction.apply(key);
            if (value != null) put(key, value);
        }
        return value;
    }
}
