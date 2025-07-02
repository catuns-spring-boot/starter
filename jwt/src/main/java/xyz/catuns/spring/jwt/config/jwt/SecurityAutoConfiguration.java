package xyz.catuns.spring.jwt.config.jwt;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import xyz.catuns.spring.base.exception.GlobalAccessDeniedHandler;
import xyz.catuns.spring.base.exception.GlobalAuthenticationEntryPoint;
import xyz.catuns.spring.jwt.config.user.UserConfigurationProperties;
import xyz.catuns.spring.jwt.repository.UserEntityRepository;
import xyz.catuns.spring.jwt.security.UserDetailsServiceImpl;
import xyz.catuns.spring.jwt.security.UsernameAuthenticationProvider;
import xyz.catuns.spring.jwt.security.cors.DefaultCorsConfigurationHandler;
import xyz.catuns.spring.jwt.security.jwt.JwtService;
import xyz.catuns.spring.jwt.security.jwt.filter.JwtTokenGeneratorFilter;
import xyz.catuns.spring.jwt.security.jwt.filter.JwtTokenValidatorFilter;

@AutoConfiguration
@EnableConfigurationProperties(UserConfigurationProperties.class)
@ConditionalOnProperty(prefix = "auth.jwt", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SecurityAutoConfiguration {

    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtService jwtService) throws Exception {
        return http
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAfter(new JwtTokenGeneratorFilter(jwtService), BasicAuthenticationFilter.class)
                .addFilterBefore(new JwtTokenValidatorFilter(jwtService), BasicAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(c -> c.configurationSource(new DefaultCorsConfigurationHandler()))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(hbc -> hbc.authenticationEntryPoint(new GlobalAuthenticationEntryPoint()))
                .exceptionHandling(handler -> handler
                        .authenticationEntryPoint(new GlobalAuthenticationEntryPoint())
                        .accessDeniedHandler(new GlobalAccessDeniedHandler()))
                .authorizeHttpRequests(r -> r
                        .requestMatchers("/api/users/user").authenticated()
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger/index.html",
                                "/v3/api/docs/**",
                                "/error",
                                "/h2-console/**",
                                "/actuator/health",
                                "/api/users/login",
                                "/api/users/register").permitAll()
                        .anyRequest().authenticated())
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    @ConditionalOnClass(UserEntityRepository.class)
    public UserDetailsService defaultUserDetailsService(UserEntityRepository userEntityRepository) {
        return new UserDetailsServiceImpl(userEntityRepository);
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        UsernameAuthenticationProvider authProvider =
                new UsernameAuthenticationProvider(userDetailsService);
        ProviderManager providerManager = new ProviderManager(authProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }

    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
