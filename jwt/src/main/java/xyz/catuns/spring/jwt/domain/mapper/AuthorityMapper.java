package xyz.catuns.spring.jwt.domain.mapper;

import io.jsonwebtoken.Claims;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static xyz.catuns.spring.jwt.core.service.AuthJwtService.AUTHORITIES_CLAIM_KEY;

@Mapper(componentModel = SPRING)
public interface AuthorityMapper {

    @Named("extractAuthoritiesList")
    default List<String> extractAuthorities(Authentication auth) {
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

    @Named("extractAuthoritiesString")
    default String extractAuthorities(Claims claims) {
        Object authoritiesClaim = claims.get(AUTHORITIES_CLAIM_KEY);
        if (authoritiesClaim instanceof List<?> rawList) {
            List<String> authoritiesList = new ArrayList<>();
            for (Object obj : rawList) {
                if (obj instanceof String) {
                    authoritiesList.add((String) obj);
                }
            }
            return String.join(",", authoritiesList);
        } else {
            return String.valueOf(authoritiesClaim);
        }
    }
    @Named("toGrantedAuthority")
    default GrantedAuthority toGrantedAuthority(String authority) {
        return new SimpleGrantedAuthority(authority);
    }

}
