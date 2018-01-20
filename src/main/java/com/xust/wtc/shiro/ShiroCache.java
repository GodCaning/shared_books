package com.xust.wtc.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * shiro cache
 * Created by Spirit on 2017/11/26.
 */
@SuppressWarnings("unchecked")
public class ShiroCache<K, V> implements Cache<K, V> {

    private static final String CACHE_PREFIX = "WTC_SHIRO_CACHE:";

    private String cacheKey;

    private RedisTemplate<String, Object> redisTemplate;

    private long globExpire = 30;

    public ShiroCache() {}

    public ShiroCache(String name, RedisTemplate redisTemplate) {
        this.cacheKey = CACHE_PREFIX + name + ":";
        this.redisTemplate = redisTemplate;
    }

    @Override
    public V get(K k) throws CacheException {
        redisTemplate.boundValueOps((String) getCacheKey(k)).expire(globExpire, TimeUnit.MINUTES);
        return (V) redisTemplate.boundValueOps((String) getCacheKey(k)).get();
    }

    @Override
    public V put(K k, V v) throws CacheException {
        V old = get(k);
        redisTemplate.boundValueOps((String) getCacheKey(k)).set(v);
        return old;
    }

    @Override
    public V remove(K k) throws CacheException {
        V old = get(k);
        redisTemplate.delete((String) getCacheKey(k));
        return old;
    }

    @Override
    public void clear() throws CacheException {
        redisTemplate.delete((Collection<String>) keys());
    }

    @Override
    public int size() {
        return keys().size();
    }

    @Override
    public Set<K> keys() {
        return (Set<K>) redisTemplate.keys((String) getCacheKey("*"));
    }

    @Override
    public Collection<V> values() {
        Set<K> set = keys();
        List<V> list = new ArrayList<>();
        for (K s : set) {
            list.add(get(s));
        }
        return list;
    }

    private K getCacheKey(Object k) {
        return (K) (this.cacheKey + k);
    }
}
