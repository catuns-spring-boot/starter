package xyz.catuns.spring.jwt.config.resolver;

import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.util.ClassUtils;
import xyz.catuns.spring.jwt.config.DomainMetadata;

import java.util.*;

public class DomainMetadataResolver {

    public static DomainMetadata resolve(AnnotationAttributes attributes) {
        return new DomainMetadata(
                attributes.getClass("userEntityClass"),
                attributes.getClass("roleEntityClass"),
                attributes.getClass("userRepositoryClass"),
                attributes.getStringArray("scanPackages"),
                attributes.getBoolean("enableSecurityChain"),
                attributes.getBoolean("enableAuthenticationManager"),
                attributes.getBoolean("enableUserDetailsService")
        );
    }

    public static GenericBeanDefinition asBeanDefinition(DomainMetadata dm) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(dm.getClass());
        beanDefinition.getConstructorArgumentValues()
                .addIndexedArgumentValue(0, dm.getUserEntityClass());
        beanDefinition.getConstructorArgumentValues()
                .addIndexedArgumentValue(1, dm.getRoleEntityClass());
        beanDefinition.getConstructorArgumentValues()
                .addIndexedArgumentValue(2, dm.getUserRepositoryClass());
        beanDefinition.getConstructorArgumentValues()
                .addIndexedArgumentValue(3, dm.getScanPackages());
        beanDefinition.getConstructorArgumentValues()
                .addIndexedArgumentValue(4, dm.isEnableSecurityChain());
        beanDefinition.getConstructorArgumentValues()
                .addIndexedArgumentValue(5, dm.isEnableAuthenticationManager());
        beanDefinition.getConstructorArgumentValues()
                .addIndexedArgumentValue(6, dm.isEnableUserDetailsService());
        return beanDefinition;
    }

    public static Set<String> determineRepositoryScanPackages(DomainMetadata metadata) {
        Set<String> packages = new HashSet<>();
        if (metadata.hasUserRepository()) {
            packages.add(ClassUtils.getPackageName(metadata.getUserEntityClass()));
        } else if (metadata.hasScanPackages()) {
            return new HashSet<>(List.of(metadata.getScanPackages()));
        }
        return packages;
    }

    public static Set<String> determineEntityScanPackages(DomainMetadata metadata) {
        Set<String> packages = new LinkedHashSet<>();
        if (metadata.hasUserEntity()) {
            packages.add(ClassUtils.getPackageName(metadata.getUserEntityClass()));
        } else if (metadata.hasScanPackages()) {
            packages.addAll(Arrays.asList(metadata.getScanPackages()));
        }
        return packages;
    }
}
