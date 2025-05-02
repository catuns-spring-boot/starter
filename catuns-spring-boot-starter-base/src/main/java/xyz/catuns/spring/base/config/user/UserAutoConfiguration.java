package xyz.catuns.spring.base.config.user;


import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import xyz.catuns.spring.base.repository.user.UserEntityRepository;
import xyz.catuns.spring.base.security.DefaultUserDetailsService;

@Configuration
@ConditionalOnClass({JpaRepository.class, UserDetailsService.class})
@EnableConfigurationProperties(UserProperties.class)
public class UserAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService defaultUserDetailsService(UserEntityRepository userEntityRepository) {
        return new DefaultUserDetailsService(userEntityRepository);
    }


}
