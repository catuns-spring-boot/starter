package xyz.catuns.spring.base.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import xyz.catuns.spring.base.security.UsernameAuthenticationProvider;
import xyz.catuns.spring.base.security.cors.DefaultCorsConfigurationHandler;
import xyz.catuns.spring.base.security.jwt.filter.JwtTokenGeneratorFilter;
import xyz.catuns.spring.base.security.jwt.filter.JwtTokenValidatorFilter;

@Configuration
@ConditionalOnClass({SecurityFilterChain.class})
@ConditionalOnProperty(prefix = "auth.jwt", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableMethodSecurity
public class JwtSecurityAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(
                                "/api/users/user"
                        ).authenticated()
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/error",
                                "/api/users/login",
                                "/api/users/register")
                        .permitAll()
                        .anyRequest().authenticated());

        http.cors(corsConfig -> corsConfig.configurationSource(new DefaultCorsConfigurationHandler()));
        http.csrf(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(hbc -> hbc.authenticationEntryPoint(new GlobalAuthenticationEntryPoint()));
        http.exceptionHandling(handler -> handler
                .authenticationEntryPoint(new GlobalAuthenticationEntryPoint())
                .accessDeniedHandler(new GlobalAccessDeniedHandler())
        );

        return http.build();
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
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
