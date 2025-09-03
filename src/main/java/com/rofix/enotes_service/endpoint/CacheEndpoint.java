package com.rofix.enotes_service.endpoint;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Caching", description = "Cache Endpoints")
@RequestMapping(value = "/api/v1/cache")
@Validated
public interface CacheEndpoint {
    @GetMapping
    ResponseEntity<?> getAllCache();

    @GetMapping("/{cache_name}")
    ResponseEntity<?> getCache(@NotBlank @PathVariable(name = "cache_name") String cacheName);

    @DeleteMapping
    ResponseEntity<?> deleteAllCache();
}
