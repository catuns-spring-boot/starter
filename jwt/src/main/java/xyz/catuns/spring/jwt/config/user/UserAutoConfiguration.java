package xyz.catuns.spring.jwt.config.user;


import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import xyz.catuns.spring.jwt.repository.UserEntityRepository;
import xyz.catuns.spring.jwt.security.DefaultUserDetailsService;

@Configuration
@ConditionalOnClass({JpaRepository.class, UserDetailsService.class})
@EnableConfigurationProperties(AdminUserProperties.class)
public class UserAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService defaultUserDetailsService(UserEntityRepository userEntityRepository) {
        return new DefaultUserDetailsService(userEntityRepository);
    }

    @Bean
    public BeanDefinitionRegistryPostProcessor entityPackageRegistrar() {
        return registry ->
                AutoConfigurationPackages.register(registry, "xyz.catuns.spring.jwt.model");
    }
}
