package xyz.catuns.spring.jwt.security.jwt;

public final class Constants {

    public static final class Jwt {

        public static final String AUTHORIZATION_HEADER_KEY = "Authorization";
        public static final String EXPIRATION_HEADER_KEY = "x-token-expiration";

        public static final String BEARER_PREFIX = "Bearer ";

        public static final String AUTHORITIES_CLAIM_KEY = "authorities";
        public static final String USERNAME_CLAIM_KEY = "username";

        public static final String ISSUER_DEFAULT_VALUE = "https://spring.catuns.xyz";

    }
}
