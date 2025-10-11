package xyz.catuns.spring.jwt.config;

/**
 * Thread-safe holder for domain configuration metadata
 * Used to pass configuration from selector to configuration classes
 */
public class JwtDomainMetadataHolder {

    private static final ThreadLocal<JwtSecurityConfigurationSelector.DomainConfiguration> METADATA =
            new ThreadLocal<>();

    public static void setMetadata(JwtSecurityConfigurationSelector.DomainConfiguration config) {
        METADATA.set(config);
    }

    public static JwtSecurityConfigurationSelector.DomainConfiguration getMetadata() {
        return METADATA.get();
    }

    public static void clear() {
        METADATA.remove();
    }
}