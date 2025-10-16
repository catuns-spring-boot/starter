package xyz.catuns.spring.base.autoconfigure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static xyz.catuns.spring.base.utils.Constants.BASE_CONFIG_PROPERTY_PREFIX;

/**
 * Configuration properties for base starter.
 * <p>
 *
 * @author Devin Catuns
 * @since 0.0.6
 */
@Data
@ConfigurationProperties(prefix = BASE_CONFIG_PROPERTY_PREFIX)
public class BaseConfigurationProperties {


}
