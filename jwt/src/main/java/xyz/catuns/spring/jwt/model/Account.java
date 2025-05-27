package xyz.catuns.spring.jwt.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "account", schema = "user_entity", uniqueConstraints = {
        @UniqueConstraint(name = "uc_provider_account_id",
                columnNames = {"provider", "providerAccountId"}
        )
})
public class Account {
    
    @Id
    private Long id;
    
    @Column(name = "type", nullable = false)
    private String type;
    
    @Column(name = "provider", nullable = false)
    private String provider;
    
    @Column(name = "provider_account_id", nullable = false)
    private String providerAccountId;
    
    @Column(name = "refresh_token")
    private String refreshToken;
    
    @Column(name = "refresh_token_expires_in")
    private Integer refreshTokenExpiresIn;
    
    @Column(name = "access_token")
    private String accessToken;
    
    @Column(name = "expires_at")
    private Integer expiresAt;
    
    @Column(name = "token_type")
    private String tokenType;
    
    @Column(name = "scope")
    private String scope;
    
    @Column(name = "id_token")
    private String idToken;
    
    @Column(name = "session_state")
    private String sessionState;
    
    @Column(name = "oath_token_secret")
    private String oathTokenSecret;
    
    @Column(name = "oath_token")
    private String oathToken;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
