package xyz.catuns.spring.security.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import xyz.catuns.spring.security.jwt.JwtToken;

import java.io.IOException;

import static xyz.catuns.spring.security.jwt.JwtConstants.*;
import static xyz.catuns.spring.security.jwt.JwtConstants.JWT_EXPIRATION_KEY;

public class JwtTokenGeneratorFilter extends OncePerRequestFilter {

    private final String shouldFilterPath;

    /**
     *
     * @param shouldFilterPath the paths that should be filtered. eg: `/api/users/user`
     */
    public JwtTokenGeneratorFilter(String shouldFilterPath) {
        this.shouldFilterPath = shouldFilterPath;
    }

    /**
     *
     * Sets the default path that should not be filtered to `/api/users/user`
     *
     */
    public JwtTokenGeneratorFilter() {
        this.shouldFilterPath = "/api/users/user";
    }

    /**
     *
     * @param request     The HTTP servlet request
     * @param response    The HTTP servlet response
     * @param filterChain The filter chain for executing other filters
     * @throws ServletException If there's an error during the filter execution
     * @throws IOException      If there's an I/O error during the filter execution
     *
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            Environment env = getEnvironment();
            String secret = env.getProperty(JWT_SECRET_KEY, JWT_SECRET_DEFAULT_VALUE);
            String issuer = env.getProperty(JWT_ISSUER_KEY, "");
            Long expiration = Long.parseLong(env.getProperty(JWT_EXPIRATION_KEY, String.valueOf(36_000_000)));

            String jwtToken = new JwtToken(issuer, expiration).generate(auth, secret);
            response.setHeader(JWT_HEADER, jwtToken);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals(shouldFilterPath);
    }

}
