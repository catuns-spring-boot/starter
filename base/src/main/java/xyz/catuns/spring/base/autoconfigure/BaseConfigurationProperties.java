package xyz.catuns.spring.base.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static xyz.catuns.spring.base.utils.Constants.BASE_CONFIG_PROPERTY_PREFIX;

@Data
@ConfigurationProperties(prefix = BASE_CONFIG_PROPERTY_PREFIX)
class BaseConfigurationProperties {


}
