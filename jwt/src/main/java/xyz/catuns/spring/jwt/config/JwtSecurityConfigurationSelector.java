package xyz.catuns.spring.jwt.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import xyz.catuns.spring.jwt.annotations.EnableJwtSecurity;
import xyz.catuns.spring.jwt.autoconfigure.JwtAuthenticationAutoConfiguration;
import xyz.catuns.spring.jwt.autoconfigure.JwtSecurityAutoConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Selects appropriate configurations based on @EnableJwtSecurity attributes
 * Determines if domain entities are configured and imports accordingly
 */
public class JwtSecurityConfigurationSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(EnableJwtSecurity.class.getName())
        );

        if (attributes == null) {
            return new String[]{JwtSecurityAutoConfiguration.class.getName()};
        }

        List<String> imports = new ArrayList<>();

        // Always import base configuration
        imports.add(JwtSecurityAutoConfiguration.class.getName());

        // Detect if domain entities are configured
        DomainConfiguration domainConfig = detectDomainConfiguration(attributes, importingClassMetadata);

        if (domainConfig.hasDomain()) {
            // Import domain-specific configurations
            imports.add(JwtDomainConfiguration.class.getName());

            // Register domain metadata for later use
            JwtDomainMetadataHolder.setMetadata(domainConfig);
        }

        // Conditionally import authentication configuration
        if (attributes.getBoolean("enableAuthenticationManager")) {
            imports.add(JwtAuthenticationAutoConfiguration.class.getName());
        }

        return imports.toArray(new String[0]);
    }

    /**
     * Detect and validate domain configuration
     */
    private DomainConfiguration detectDomainConfiguration(
            AnnotationAttributes attributes,
            AnnotationMetadata metadata) {

        String[] domainPackages = attributes.getStringArray("domainPackages");
        Class<?> userEntityClass = attributes.getClass("userEntityClass");
        Class<?> roleEntityClass = attributes.getClass("roleEntityClass");
        Class<?> userRepositoryClass = attributes.getClass("userRepositoryClass");
        boolean enableUserDetailsService = attributes.getBoolean("enableUserDetailsService");

        DomainConfiguration config = new DomainConfiguration();
        config.setDomainPackages(domainPackages);
        config.setUserEntityClass(userEntityClass);
        config.setRoleEntityClass(roleEntityClass);
        config.setUserRepositoryClass(userRepositoryClass);
        config.setEnableUserDetailsService(enableUserDetailsService);
        config.setAnnotatedClassName(metadata.getClassName());

        // Validate configuration
        config.validate();

        return config;
    }

    /**
     * Holds domain configuration metadata
     */
    @Setter
    @Getter
    public static class DomainConfiguration {
        // Getters and setters
        private String[] domainPackages = {};
        private Class<?> userEntityClass = Object.class;
        private Class<?> roleEntityClass = Object.class;
        private Class<?> userRepositoryClass = Object.class;
        private boolean enableUserDetailsService = true;
        private String annotatedClassName;

        public boolean hasDomain() {
            return domainPackages.length > 0
                    || userEntityClass != Object.class
                    || userRepositoryClass != Object.class;
        }

        public String[] getScanPackages() {
            if (domainPackages.length > 0) {
                return domainPackages;
            }

            if (userEntityClass != Object.class) {
                return new String[]{ClassUtils.getPackageName(userEntityClass)};
            }

            if (userRepositoryClass != Object.class) {
                return new String[]{ClassUtils.getPackageName(userRepositoryClass)};
            }

            return new String[]{ClassUtils.getPackageName(annotatedClassName)};
        }

        public void validate() {
            // If any domain class is specified, validate completeness
            if (userEntityClass != Object.class
                    || roleEntityClass != Object.class
                    || userRepositoryClass != Object.class) {

                if (userEntityClass == Object.class) {
                    throw new IllegalArgumentException(
                            "@EnableJwtSecurity: userEntityClass must be specified when configuring domain entities"
                    );
                }

                if (userRepositoryClass == Object.class) {
                    throw new IllegalArgumentException(
                            "@EnableJwtSecurity: userRepositoryClass must be specified when configuring domain entities"
                    );
                }

                // Validate that userEntityClass extends UserEntity
                try {
                    Class<?> baseUserEntity = Class.forName("xyz.catuns.spring.jwt.model.UserEntity");
                    if (!baseUserEntity.isAssignableFrom(userEntityClass)) {
                        throw new IllegalArgumentException(
                                userEntityClass.getName() + " must extend xyz.catuns.spring.jwt.model.UserEntity"
                        );
                    }
                } catch (ClassNotFoundException e) {
                    // Base class not found - this is okay for testing
                }

                // Validate that userRepositoryClass extends UserEntityRepository
                try {
                    Class<?> baseUserRepository = Class.forName("xyz.catuns.spring.jwt.repository.UserEntityRepository");
                    if (!baseUserRepository.isAssignableFrom(userRepositoryClass)) {
                        throw new IllegalArgumentException(
                                userRepositoryClass.getName() + " must extend xyz.catuns.spring.jwt.repository.UserEntityRepository"
                        );
                    }
                } catch (ClassNotFoundException e) {
                    // Base class not found - this is okay for testing
                }
            }
        }

    }
}
