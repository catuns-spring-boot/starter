package xyz.catuns.spring.base.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * This class is used to get username from Security context holder
 * @param <T>
 * @param <R>
 */
@FunctionalInterface
public interface Authenticated<T,R> {
    /**
     * Todo:
     * @param t not implemented
     * @return
     */
    R apply(T t);

    default String username() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            } else {
                return principal.toString();
            }
        }
        return "";
    }

}
