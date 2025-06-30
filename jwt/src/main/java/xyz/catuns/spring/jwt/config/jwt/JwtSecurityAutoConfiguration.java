package xyz.catuns.spring.jwt.config.jwt;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
import xyz.catuns.spring.jwt.repository.UserEntityRepository;
import xyz.catuns.spring.jwt.security.UsernameAuthenticationProvider;
import xyz.catuns.spring.jwt.security.cors.DefaultCorsConfigurationHandler;
import xyz.catuns.spring.jwt.security.jwt.JwtProperties;
import xyz.catuns.spring.jwt.security.jwt.filter.JwtTokenGeneratorFilter;
import xyz.catuns.spring.jwt.security.jwt.filter.JwtTokenValidatorFilter;
import xyz.catuns.spring.jwt.service.UserEntityService;
import xyz.catuns.spring.jwt.service.UserEntityServiceImpl;

@EnableMethodSecurity
@AutoConfiguration
@ConditionalOnProperty(prefix = "auth.jwt", name = "enabled", havingValue = "true", matchIfMissing = true)
public class JwtSecurityAutoConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
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
    @ConditionalOnMissingBean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService
    ) {
        UsernameAuthenticationProvider authProvider =
                new UsernameAuthenticationProvider(userDetailsService);
        ProviderManager providerManager = new ProviderManager(authProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @ConditionalOnBean(UserEntityRepository.class)
    @ConditionalOnMissingBean
    public UserEntityService userEntityService(
            UserEntityRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtProperties jwtProperties
    ) {
        return new UserEntityServiceImpl(
                userRepository,
                passwordEncoder,
                authenticationManager,
                jwtProperties
        );
    }
}
