package xyz.catuns.spring.base.autoconfigure.properties;

import lombok.Data;

import java.util.Map;

@Data
public class KafkaProperties {
    private Map<String, String> topics;
    private Map<String, String> configs;
    private Integer partitions = 1;
    private Integer replicas = 1;
}
