package xyz.catuns.spring.jwt.security.jwt.filter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import xyz.catuns.spring.jwt.exception.TokenExpiredException;
import xyz.catuns.spring.jwt.security.jwt.JwtService;

import java.io.IOException;

import static xyz.catuns.spring.jwt.security.jwt.Constants.Jwt.AUTHORIZATION_HEADER_KEY;


public class JwtTokenValidatorFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtTokenValidatorFilter.class);

    private static final String BEARER_PREFIX = "Bearer ";

    private final String shouldNotFilterPath;
    private final JwtService jwtService;

    /**
     *
     * @param shouldNotFilterPath the path that should not be filtered. eg: `/api/users/user`
     */
    public JwtTokenValidatorFilter(JwtService jwtService, String shouldNotFilterPath) {
        this.shouldNotFilterPath = shouldNotFilterPath;
        this.jwtService = jwtService;
    }

    /**
     *
     * Sets the default path that should not be filtered to `/api/users/user`
     *
     */
    public JwtTokenValidatorFilter(JwtService jwtService) {
        this(jwtService, "/api/users/user");
    }

    /**
     * Validates the JWT token present in the request and sets up authentication if the token is valid.
     *
     * @param request The HTTP servlet request
     * @param response The HTTP servlet response
     * @param filterChain The filter chain for executing other filters
     * @throws ServletException If there's an error during the filter execution
     * @throws IOException If there's an I/O error during the filter execution
     *
     * Note: This filter is executed once per request, as it extends OncePerRequestFilter
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = request.getHeader(AUTHORIZATION_HEADER_KEY);
        if (jwtToken != null) {
            try {
                if (jwtToken.regionMatches(true, 0, BEARER_PREFIX, 0, BEARER_PREFIX.length())) {
                    jwtToken = jwtToken.substring(BEARER_PREFIX.length()).trim();
                }
                Authentication authentication = jwtService.validate(jwtToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (ExpiredJwtException exception) {
                log.debug("Jwt expired {}", exception.toString());
                throw new TokenExpiredException();
            } catch (Exception exception) {
                throw new BadCredentialsException("Invalid token received", exception);
            }
        }
        filterChain.doFilter(request, response);
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals(shouldNotFilterPath);
    }
}
