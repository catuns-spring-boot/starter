package xyz.catuns.spring.base.properties;

import lombok.Data;

import java.time.Duration;

@Data
public class CacheProperties {
    private String name;
    private Duration ttl;
}
