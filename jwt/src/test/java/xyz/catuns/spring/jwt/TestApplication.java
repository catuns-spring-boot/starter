package xyz.catuns.spring.jwt;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootConfiguration
@EnableAutoConfiguration
@EntityScan(basePackages = "xyz.catuns.spring.jwt.test.entity")
@EnableJpaRepositories(basePackages = "xyz.catuns.spring.jwt.test.repository")
public class TestApplication {
}
