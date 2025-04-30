package xyz.catuns.spring.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.catuns.spring.model.user.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}
