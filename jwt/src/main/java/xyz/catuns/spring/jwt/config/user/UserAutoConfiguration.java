package xyz.catuns.spring.jwt.config.user;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import xyz.catuns.spring.jwt.repository.UserEntityRepository;
import xyz.catuns.spring.jwt.security.UserDetailsServiceImpl;

@Configuration
@EnableConfigurationProperties(AdminUserProperties.class)
public class UserAutoConfiguration implements ApplicationContextAware {

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService defaultUserDetailsService(UserEntityRepository userEntityRepository) {
        return new UserDetailsServiceImpl(userEntityRepository);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext instanceof BeanDefinitionRegistry) {
            AutoConfigurationPackages.register(
                    (BeanDefinitionRegistry) applicationContext,
                    "xyz.catuns.spring.jwt.model",
                    "xyz.catuns.spring.jwt.repository"
            );
        }
    }
}
