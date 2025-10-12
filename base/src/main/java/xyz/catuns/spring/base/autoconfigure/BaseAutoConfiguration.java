package xyz.catuns.spring.base.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import xyz.catuns.spring.base.autoconfigure.properties.BaseConfigurationProperties;

@Slf4j
@AutoConfiguration
@Import({
        ExceptionHandlerAutoConfiguration.class
        // Add other auto-configurations here
})
@EnableConfigurationProperties(BaseConfigurationProperties.class)
public class BaseAutoConfiguration {

    public BaseAutoConfiguration() {
        log.debug("Base Auto-Configuration initialized");
    }
}
