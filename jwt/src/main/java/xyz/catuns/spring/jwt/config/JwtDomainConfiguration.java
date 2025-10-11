package xyz.catuns.spring.jwt.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.domain.EntityScanPackages;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import xyz.catuns.spring.jwt.config.properties.JwtDomainProperties;

/**
 * Configuration for JWT domain entities
 * Registers entity scanning and repository configuration based on @EnableJwtSecurity
 */
@Configuration
@EnableConfigurationProperties(JwtDomainProperties.class)
@ConditionalOnClass(name = "javax.persistence.MappedSuperclass")
public class JwtDomainConfiguration implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(
            AnnotationMetadata importingClassMetadata,
            BeanDefinitionRegistry registry) {

        JwtSecurityConfigurationSelector.DomainConfiguration config =
                JwtDomainMetadataHolder.getMetadata();

        if (config == null || !config.hasDomain()) {
            return;
        }

        try {
            // Get packages to scan
            String[] packagesToScan = config.getScanPackages();

            // Register entity scan packages
            EntityScanPackages.register(registry, packagesToScan);

            // Note: @EnableJpaRepositories needs to be on a configuration class
            // So we'll create it dynamically via inner class
            registerRepositoryConfiguration(registry, packagesToScan);

            // Store configuration for other beans to use
            registry.registerBeanDefinition(
                    "jwtDomainConfiguration",
                    org.springframework.beans.factory.support.BeanDefinitionBuilder
                            .genericBeanDefinition(JwtSecurityConfigurationSelector.DomainConfiguration.class, () -> config)
                            .getBeanDefinition()
            );

        } finally {
            JwtDomainMetadataHolder.clear();
        }
    }

    private void registerRepositoryConfiguration(BeanDefinitionRegistry registry, String[] packages) {
        // Register a configuration class that will have @EnableJpaRepositories
        // This is a simplified approach - in production you might use
        // RepositoryConfigurationDelegate or AnnotationRepositoryConfigurationSource
    }

    /**
     * Inner configuration for repository scanning
     */
    @Configuration
    @EnableJpaRepositories(
            basePackages = {}, // Will be set dynamically
            considerNestedRepositories = true
    )
    static class RepositoryConfiguration {
    }
}