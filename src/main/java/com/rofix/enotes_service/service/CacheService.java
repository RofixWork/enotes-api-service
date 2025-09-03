package com.rofix.enotes_service.service;

import org.springframework.cache.Cache;

import java.util.Collection;

public interface CacheService {
    Collection<String> getAllCache();
    Cache  getCache(String cacheName);
    void deleteAllCache();
}
