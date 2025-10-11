package xyz.catuns.spring.jwt.utils;

public final class Constants {

    public static final class Config {
        public static final String JWT_CONFIG_PROPERTY_PREFIX = "jwt";
        public static final String JWT_SECURITY_CONFIG_PROPERTY_PREFIX = "jwt.security";
    }


    public static final class Headers {
        public static final String AUTHORIZATION_KEY = "Authorization";
        public static final String TOKEN_EXPIRATION_KEY = "x-token-expiration";
    }

    public static final class Jwt {
        public static final String BEARER_TOKEN_PREFIX = "Bearer ";
    }
}
