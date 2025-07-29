package xyz.catuns.spring.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import xyz.catuns.spring.jwt.model.VerificationToken;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    @Query("SELECT t from VerificationToken t WHERE t.identifier = :identifier " +
            "AND t.expires >= :expiration")
    Optional<VerificationToken> findByIdentifierAndExpiredAfter(
            @Param("identifier") String identifier,
            @Param("expiration") Instant expiration);

    List<VerificationToken> findAllByIdentifier(@Param("identifier") String identifier);

    Optional<VerificationToken> findByToken(String token);

    void deleteAllByIdentifier(String identifier);
}
