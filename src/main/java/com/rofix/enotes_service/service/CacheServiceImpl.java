package com.rofix.enotes_service.service;

import com.rofix.enotes_service.exception.base.NotFoundException;
import com.rofix.enotes_service.utils.LoggerUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.event.Level;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {
    private final CacheManager cacheManager;

    @Override
    public Collection<String> getAllCache() {
        var cacheNames = cacheManager.getCacheNames();

        for (String cacheName : cacheNames) {
            Cache cache = cacheManager.getCache(cacheName);
            LoggerUtils.createLog(Level.INFO, getClass().getName(), "getCache()", "Cache: " + cache);
        }

        return cacheNames;
    }

    @Override
    public Cache getCache(String cacheName) {
        var cacheNames = cacheManager.getCacheNames();

        for (String name : cacheNames) {
            if(name.equals(cacheName))
            {
                Cache cache = cacheManager.getCache(cacheName);
                LoggerUtils.createLog(Level.INFO, getClass().getName(), "getCache()", "Cache: " + cacheName);
                return cache;
            }
        }
        throw new NotFoundException("Cache not found");
    }

    @Override
    public void deleteAllCache() {
        var cacheNames = cacheManager.getCacheNames();

        for (String cacheName : cacheNames) {
            Cache cache = cacheManager.getCache(cacheName);
            if(cache!=null) {
                LoggerUtils.createLog(Level.INFO, getClass().getName(), "getCache()", "Cache: " + cache);
                cache.clear();
            }
        }
    }
}
