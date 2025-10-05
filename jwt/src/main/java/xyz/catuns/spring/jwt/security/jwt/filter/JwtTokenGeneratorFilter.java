package xyz.catuns.spring.jwt.security.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import xyz.catuns.spring.jwt.security.jwt.JwtToken;
import xyz.catuns.spring.jwt.security.jwt.JwtService;

import java.io.IOException;

import static xyz.catuns.spring.jwt.security.jwt.Constants.Jwt.*;

public class JwtTokenGeneratorFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtTokenGeneratorFilter(JwtService jwtService) {
        this.jwtService = jwtService;
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
            JwtToken jwtToken = jwtService.generate(auth);
            response.setHeader(AUTHORIZATION_HEADER_KEY, BEARER_PREFIX.concat(jwtToken.value()));
            response.setHeader(EXPIRATION_HEADER_KEY, jwtToken.expiration().toString());
        }
        filterChain.doFilter(request, response);
    }
}
