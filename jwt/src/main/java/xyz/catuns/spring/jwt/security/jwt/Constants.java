package xyz.catuns.spring.jwt.security.jwt;

public final class Constants {

    private static final long TEN_HOURS = 36_000_000;

    public static final class Jwt {

        public static final String AUTHORIZATION_HEADER_KEY = "Authorization";
        public static final String EXPIRATION_HEADER_KEY = "token-expiration";

        public static final String AUTHORITY_KEY = "authorities";

        public static final String EXPIRATION_KEY = "auth.jwt.expiration";
        public static final String EXPIRATION_DEFAULT_VALUE = String.valueOf(TEN_HOURS);

        public static final String ISSUER_KEY = ("auth.jwt.issuer");
        public static final String ISSUER_DEFAULT_VALUE = "https://spring.catuns.xyz";

        public static final String SECRET_KEY = "auth.jwt.secret";
        public static final String SECRET_DEFAULT_VALUE = "";

        public static final String USERNAME_KEY = "username";

    }
}
