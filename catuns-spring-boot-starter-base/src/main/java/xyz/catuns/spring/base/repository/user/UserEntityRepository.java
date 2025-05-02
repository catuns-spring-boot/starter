package xyz.catuns.spring.base.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.catuns.spring.base.model.user.UserEntity;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}
