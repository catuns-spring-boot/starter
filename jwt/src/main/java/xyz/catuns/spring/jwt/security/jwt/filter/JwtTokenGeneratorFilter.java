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

    private final String shouldFilterPath;
    private final JwtService jwtService;


    /**
     *
     * @param shouldFilterPath the paths that should be filtered. eg: `/api/users/user`
     */
    public JwtTokenGeneratorFilter(JwtService jwtService, String shouldFilterPath) {
        this.shouldFilterPath = shouldFilterPath;
        this.jwtService = jwtService;
    }

    /**
     *
     * Sets the default path that should not be filtered to `/api/users/user`
     *
     */
    public JwtTokenGeneratorFilter(JwtService jwtService) {
        this(jwtService, "/api/users/user");
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
            response.setHeader(AUTHORIZATION_HEADER_KEY, "Bearer ".concat(jwtToken.value()));
            response.setHeader(EXPIRATION_HEADER_KEY, jwtToken.expiration().toString());
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals(shouldFilterPath);
    }

}
