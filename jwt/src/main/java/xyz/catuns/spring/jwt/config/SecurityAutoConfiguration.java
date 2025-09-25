package xyz.catuns.spring.jwt.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import xyz.catuns.spring.jwt.config.user.UserConfigurationProperties;
import xyz.catuns.spring.jwt.repository.UserEntityRepository;
import xyz.catuns.spring.jwt.security.UserDetailsServiceImpl;

@EnableWebSecurity
@AutoConfiguration
@EnableConfigurationProperties(UserConfigurationProperties.class)
@ConditionalOnProperty(prefix = "auth.jwt", name = "enabled", havingValue = "true", matchIfMissing = true)
@Import(SecurityFilterChainCustomizers.class)
public class SecurityAutoConfiguration {

    @Bean(name = "userEntityDetailsService")
//    @ConditionalOnMissingBean(UserDetailsService.class)
    @ConditionalOnBean(UserEntityRepository.class)
    public UserDetailsService defaultUserDetailsService(UserEntityRepository userEntityRepository) {
        return new UserDetailsServiceImpl(userEntityRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
