package xyz.catuns.spring.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.catuns.spring.jwt.model.UserEntity;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}