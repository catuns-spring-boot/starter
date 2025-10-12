package xyz.catuns.spring.base.autoconfigure.properties;

import lombok.Data;

import java.util.Map;

/**
 * Kafka broker properties
 * @author Devin Catuns
 */
@Data
public class KafkaProperties {
    /**
     * Map key to value topic name
     */
    private Map<String, String> topics;
    /**
     * Additional configuration properties to
     * apply to NewTopic builder
     */
    private Map<String, String> configs;
    /**
     * Number of partitions to apply to new topics
     */
    private Integer partitions = 1;
    /**
     * Number of replicas to apply to new topics
     */
    private Integer replicas = 1;
}
