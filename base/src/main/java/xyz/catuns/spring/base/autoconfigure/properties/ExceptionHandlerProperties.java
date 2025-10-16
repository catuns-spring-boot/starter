package xyz.catuns.spring.base.autoconfigure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static xyz.catuns.spring.base.utils.Constants.EXCEPTION_CONFIG_PROPERTY_PREFIX;

/**
 * Configuration properties for global exception handling.
 * <p>
 *
 * @author Devin Catuns
 * @since 0.0.6
 */
@Data
@ConfigurationProperties(prefix = EXCEPTION_CONFIG_PROPERTY_PREFIX)
public class ExceptionHandlerProperties {

    /**
     * Enable or disable global exception handler
     *
     */
    private boolean enabled = true;

    /**
     * Include stack trace in error responses (dev only)
     */
    private boolean includeStackTrace = false;

    /**
     * Include exception cause in error responses
     */
    private boolean includeCause = false;

    /**
     * Include binding errors details
     */
    private boolean includeBindingErrors = true;

    /**
     * Log all exceptions
     */
    private boolean logExceptions = true;
}
