package com.rofix.enotes_service.controller;

import com.rofix.enotes_service.endpoint.CacheEndpoint;
import com.rofix.enotes_service.service.CacheService;
import com.rofix.enotes_service.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class CacheController implements CacheEndpoint {
    private final CacheService cacheService;

    @Override
    public ResponseEntity<?> getAllCache() {
        Collection<String> allCache =cacheService.getAllCache();
        return ResponseUtils.createSuccessResponse("Get All Cache", allCache);
    }

    @Override
    public ResponseEntity<?> getCache(String cacheName) {
        Cache cache = cacheService.getCache(cacheName);
        return ResponseUtils.createSuccessResponse("Get Cache", cache);
    }

    @Override
    public ResponseEntity<?> deleteAllCache() {
        cacheService.deleteAllCache();
        return ResponseUtils.createSuccessResponse("Clear Cache");
    }
}
