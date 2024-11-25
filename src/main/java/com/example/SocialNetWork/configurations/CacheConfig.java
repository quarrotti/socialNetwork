package com.example.SocialNetWork.configurations;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();


        cacheManager.registerCustomCache("userCache",
                Caffeine.newBuilder()
                        .maximumSize(100)
                        .expireAfterWrite(10, TimeUnit.MINUTES)
                        .build());

        cacheManager.registerCustomCache("noteCache",
                Caffeine.newBuilder()
                        .maximumSize(200)
                        .expireAfterWrite(10, TimeUnit.MINUTES)
                        .build());

        cacheManager.registerCustomCache("discussionCache",
                Caffeine.newBuilder()
                        .maximumSize(200)
                        .expireAfterWrite(10, TimeUnit.MINUTES)
                        .build());

        return cacheManager;
    }
}
