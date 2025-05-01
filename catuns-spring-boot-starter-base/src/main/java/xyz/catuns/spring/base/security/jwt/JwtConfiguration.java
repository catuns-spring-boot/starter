package xyz.catuns.spring.base.security.jwt;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@AutoConfiguration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfiguration
{
//    private final JwtProperties jwtProperties;
//
//    public JwtConfiguration(JwtProperties jwtProperties) {
//        this.jwtProperties = jwtProperties;
//    }
}
