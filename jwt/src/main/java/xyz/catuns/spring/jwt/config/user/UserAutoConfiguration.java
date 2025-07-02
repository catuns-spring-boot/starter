package xyz.catuns.spring.jwt.config.user;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(UserConfigurationProperties.class)
public class UserAutoConfiguration {


//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        if (applicationContext instanceof BeanDefinitionRegistry) {
//            AutoConfigurationPackages.register(
//                    (BeanDefinitionRegistry) applicationContext,
//                    "xyz.catuns.spring.jwt.model",
//                    "xyz.catuns.spring.jwt.repository"
//            );
//        }
//    }
}
