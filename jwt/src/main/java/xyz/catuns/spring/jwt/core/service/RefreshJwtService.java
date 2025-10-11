package xyz.catuns.spring.jwt.core.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import xyz.catuns.spring.jwt.autoconfigure.properties.JwtProperties;
import xyz.catuns.spring.jwt.exception.MissingSecretException;
import xyz.catuns.spring.jwt.domain.entity.TokenEntity;
import xyz.catuns.spring.jwt.domain.repository.TokenEntityRepository;
import xyz.catuns.spring.jwt.core.model.JwtToken;

import java.time.Instant;
import java.util.Date;

public class RefreshJwtService<RefreshToken extends TokenEntity> extends AbstractJwtService<String> {

    public static final String IDENTIFIER_CLAIM_KEY = "identifier";

    protected final TokenEntityRepository<RefreshToken> repository;

    public RefreshJwtService(JwtProperties properties, TokenEntityRepository<RefreshToken> repository) throws MissingSecretException {
        super(properties);
        this.repository = repository;
    }

    /**
     * Creates jwt token using {@link JwtProperties#getRefresh()} duration
     * @param identifier username field (subject)
     * @return {@link JwtToken}
     */
    @Override
    public JwtToken generate(String identifier) {
        Instant now = Instant.now();
        Instant expiration = now.plus(properties.getRefresh());

        JwtBuilder jwtBuilder = Jwts.builder();
        customizer().customize(jwtBuilder);

        String token = jwtBuilder.issuedAt(Date.from(now))
                .subject(identifier)
                .issuer(properties.getIssuer())
                .claim(IDENTIFIER_CLAIM_KEY, identifier)
                .expiration(Date.from(expiration))
                .signWith(getSecretKey())
                .compact();

        return new JwtToken(token, expiration, now, identifier);
    }

    @Override
    public String validate(String token) {
        Claims claims = this.getClaims(token);
        String identifier = String.valueOf(claims.get(IDENTIFIER_CLAIM_KEY));
        RefreshToken refreshToken = repository.findByToken(token)
                .orElseThrow();

        return identifier;
    }


}
