package xyz.catuns.spring.base.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import xyz.catuns.spring.base.repository.user.UserRepository;
import xyz.catuns.spring.base.security.DefaultUserDetailsService;

@Configuration
@ConditionalOnClass({JpaRepository.class, UserDetailsService.class})
public class UserAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService defaultUserDetailsService(UserRepository userRepository) {
        return new DefaultUserDetailsService(userRepository);
    }
}
